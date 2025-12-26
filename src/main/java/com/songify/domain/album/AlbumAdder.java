package com.songify.domain.album;

import com.songify.domain.artist.ArtistNotFoundException;
import com.songify.domain.artist.ArtistRepository;
import com.songify.domain.song.SongNotFoundException;
import com.songify.domain.song.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public Album addAlbum(final Long songId, final String title, final Instant releaseDate) {
        var song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song with id %s not found.".formatted(songId)));

        Album album = new Album(title, releaseDate);
        album.addSongToAlbum(song);

        return albumRepository.save(album);
    }

    public Album addSongToAlbum(final Long songId, final Long albumId) {
        var song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song with id %s not found.".formatted(songId)));
        var album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found.".formatted(albumId)));

        album.addSongToAlbum(song);
        return albumRepository.save(album);
    }

    public Album addArtistToAlbum(final Long artistId, final Long albumId) {
        var artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id %s not found.".formatted(artistId)));
        var album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id %s not found.".formatted(albumId)));

        album.addArtist(artist);
        return albumRepository.save(album);
    }
}
