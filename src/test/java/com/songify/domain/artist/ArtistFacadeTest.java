package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistDto;
import com.songify.domain.artist.dto.ArtistPatchRequestDto;
import com.songify.domain.artist.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistFacadeTest {

    @Mock private ArtistRetriever artistRetriever;
    @Mock private ArtistAdder artistAdder;
    @Mock private ArtistDeleter artistDeleter;
    @Mock private ArtistAssigner artistAssigner;
    @Mock private ArtistUpdater artistUpdater;
    @Mock private ArtistMapper artistMapper;

    @InjectMocks private ArtistFacade artistFacade;

    @Test
    void addArtist_shouldReturnMappedDto() {
        ArtistRequestDto dto = new ArtistRequestDto("John");
        Artist savedArtist = new Artist(1L, "John", Set.of());
        ArtistDto mappedDto = new ArtistDto(1L, "John");

        when(artistAdder.addArtist("John")).thenReturn(savedArtist);
        when(artistMapper.mapArtistToArtistDto(savedArtist)).thenReturn(mappedDto);

        ArtistDto result = artistFacade.addArtist(dto);

        assertThat(mappedDto).isEqualTo(result);
        verify(artistAdder).addArtist("John");
        verify(artistMapper).mapArtistToArtistDto(savedArtist);
    }

    @Test
    void addArtistWithDefaultAlbumAndSong_shouldReturnMappedDto() {
        ArtistRequestDto dto = new ArtistRequestDto("John");
        Artist savedArtist = new Artist(1L, "John", Set.of());
        ArtistDto mappedDto = new ArtistDto(1L, "John");

        when(artistAdder.addArtistWithDefaultAlbumAndSong(dto)).thenReturn(savedArtist);
        when(artistMapper.mapArtistToArtistDto(savedArtist)).thenReturn(mappedDto);

        ArtistDto result = artistFacade.addArtistWithDefaultAlbumAndSong(dto);

        assertThat(mappedDto).isEqualTo(result);
        verify(artistAdder).addArtistWithDefaultAlbumAndSong(dto);
        verify(artistMapper).mapArtistToArtistDto(savedArtist);
    }

    @Test
    void updateArtistNameById_shouldReturnMappedDto() {
        Artist updatedArtist = new Artist(1L, "NewName", Set.of());
        ArtistDto mappedDto = new ArtistDto(1L, "NewName");

        when(artistUpdater.updateName(1L, "NewName")).thenReturn(updatedArtist);
        when(artistMapper.mapArtistToArtistDto(updatedArtist)).thenReturn(mappedDto);

        ArtistDto result = artistFacade.updateArtistNameById(1L, "NewName");

        assertThat(mappedDto).isEqualTo(result);
        verify(artistUpdater).updateName(1L, "NewName");
        verify(artistMapper).mapArtistToArtistDto(updatedArtist);
    }

    @Test
    void patchArtist_shouldReturnMappedDto() {
        ArtistPatchRequestDto dto = new ArtistPatchRequestDto("PatchedName");
        Artist updatedArtist = new Artist(1L, "PatchedName", Set.of());
        ArtistDto mappedDto = new ArtistDto(1L, "PatchedName");

        when(artistUpdater.patch(1L, dto)).thenReturn(updatedArtist);
        when(artistMapper.mapArtistToArtistDto(updatedArtist)).thenReturn(mappedDto);

        ArtistDto result = artistFacade.patchArtist(1L, dto);

        assertThat(mappedDto).isEqualTo(result);
        verify(artistUpdater).patch(1L, dto);
        verify(artistMapper).mapArtistToArtistDto(updatedArtist);
    }

    @Test
    void assignArtistToAlbum_shouldCallAssigner() {
        artistFacade.assignArtistToAlbum(1L, 10L);
        verify(artistAssigner).addArtistToAlbum(1L, 10L);
    }

    @Test
    void findAllArtists_shouldReturnMappedSet() {
        Pageable pageable = PageRequest.of(0, 10);
        Set<Artist> artists = Set.of(new Artist(1L, "John", Set.of()));
        Set<ArtistDto> mappedDtos = Set.of(new ArtistDto(1L, "John"));

        when(artistRetriever.findAll(pageable)).thenReturn(artists);
        when(artistMapper.mapArtistsToArtistDtos(artists)).thenReturn(mappedDtos);

        Set<ArtistDto> result = artistFacade.findAllArtists(pageable);

        assertThat(mappedDtos).isEqualTo(result);
        verify(artistRetriever).findAll(pageable);
        verify(artistMapper).mapArtistsToArtistDtos(artists);
    }

    @Test
    void deleteArtistByIdWithAlbumAndSongs_shouldCallDeleter() {
        artistFacade.deleteArtistByIdWithAlbumAndSongs(5L);
        verify(artistDeleter).deleteArtistByIdWithAlbumAndSongs(5L);
    }
}
