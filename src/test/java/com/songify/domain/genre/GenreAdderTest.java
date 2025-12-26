package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreAdderTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreAdder genreAdder;

    @Test
    void addGenre_should_return_saved_genre_dto() {
        // Given
        Genre saved = new Genre(1L, "rock");

        when(genreRepository.save(any(Genre.class))).thenReturn(saved);

        // When
        GenreDto result = genreAdder.addGenre("rock");

        // Then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("rock");
    }
}
