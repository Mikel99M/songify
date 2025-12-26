package com.songify.domain.album.dto;

import com.songify.domain.artist.dto.ArtistDto;
import com.songify.domain.song.dto.SongDto;
import lombok.Builder;

import java.util.Set;

@Builder
public record AlbumDtoWithArtistsAndSongs(
        AlbumDto album,
        Set<ArtistDto> artists,
        Set<SongDto> songs)
{
}
