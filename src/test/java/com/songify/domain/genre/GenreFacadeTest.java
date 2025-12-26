package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import com.songify.domain.genre.dto.GenreRequestDto;
import com.songify.domain.song.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreFacadeTest {

    @Mock private GenreRetriever genreRetriever;
    @Mock private GenreAdder genreAdder;
    @Mock private GenreDeleter genreDeleter;
    @Mock private GenreUpdater genreUpdater;
    @Mock private GenreMapper genreMapper;
    @Mock private SongRepository songRepository;

    @InjectMocks private GenreFacade genreFacade;

    @Test
    void addGenre_should_delegate_to_GenreAdder() {
        GenreRequestDto dto = new GenreRequestDto("Rock");
        GenreDto expected = new GenreDto(1L, "Rock");

        when(genreAdder.addGenre("Rock")).thenReturn(expected);

        GenreDto result = genreFacade.addGenre(dto);

        assertThat(result).isEqualTo(expected);
        verify(genreAdder).addGenre("Rock");
    }

    @Test
    void findAllGenres_should_return_from_Retriever() {
        Pageable pageable = PageRequest.of(0, 10);
        Set<GenreDto> expected = Set.of(new GenreDto(1L, "Pop"));

        when(genreRetriever.findAllGenres(pageable)).thenReturn(expected);

        Set<GenreDto> result = genreFacade.findAllGenres(pageable);

        assertThat(result).isEqualTo(expected);
        verify(genreRetriever).findAllGenres(pageable);
    }

    @Test
    void deleteGenreById_should_delete_whenNoSongAssigned() {
        long id = 5L;
        Genre genre = new Genre(5L, "Indie");

        when(genreRetriever.findById(id)).thenReturn(genre);
        when(songRepository.existsByGenre(genre)).thenReturn(false);

        genreFacade.deleteGenreById(id);

        verify(genreRetriever).findById(id);
        verify(songRepository).existsByGenre(genre);
        verify(genreDeleter).deleteGenreById(id);
    }

    @Test
    void deleteGenreById_shouldNotDelete_whenSongsExist() {
        long id = 5L;
        Genre genre = new Genre(5L, "Indie");

        when(genreRetriever.findById(id)).thenReturn(genre);
        when(songRepository.existsByGenre(genre)).thenReturn(true);

        genreFacade.deleteGenreById(id);

        verify(genreRetriever).findById(id);
        verify(songRepository).existsByGenre(genre);
        verify(genreDeleter, never()).deleteGenreById(anyLong());
    }

    @Test
    void changeGenreNameById_should_return_MappedDto() {
        GenreRequestDto dto = new GenreRequestDto("NewName");
        Genre updated = new Genre(1L, "NewName");
        GenreDto mapped = new GenreDto(1L, "NewName");

        when(genreUpdater.changeGenreNameById(1L, dto)).thenReturn(updated);
        when(genreMapper.mapGenreToGenreDto(updated)).thenReturn(mapped);

        GenreDto result = genreFacade.changeGenreNameById(1L, dto);

        assertThat(result).isEqualTo(mapped);
        verify(genreUpdater).changeGenreNameById(1L, dto);
        verify(genreMapper).mapGenreToGenreDto(updated);
    }
}
