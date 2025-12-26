package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreAdder {

    private final GenreRepository genreRepository;

    GenreDto addGenre(String name) {
        Genre genre = new Genre(name);
        Genre saved = genreRepository.save(genre);
        return new GenreDto(saved.getId(), saved.getName());
    }
}
