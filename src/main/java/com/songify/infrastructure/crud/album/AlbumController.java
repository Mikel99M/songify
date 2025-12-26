package com.songify.infrastructure.crud.album;

import com.songify.domain.album.AlbumFacade;
import com.songify.domain.album.dto.AlbumDto;
import com.songify.domain.album.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.album.dto.AlbumRequestDto;
import com.songify.domain.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/albums")
@AllArgsConstructor
class AlbumController {

    private final AlbumFacade albumFacade;

    @GetMapping
    public ResponseEntity<Page<AlbumDto>> getAlbums(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<AlbumDto> result = albumFacade.findAllAlbums(pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDtoWithArtistsAndSongs> getAlbumWithArtistsAndSongs(@PathVariable Long albumId) {
        AlbumDtoWithArtistsAndSongs dto = albumFacade.findAlbumWithArtistsAndSongs(albumId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{albumId}/songs")
    public ResponseEntity<Set<SongDto>> getAlbumSongs(@PathVariable Long albumId) {
        Set<SongDto> result = albumFacade.findAlbumsSongs(albumId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<AlbumDtoWithArtistsAndSongs> postAlbum(@RequestBody final AlbumRequestDto albumRequestDto) {
        AlbumDtoWithArtistsAndSongs dto = albumFacade.addAlbumWithSong(albumRequestDto);
        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{albumId}/artists/{artistId}")
    public ResponseEntity<AlbumDtoWithArtistsAndSongs> addArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
        AlbumDtoWithArtistsAndSongs dto = albumFacade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{albumId}/songs/{songId}")
    public ResponseEntity<AlbumDtoWithArtistsAndSongs> addSongToAlbum(@PathVariable Long songId, @PathVariable Long albumId) {
        AlbumDtoWithArtistsAndSongs dto = albumFacade.addSongToAlbum(songId, albumId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable Long albumId, @RequestBody AlbumRequestDto albumRequestDto) {
        AlbumDto dto = albumFacade.updateAlbum(albumId, albumRequestDto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long albumId) {
        albumFacade.deleteAlbumById(albumId);
        return ResponseEntity.status(204).build();
    }

}
