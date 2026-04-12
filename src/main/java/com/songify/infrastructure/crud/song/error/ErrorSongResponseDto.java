package com.songify.infrastructure.crud.song.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto(String message, HttpStatus status) {
}
