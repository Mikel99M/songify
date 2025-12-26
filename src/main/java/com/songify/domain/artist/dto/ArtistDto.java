package com.songify.domain.artist.dto;

import lombok.Builder;

@Builder
public record ArtistDto(Long id, String name) {
}