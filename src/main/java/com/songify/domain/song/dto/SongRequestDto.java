package com.songify.domain.song.dto;

import com.songify.domain.util.SongLanguageDto;

import java.time.Instant;

public record SongRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        SongLanguageDto language
) {
}
