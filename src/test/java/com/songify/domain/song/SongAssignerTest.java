package com.songify.domain.song;

import com.songify.domain.genre.Genre;
import com.songify.domain.genre.GenreNotFoundException;
import com.songify.domain.genre.GenreRepository;
import com.songify.domain.song.dto.SongDto;
import com.songify.domain.util.SongLanguageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongAssignerTest {

    @Mock
    SongRetriever songRetriever;

    @Mock
    SongMapper songMapper;

    @Mock
    GenreRepository genreRepository;

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongAssigner songAssigner;

    Instant releaseDate = Instant.now();

    @Test
    void assignGenre_should_set_genre_and_return_dto() {
        // Given
        Long songId = 1L;
        Long genreId = 10L;

        Song song = new Song("test");
        Genre genre = new Genre("rock");

        SongDto dto = new SongDto(songId, "test", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        when(songRetriever.findSongById(songId)).thenReturn(song);
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(songMapper.mapSongToSongDto(song)).thenReturn(dto);

        // When
        SongDto result = songAssigner.assignGenre(songId, genreId);

        // Then
        assertThat(song.getGenre()).isEqualTo(genre);
        assertThat(result).isEqualTo(dto);

        verify(songRetriever).findSongById(songId);
        verify(genreRepository).findById(genreId);
        verify(songMapper).mapSongToSongDto(song);
    }

    @Test
    void assignGenre_should_throw_when_genre_not_found() {
        // Given
        Long songId = 1L;
        Long genreId = 999L;
        Song song = new Song("test");

        when(songRetriever.findSongById(songId)).thenReturn(song);
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> songAssigner.assignGenre(songId, genreId))
                .isInstanceOf(GenreNotFoundException.class);

        verify(songRetriever).findSongById(songId);
        verify(genreRepository).findById(genreId);
        verify(songMapper, never()).mapSongToSongDto(any());
    }

}
