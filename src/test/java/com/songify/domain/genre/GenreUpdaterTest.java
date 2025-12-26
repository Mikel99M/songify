package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreUpdaterTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreUpdater genreUpdater;

    @Test
    void changeGenreNameById_should_update_genre_name() {
        // Given
        Long id = 1L;
        Genre genre = new Genre("old");
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreRepository.save(any(Genre.class))).thenAnswer(inv -> inv.getArgument(0));

        GenreRequestDto dto = new GenreRequestDto("new");

        // When
        Genre result = genreUpdater.changeGenreNameById(id, dto);

        // Then
        assertThat(result.getName()).isEqualTo("new");
        verify(genreRepository).findById(id);
        verify(genreRepository).save(genre);
    }

    @Test
    void changeGenreNameById_should_throw_exception_when_genre_not_found() {
        // Given
        Long id = 100L;
        GenreRequestDto dto = new GenreRequestDto("x");

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> genreUpdater.changeGenreNameById(id, dto))
                .isInstanceOf(GenreNotFoundException.class);

        verify(genreRepository).findById(id);
        verify(genreRepository, never()).save(any());
    }
}
