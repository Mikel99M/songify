package com.songify.domain.album;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class AlbumDeleter {

    private final AlbumRepository albumRepository;

    public void deleteAllAlbumsByIds(final Set<Long> albumIdsToDelete) {
        albumRepository.deleteByIdIn(albumIdsToDelete);
    }

    public void deleteAlbumByIdWhenNoSongsAssigned(final Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found".formatted(id)));

        boolean hasSongs = album.getSongs() != null && !album.getSongs().isEmpty();

        if (!hasSongs) {
            albumRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Album %s has songs and cannot be deleted".formatted(id));
        }
    }

}
