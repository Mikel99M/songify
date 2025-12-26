package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreRetrieverTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreRetriever genreRetriever;

    @Test
    void findAllGenres_should_return_genre_dtos() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        Genre g1 = new Genre("rock");
        g1.setId(1L);
        Genre g2 = new Genre("rap");
        g2.setId(2L);

        when(genreRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(g1, g2)));

        // When
        Set<GenreDto> result = genreRetriever.findAllGenres(pageable);

        // Then
        assertThat(result).containsExactlyInAnyOrder(
                new GenreDto(1L, "rock"),
                new GenreDto(2L, "rap")
        );

        verify(genreRepository).findAll(pageable);
    }

    @Test
    void findById_should_return_genre_when_exists() {
        // Given
        Genre genre = new Genre("metal");
        when(genreRepository.findById(5L)).thenReturn(Optional.of(genre));

        // When
        Genre result = genreRetriever.findById(5L);

        // Then
        assertThat(result).isEqualTo(genre);
        verify(genreRepository).findById(5L);
    }

    @Test
    void findById_should_throw_exception_when_not_found() {
        // Given
        when(genreRepository.findById(7L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> genreRetriever.findById(7L))
                .isInstanceOf(GenreNotFoundException.class);

        verify(genreRepository).findById(7L);
    }

    @Test
    void existsById_should_delegate_to_repository() {
        // Given
        when(genreRepository.existsById(3L)).thenReturn(true);

        // When
        boolean result = genreRetriever.existsById(3L);

        // Then
        assertThat(result).isTrue();
        verify(genreRepository).existsById(3L);
    }
}
