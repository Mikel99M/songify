package com.songify.domain.song.dto;

import com.songify.domain.genre.Genre;
import com.songify.domain.util.SongLanguageDto;
import lombok.Builder;

import java.time.Instant;

@Builder
public record SongDto(
        Long id,
        String name,
        Instant releaseDate,
        Long duration,
        String genre,
        SongLanguageDto language
        ) {
}
