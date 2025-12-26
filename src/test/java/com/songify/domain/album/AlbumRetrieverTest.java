package com.songify.domain.album;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumRetrieverTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumRetriever albumRetriever;

    @Test
    void findAllAlbums_should_return_all_albums() {
        Pageable pageable = Pageable.unpaged();
        Album album = Album.builder().id(1L).build();

        Page<Album> page = new PageImpl<>(List.of(album));
        when(albumRepository.findAll(pageable)).thenReturn(page);

        Page<Album> result = albumRetriever.findAllAlbums(pageable);

        assertThat(result).containsExactly(album);
        verify(albumRepository).findAll(pageable);
    }

    @Test
    void findById_should_return_album() {
        Album album = Album.builder().id(1L).build();

        when(albumRepository.findAlbumByIdWithSongsAndArtists(1L))
                .thenReturn(Optional.of(album));

        Album result = albumRetriever.findById(1L);

        assertThat(result).isEqualTo(album);
        verify(albumRepository).findAlbumByIdWithSongsAndArtists(1L);
    }

    @Test
    void findById_should_throw_when_not_found() {
        when(albumRepository.findAlbumByIdWithSongsAndArtists(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumRetriever.findById(1L))
                .isInstanceOf(AlbumNotFoundException.class);
    }
}
