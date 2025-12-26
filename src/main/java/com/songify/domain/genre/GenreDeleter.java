package com.songify.domain.genre;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreDeleter {

    private final GenreRepository genreRepository;

    void deleteGenreById(final Long id) {
        int i = genreRepository.deleteByIdReturningCount(id);
        if (i != 1) {
            throw new GenreWasNotDeletedException("Genre with id %s was not deleted".formatted(id));
        }

        genreRepository.deleteById(id);
    }

}
