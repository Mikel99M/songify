package com.songify.domain.song;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongRetrieverTest {

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongRetriever songRetriever;

    @Test
    void findSongById_should_return_song_when_exists() {
        // Given
        Song song = new Song("abc");
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        // When
        Song result = songRetriever.findSongById(1L);

        // Then
        assertThat(result).isEqualTo(song);
        verify(songRepository).findById(1L);
    }

    @Test
    void findSongById_should_throw_when_not_found() {
        // Given
        when(songRepository.findById(2L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> songRetriever.findSongById(2L))
                .isInstanceOf(SongNotFoundException.class);

        verify(songRepository).findById(2L);
    }

    @Test
    void findAll_should_return_page_of_songs() {
        // Given
        Pageable pageable = PageRequest.of(0, 20);
        Song s = new Song("abc");

        when(songRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(s)));

        // When
        Page<Song> result = songRetriever.findAll(pageable);

        // Then
        assertThat(result.getContent()).containsExactly(s);
        verify(songRepository).findAll(pageable);
    }
}
