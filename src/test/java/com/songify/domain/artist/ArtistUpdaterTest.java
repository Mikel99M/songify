package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistPatchRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistUpdaterTest {

    @Mock
    ArtistRetriever artistRetriever;

    @InjectMocks
    ArtistUpdater artistUpdater;

    @Test
    void updateName_should_update_artist_name() {
        // Given
        Long id = 1L;
        Artist artist = new Artist("old");
        when(artistRetriever.findById(id)).thenReturn(artist);

        // When
        Artist result = artistUpdater.updateName(id, "new");

        // Then
        assertThat(result.getName()).isEqualTo("new");
        verify(artistRetriever).findById(id);
    }

    @Test
    void patch_should_update_name_when_provided() {
        // Given
        Long id = 2L;
        ArtistPatchRequestDto req = new ArtistPatchRequestDto("patched");
        Artist artist = new Artist("old");
        when(artistRetriever.findById(id)).thenReturn(artist);

        // When
        Artist result = artistUpdater.patch(id, req);

        // Then
        assertThat(result.getName()).isEqualTo("patched");
        verify(artistRetriever).findById(id);
    }

    @Test
    void patch_should_not_change_name_when_null_in_request() {
        // Given
        Long id = 3L;
        ArtistPatchRequestDto req = new ArtistPatchRequestDto(null);
        Artist artist = new Artist("unchanged");
        when(artistRetriever.findById(id)).thenReturn(artist);

        // When
        Artist result = artistUpdater.patch(id, req);

        // Then
        assertThat(result.getName()).isEqualTo("unchanged");
        verify(artistRetriever).findById(id);
    }
}
