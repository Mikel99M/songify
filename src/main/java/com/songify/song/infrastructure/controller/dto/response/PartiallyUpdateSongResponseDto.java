package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.infrastructure.controller.Song;

public record PartiallyUpdateSongResponseDto(Song updatedSong) {
}
