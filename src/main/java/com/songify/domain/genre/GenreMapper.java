package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
class GenreMapper {

    GenreDto mapGenreToGenreDto(final Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
