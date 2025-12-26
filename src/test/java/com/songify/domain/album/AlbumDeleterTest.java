package com.songify.domain.album;

import com.songify.domain.song.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumDeleterTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumDeleter albumDeleter;

    @Test
    void deleteAllAlbumsByIds_should_invoke_repository_delete() {
        Set<Long> ids = Set.of(1L, 2L);

        albumDeleter.deleteAllAlbumsByIds(ids);

        verify(albumRepository).deleteByIdIn(ids);
    }

    @Test
    void deleteAlbumByIdWhenNoSongsAssigned_should_delete_album() {
        Album album = Album.builder()
                .id(1L)
                .songs(Set.of())
                .build();

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        albumDeleter.deleteAlbumByIdWhenNoSongsAssigned(1L);

        verify(albumRepository).deleteById(1L);
    }

    @Test
    void deleteAlbumByIdWhenNoSongsAssigned_should_throw_when_not_found() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumDeleter.deleteAlbumByIdWhenNoSongsAssigned(1L))
                .isInstanceOf(AlbumNotFoundException.class);
    }

    @Test
    void deleteAlbumByIdWhenNoSongsAssigned_should_throw_when_album_has_songs() {
        Album album = Album.builder()
                .id(1L)
                .songs(Set.of(Song.builder().build()))
                .build();

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        assertThatThrownBy(() -> albumDeleter.deleteAlbumByIdWhenNoSongsAssigned(1L))
                .isInstanceOf(IllegalStateException.class);

        verify(albumRepository, never()).deleteById(anyLong());
    }
}
