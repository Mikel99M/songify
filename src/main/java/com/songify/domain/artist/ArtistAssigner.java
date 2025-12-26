package com.songify.domain.artist;

import com.songify.domain.album.Album;
import com.songify.domain.album.AlbumNotFoundException;
import com.songify.domain.album.AlbumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAssigner {

    private final ArtistRetriever artistRetriever;
    private final AlbumRepository albumRepository;

    void addArtistToAlbum(Long artistId, Long albumId) {
        Artist artist = artistRetriever.findById(artistId);
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found".formatted(albumId)));

        artist.addAlbum(album);
    }
}

