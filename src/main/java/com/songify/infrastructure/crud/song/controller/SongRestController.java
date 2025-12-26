package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.song.SongFacade;
import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongPatchRequestDto;
import com.songify.domain.song.dto.SongRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongFacade songFacade;

    @GetMapping
    ResponseEntity<Page<SongDto>> getAllSongs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<SongDto> response = songFacade.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<SongDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        SongDto response = songFacade.findSong(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<SongDto> postSong(@RequestBody @Valid SongRequestDto request) {
        SongDto response = songFacade.addSong(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songFacade.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<SongDto> update(@PathVariable Long id,
                                   @RequestBody @Valid SongPatchRequestDto request) {
        SongDto response = songFacade.patchSong(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{songId}/genre/{genreId}")
    public ResponseEntity<SongDto> assignGenre(@PathVariable Long songId, @PathVariable Long genreId) {
        SongDto response = songFacade.assignGenre(songId, genreId);
        return ResponseEntity.ok(response);
    }

}
