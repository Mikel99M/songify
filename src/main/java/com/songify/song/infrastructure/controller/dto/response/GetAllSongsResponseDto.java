package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.infrastructure.controller.Song;

import java.util.Map;

public record GetAllSongsResponseDto(Map<Integer, Song> songs) {
}
