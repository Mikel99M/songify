package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GenreRetriever {

    private final GenreRepository genreRepository;


    Set<GenreDto> findAllGenres(final Pageable pageable) {
        return genreRepository.findAll(pageable)
                .stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
    }

    Genre findById(final Long id) {
        return genreRepository.findById(id).orElseThrow(
                () -> new GenreNotFoundException("Genre with id %s not found".formatted(id))
        );
    }

    boolean existsById(final Long id) {
        return genreRepository.existsById(id);
    }
}
