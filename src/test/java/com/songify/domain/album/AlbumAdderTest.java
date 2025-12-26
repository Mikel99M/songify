package com.songify.domain.album;

import com.songify.domain.artist.Artist;
import com.songify.domain.artist.ArtistNotFoundException;
import com.songify.domain.artist.ArtistRepository;
import com.songify.domain.song.Song;
import com.songify.domain.song.SongNotFoundException;
import com.songify.domain.song.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumAdderTest {

    @Mock
    private SongRepository songRepository;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumAdder albumAdder;

    @Test
    void addAlbum_should_return_album_added() {
        // Given
        Long songId = 1L;
        Instant date = Instant.now();

        Song song = Song.builder().id(songId).name("test").build();

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        Album savedAlbum = Album.builder()
                .id(10L)
                .title("album")
                .releaseDate(date)
                .songs(Set.of(song))
                .build();

        when(albumRepository.save(any(Album.class))).thenReturn(savedAlbum);

        // When
        Album result = albumAdder.addAlbum(songId, "album", date);

        // Then
        assertThat(result).isEqualTo(savedAlbum);
        assertThat(result.getReleaseDate()).isEqualTo(date);
        assertThat(result.getSongs()).contains(song);

        verify(songRepository).findById(songId);
        verify(albumRepository).save(any(Album.class));

    }

    @Test
    void addAlbum_should_throw_exception_when_song_not_found() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumAdder.addAlbum(1L, "a", Instant.now()))
                .isInstanceOf(SongNotFoundException.class);
    }

    @Test
    void addSongToAlbum_should_return_album_saved() {
        Long songId = 1L;
        Long albumId = 2L;

        Song song = Song.builder().id(songId).name("s").build();
        Album album = Album.builder().id(albumId).title("t").songs(new HashSet<>()).build();

        when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        Album saved = Album.builder()
                .id(albumId)
                .title("t")
                .songs(Set.of(song))
                .build();

        when(albumRepository.save(any(Album.class))).thenReturn(saved);

        Album result = albumAdder.addSongToAlbum(songId, albumId);

        assertThat(result).isEqualTo(saved);
        assertThat(result.getSongs()).contains(song);

        verify(songRepository).findById(songId);
        verify(albumRepository).findById(albumId);
        verify(albumRepository).save(any(Album.class));
    }

    @Test
    void addSongToAlbum_should_throw_exception_when_song_not_found() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumAdder.addSongToAlbum(1L, 2L))
                .isInstanceOf(SongNotFoundException.class);
    }

    @Test
    void addSongToAlbum_should_throw_exception_when_album_not_found() {
        Song song = Song.builder().id(1L).name("s").build();
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(albumRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumAdder.addSongToAlbum(1L, 2L))
                .isInstanceOf(AlbumNotFoundException.class);
    }

    @Test
    void addArtistToAlbum_should_return_album_saved() {
        Long artistId = 1L;
        Long albumId = 2L;

        Artist artist = Artist.builder().id(artistId).name("a").build();
        Album album = Album.builder().id(albumId).title("t").artists(new HashSet<>()).build();

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        Album saved = Album.builder()
                .id(albumId)
                .title("t")
                .artists(Set.of(artist))
                .build();

        when(albumRepository.save(any(Album.class))).thenReturn(saved);

        Album result = albumAdder.addArtistToAlbum(artistId, albumId);

        assertThat(result).isEqualTo(saved);
        assertThat(result.getArtists()).contains(artist);

        verify(artistRepository).findById(artistId);
        verify(albumRepository).findById(albumId);
        verify(albumRepository).save(any(Album.class));
    }

    @Test
    void addArtistToAlbum_should_throw_exception_when_artist_not_found() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumAdder.addArtistToAlbum(1L, 2L))
                .isInstanceOf(ArtistNotFoundException.class);
    }

    @Test
    void addArtistToAlbum_should_throw_exception_when_album_not_found() {
        Artist artist = Artist.builder().id(1L).name("a").build();
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(albumRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumAdder.addArtistToAlbum(1L, 2L))
                .isInstanceOf(AlbumNotFoundException.class);
    }
}