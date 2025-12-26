package com.songify.domain.artist;

import com.songify.domain.album.Album;
import com.songify.domain.album.AlbumRepository;
import com.songify.domain.song.Song;
import com.songify.domain.song.SongRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class ArtistDeleter {

    private final ArtistRetriever artistRetriever;

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Transactional
    void deleteArtistByIdWithAlbumAndSongs(Long artistId) {

        Artist artist = artistRetriever.findById(artistId);

        Set<Album> albums = albumRepository.findAllAlbumsByArtistId(artistId);

        if (albums.isEmpty()) {
            artistRepository.deleteById(artistId);
            return;
        }

        albums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.getArtists().remove(artist));

        Set<Album> albumsToDelete = albums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> songIdsToDelete = albumsToDelete.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());

        if (!songIdsToDelete.isEmpty()) {
            songRepository.deleteByIdIn(songIdsToDelete);
        }

        Set<Long> albumIdsToDelete = albumsToDelete.stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        if (!albumIdsToDelete.isEmpty()) {
            albumRepository.deleteByIdIn(albumIdsToDelete);
        }

        artistRepository.deleteById(artistId);

        log.info("Deleted artist {}, albums: {}, songs: {}",
                artistId,
                albumIdsToDelete,
                songIdsToDelete
        );
    }
}
