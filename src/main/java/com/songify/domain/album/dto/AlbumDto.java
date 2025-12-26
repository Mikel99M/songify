package com.songify.domain.album.dto;

import lombok.Builder;

@Builder
public record AlbumDto(Long id, String title) {
}