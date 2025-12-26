package com.songify.domain.artist;

import com.songify.domain.album.Album;
import com.songify.domain.album.AlbumNotFoundException;
import com.songify.domain.album.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistAssignerTest {

    @Mock
    ArtistRetriever artistRetriever;

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    ArtistAssigner artistAssigner;

    @Test
    void addArtistToAlbum_should_add_album_to_artist() {
        // Given
        Long artistId = 1L;
        Long albumId = 10L;

        Artist artist = Artist.builder().id(artistId).name("test").albums(new HashSet<>()).build();
        Album album = Album.builder().id(albumId).title("album").build();

        when(artistRetriever.findById(artistId)).thenReturn(artist);
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        // When
        artistAssigner.addArtistToAlbum(artistId, albumId);

        // Then
        verify(artistRetriever).findById(artistId);
        verify(albumRepository).findById(albumId);

        assertThat(artist.getAlbums()).contains(album);
    }

    @Test
    void addArtistToAlbum_should_throw_when_album_not_found() {
        // Given
        Long artistId = 1L;
        Long albumId = 10L;

        Artist artist = Artist.builder().id(artistId).name("test").build();

        when(artistRetriever.findById(artistId)).thenReturn(artist);
        when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> artistAssigner.addArtistToAlbum(artistId, albumId))
                .isInstanceOf(AlbumNotFoundException.class);
    }
}
