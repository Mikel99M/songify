package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtistMapper {

    public ArtistDto mapArtistToArtistDto(Artist artist) {
        return ArtistDto.builder()
                .id(artist.getId())
                .name(artist.getName())
                .build();
    }

    Set<ArtistDto> mapArtistsToArtistDtos(final Set<Artist> artists) {
        return artists.stream()
                .map(this::mapArtistToArtistDto)
                .collect(Collectors.toSet());
    }
}
