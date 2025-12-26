package com.songify.domain.album;

import com.songify.domain.album.dto.AlbumDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AlbumMapper {

    public AlbumDto mapAlbumToAlbumDto(Album album) {
        return new AlbumDto(album.getId(), album.getTitle());
    }

    Page<AlbumDto> mapAlbumsToAlbumDtos(final Page<Album> allAlbums) {
        return allAlbums.map(this::mapAlbumToAlbumDto);
    }


}
