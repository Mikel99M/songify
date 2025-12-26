package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistAdderTest {

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    ArtistAdder artistAdder;

    @Test
    void addArtist_should_create_and_save_artist() {
        // Given
        Artist saved = new Artist("bob");
        saved.setId(10L);

        when(artistRepository.save(any(Artist.class))).thenReturn(saved);

        // When
        Artist result = artistAdder.addArtist("bob");

        // Then
        assertThat(result).isEqualTo(saved);
        verify(artistRepository).save(any(Artist.class));
    }

    @Test
    void addArtistWithDefaultAlbumAndSong_should_create_relations_and_save() {
        // Given
        ArtistRequestDto dto = new ArtistRequestDto("mike");
        Artist saved = new Artist("mike");
        saved.setId(20L);

        when(artistRepository.save(any(Artist.class))).thenReturn(saved);

        // When
        Artist result = artistAdder.addArtistWithDefaultAlbumAndSong(dto);

        // Then
        assertThat(result).isEqualTo(saved);
        verify(artistRepository).save(any(Artist.class));
    }
}
