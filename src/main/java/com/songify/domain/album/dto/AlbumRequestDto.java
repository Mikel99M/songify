package com.songify.domain.album.dto;

import java.time.Instant;

public record AlbumRequestDto(Long songId, String title, Instant releaseDate) {
}
