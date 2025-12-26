package com.songify.infrastructure.crud.genre;

import com.songify.domain.genre.GenreFacade;
import com.songify.domain.genre.dto.GenreDto;
import com.songify.domain.genre.dto.GenreRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

import java.util.Set;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {

    private final GenreFacade genreFacade;

    @GetMapping
    ResponseEntity<Set<GenreDto>> getGenres(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(genreFacade.findAllGenres(pageable));
    }

    @PostMapping
    ResponseEntity<GenreDto> addGenre(@RequestBody @Valid GenreRequestDto dto) {
        GenreDto response = genreFacade.addGenre(dto);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreFacade.deleteGenreById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<GenreDto> changeGenreName(@PathVariable Long id, @RequestBody @Valid GenreRequestDto dto) {
        GenreDto response = genreFacade.changeGenreNameById(id, dto);
        return ResponseEntity.status(201).body(response);
    }
}

