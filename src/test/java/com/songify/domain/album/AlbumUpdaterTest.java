package com.songify.domain.album;

import com.songify.domain.album.dto.AlbumRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumUpdaterTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumUpdater albumUpdater;

    @Test
    void updateAlbum_should_update_and_return_album() {
        Album album = Album.builder().id(1L).title("old").releaseDate(Instant.now()).build();
        AlbumRequestDto dto = new AlbumRequestDto(1L, "new", Instant.parse("2020-01-01T00:00:00Z"));

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        Album result = albumUpdater.updateAlbum(1L, dto);

        assertThat(result.getTitle()).isEqualTo("new");
        assertThat(result.getReleaseDate()).isEqualTo(dto.releaseDate());
    }

    @Test
    void updateAlbum_should_throw_when_not_found() {
        AlbumRequestDto dto = new AlbumRequestDto(1L, "new", Instant.now());

        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> albumUpdater.updateAlbum(1L, dto))
                .isInstanceOf(AlbumNotFoundException.class);
    }
}
