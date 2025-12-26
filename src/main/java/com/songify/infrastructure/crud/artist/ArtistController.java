package com.songify.infrastructure.crud.artist;

import com.songify.domain.artist.ArtistFacade;
import com.songify.domain.artist.dto.ArtistDto;
import com.songify.domain.artist.dto.ArtistPatchRequestDto;
import com.songify.domain.artist.dto.ArtistRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
class ArtistController {

    private final ArtistFacade artistFacade;

    @GetMapping
    ResponseEntity<AllArtistsDto> getArtists(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        AllArtistsDto result = new AllArtistsDto(artistFacade.findAllArtists(pageable));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    ResponseEntity<ArtistDto> postArtist(@RequestBody @Valid ArtistRequestDto artistRequestDto) {
        ArtistDto response = artistFacade.addArtist(artistRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/default")
    ResponseEntity<ArtistDto> addArtistWithDefaultAlbumAndSong(@RequestBody @Valid ArtistRequestDto artistRequestDto) {
        ArtistDto response = artistFacade.addArtistWithDefaultAlbumAndSong(artistRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{artistId}")
    ResponseEntity<String> deleteArtistWithAllAlbumsAndSongs(@PathVariable Long artistId) {
        artistFacade.deleteArtistByIdWithAlbumAndSongs(artistId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{artistId}/albums/{albumId}")
    ResponseEntity<Void> assignArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
        artistFacade.assignArtistToAlbum(artistId, albumId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{artistId}/name/{newName}")
    ResponseEntity<ArtistDto> updateArtistName(@PathVariable Long artistId, @Valid @PathVariable String newName) {
        ArtistDto response = artistFacade.updateArtistNameById(artistId, newName);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{artistId}")
    ResponseEntity<ArtistDto> updateArtist(@PathVariable Long artistId, @RequestBody ArtistPatchRequestDto requestDto) {
        ArtistDto response = artistFacade.patchArtist(artistId, requestDto);
        return ResponseEntity.ok(response);
    }

}
