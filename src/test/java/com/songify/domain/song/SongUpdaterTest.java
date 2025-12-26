package com.songify.domain.song;

import com.songify.domain.genre.Genre;
import com.songify.domain.genre.GenreNotFoundException;
import com.songify.domain.genre.GenreRepository;
import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongPatchRequestDto;
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
class SongUpdaterTest {

    @Mock
    SongRetriever songRetriever;

    @Mock
    GenreRepository genreRepository;

    @Mock
    SongMapper songMapper;

    @InjectMocks
    SongUpdater songUpdater;

    Instant releaseDate = Instant.now();

    @Test
    void patch_should_patch_all_fields_when_provided() {
        // Given
        Long id = 1L;
        Long genreId = 10L;

        Song song = new Song();
        Genre genre = new Genre("rock");

        SongPatchRequestDto req = new SongPatchRequestDto(
                "new-name",
                Instant.now(),
                200L,
                genreId,
                SongLanguage.ENGLISH
        );

        when(songRetriever.findSongById(id)).thenReturn(song);
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        SongDto dto = new SongDto(1L, "new-name", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);
        when(songMapper.mapSongToSongDto(song)).thenReturn(dto);

        // When
        SongDto result = songUpdater.patch(id, req);

        // Then
        assertThat(song.getName()).isEqualTo("new-name");
        assertThat(song.getGenre()).isEqualTo(genre);
        assertThat(result).isEqualTo(dto);

        verify(songRetriever).findSongById(id);
        verify(genreRepository).findById(genreId);
        verify(songMapper).mapSongToSongDto(song);
    }

    @Test
    void patch_should_throw_exception_when_genre_not_found() {
        // Given
        Long id = 1L;
        Long genreId = 99L;

        Song song = new Song();

        SongPatchRequestDto req = new SongPatchRequestDto(
                null, null, null, genreId, null
        );

        when(songRetriever.findSongById(id)).thenReturn(song);
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> songUpdater.patch(id, req))
                .isInstanceOf(GenreNotFoundException.class);

        verify(songRetriever).findSongById(id);
        verify(genreRepository).findById(genreId);
        verify(songMapper, never()).mapSongToSongDto(any());
    }
}
