package com.songify.domain.artist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistRetrieverTest {

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    ArtistRetriever artistRetriever;

    @Test
    void findById_should_return_artist_when_exists() {
        // Given
        Artist artist = new Artist("john");
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        // When
        Artist result = artistRetriever.findById(1L);

        // Then
        assertThat(result).isEqualTo(artist);
        verify(artistRepository).findById(1L);
    }

    @Test
    void findById_should_throw_exception_when_not_found() {
        // Given
        when(artistRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> artistRetriever.findById(99L))
                .isInstanceOf(ArtistNotFoundException.class);
        verify(artistRepository).findById(99L);
    }

    @Test
    void findAll_should_return_artists_from_repository() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Artist a1 = new Artist("john");
        Artist a2 = new Artist("alice");

        when(artistRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(a1, a2)));

        // When
        Set<Artist> result = artistRetriever.findAll(pageable);

        // Then
        assertThat(result).containsExactlyInAnyOrder(a1, a2);
        verify(artistRepository).findAll(pageable);
    }

    @Test
    void existsById_should_delegate_to_repository() {
        // Given
        when(artistRepository.existsById(5L)).thenReturn(true);

        // When
        boolean result = artistRetriever.existsById(5L);

        // Then
        assertThat(result).isTrue();
        verify(artistRepository).existsById(5L);
    }
}
