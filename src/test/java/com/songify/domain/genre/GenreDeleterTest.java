package com.songify.domain.genre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GenreDeleterTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreDeleter genreDeleter;

    @Test
    void deleteGenreById_should_delete_genre_when_count_is_1() {
        // Given
        Long id = 1L;
        when(genreRepository.deleteByIdReturningCount(id)).thenReturn(1);

        // When
        genreDeleter.deleteGenreById(id);

        // Then
        verify(genreRepository).deleteByIdReturningCount(id);
        verify(genreRepository).deleteById(id);
    }

    @Test
    void deleteGenreById_should_throw_exception_when_delete_count_not_1() {
        // Given
        Long id = 5L;
        when(genreRepository.deleteByIdReturningCount(id)).thenReturn(0);

        // When / Then
        assertThatThrownBy(() -> genreDeleter.deleteGenreById(id))
                .isInstanceOf(GenreWasNotDeletedException.class);

        verify(genreRepository).deleteByIdReturningCount(id);
        verify(genreRepository, never()).deleteById(any());
    }
}
