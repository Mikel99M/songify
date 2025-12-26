package com.songify.domain.album;

import com.songify.domain.album.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlbumUpdater {

    private final AlbumRepository albumRepository;

    Album updateAlbum(Long id, AlbumRequestDto dto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found.".formatted(id)));

        album.setTitle(dto.title());
        album.setReleaseDate(dto.releaseDate());

        return album;
    }
}
