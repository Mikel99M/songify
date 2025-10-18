package com.songify.domain.crud.dto;

import com.songify.domain.crud.util.SongLanguageDto;

import java.time.Instant;

public record SongRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        SongLanguageDto language
) {
}
