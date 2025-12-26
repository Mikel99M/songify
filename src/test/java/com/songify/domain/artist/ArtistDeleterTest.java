package com.songify.domain.artist;

import com.songify.domain.album.Album;
import com.songify.domain.album.AlbumRepository;
import com.songify.domain.song.Song;
import com.songify.domain.song.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistDeleterTest {

    @Mock
    ArtistRetriever artistRetriever;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;

    @Mock
    SongRepository songRepository;

    @InjectMocks
    ArtistDeleter artistDeleter;

    @Test
    void deleteArtist_should_delete_when_no_albums() {
        // Given
        Long id = 1L;
        Artist artist = new Artist("x");
        when(artistRetriever.findById(id)).thenReturn(artist);
        when(albumRepository.findAllAlbumsByArtistId(id)).thenReturn(Set.of());

        // When
        artistDeleter.deleteArtistByIdWithAlbumAndSongs(id);

        // Then
        verify(artistRepository).deleteById(id);
    }

    @Test
    void deleteArtist_should_remove_artist_from_shared_albums() {
        // Given
        Long id = 2L;
        Artist artist = new Artist("a");
        when(artistRetriever.findById(id)).thenReturn(artist);

        Album shared = new Album("sh", Instant.now());
        shared.setId(100L);
        shared.setArtists(new HashSet<>(Set.of(artist, new Artist("other"))));

        when(albumRepository.findAllAlbumsByArtistId(id))
                .thenReturn(Set.of(shared));

        // When
        artistDeleter.deleteArtistByIdWithAlbumAndSongs(id);

        // Then
        assertThat(shared.getArtists()).hasSize(1);
        verify(artistRepository).deleteById(id);
    }

    @Test
    void deleteArtist_should_delete_albums_where_artist_is_only_one() {
        // Given
        Long id = 3L;
        Artist artist = new Artist("solo");
        when(artistRetriever.findById(id)).thenReturn(artist);

        Song s1 = new Song("aaa");
        s1.setId(11L);
        Song s2 = new Song("bbb");
        s2.setId(22L);

        Album solo = new Album("solo", Instant.now());
        solo.setId(200L);
        solo.setArtists(Set.of(artist));
        solo.setSongs(Set.of(s1, s2));

        when(albumRepository.findAllAlbumsByArtistId(id))
                .thenReturn(Set.of(solo));

        // When
        artistDeleter.deleteArtistByIdWithAlbumAndSongs(id);

        // Then
        verify(songRepository).deleteByIdIn(Set.of(11L, 22L));
        verify(albumRepository).deleteByIdIn(Set.of(200L));
        verify(artistRepository).deleteById(id);
    }
}
