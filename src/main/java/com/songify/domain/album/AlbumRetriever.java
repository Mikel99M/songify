package com.songify.domain.album;

import com.songify.domain.song.Song;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    public Page<Album> findAllAlbums(final Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    public Album findById(final Long albumId) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found".formatted(albumId)));
    }

    public Set<Song> findAllSongInAlbum(final Long albumId) {
        return albumRepository.findAllSongInAlbum(albumId);
    }

}
