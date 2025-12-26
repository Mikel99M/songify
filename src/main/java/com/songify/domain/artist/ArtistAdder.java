package com.songify.domain.artist;

import com.songify.domain.album.Album;
import com.songify.domain.artist.dto.ArtistRequestDto;
import com.songify.domain.song.Song;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;

    Artist addArtist(String name) {
        Artist artist = new Artist(name);
        return artistRepository.save(artist);
    }

    Artist addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        Artist artist = new Artist(dto.name());

        Album album = new Album();
        album.setTitle("default-album:" + UUID.randomUUID());
        album.setReleaseDate(Instant.now());

        Song song = new Song("default-song:" + UUID.randomUUID());

        album.addSongToAlbum(song);
        artist.addAlbum(album);

        return artistRepository.save(artist);
    }
}
