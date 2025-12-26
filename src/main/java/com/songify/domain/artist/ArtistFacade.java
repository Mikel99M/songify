package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistDto;
import com.songify.domain.artist.dto.ArtistPatchRequestDto;
import com.songify.domain.artist.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class ArtistFacade {


    private final ArtistRetriever artistRetriever;
    private final ArtistAdder artistAdder;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;

    private final ArtistMapper artistMapper;

    public ArtistDto addArtist(ArtistRequestDto dto) {
        Artist saved = artistAdder.addArtist(dto.name());
        return artistMapper.mapArtistToArtistDto(saved);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        Artist saved = artistAdder.addArtistWithDefaultAlbumAndSong(dto);
        return artistMapper.mapArtistToArtistDto(saved);
    }

    public ArtistDto updateArtistNameById(Long id, String name) {
        Artist updated = artistUpdater.updateName(id, name);
        return artistMapper.mapArtistToArtistDto(updated);
    }

    public ArtistDto patchArtist(Long id, ArtistPatchRequestDto dto) {
        Artist updated = artistUpdater.patch(id, dto);
        return artistMapper.mapArtistToArtistDto(updated);
    }

    public void assignArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistMapper.mapArtistsToArtistDtos(artistRetriever.findAll(pageable));
    }

    public void deleteArtistByIdWithAlbumAndSongs(Long id) {
        artistDeleter.deleteArtistByIdWithAlbumAndSongs(id);
    }

}