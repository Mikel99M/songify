package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreUpdater {

    private final GenreRepository genreRepository;

    Genre changeGenreNameById(final Long id, final @Valid GenreRequestDto dto) {

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id %s not found".formatted(id)));

        genre.setName(dto.name());
        Genre genreUpdated = genreRepository.save(genre);
        return genreUpdated;
    }
}
