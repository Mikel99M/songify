package com.songify.domain.song.dto;

import com.songify.domain.song.SongLanguage;

import java.time.Instant;

public record SongPatchRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        Long genreId,
        SongLanguage language
) {}