package com.songify.domain.song;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SongDeleterTest {

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongDeleter songDeleter;

    @Test
    void deleteSong_should_call_repository_delete() {
        // Given
        Long id = 1L;

        // When
        songDeleter.deleteSong(id);

        // Then
        verify(songRepository).deleteById(id);
    }

    @Test
    void deleteAllSongs_should_call_repository_bulk_delete() {
        // Given
        Set<Long> ids = Set.of(1L, 2L, 3L);

        // When
        songDeleter.deleteAllSongs(ids);

        // Then
        verify(songRepository).deleteByIdIn(ids);
    }
}
