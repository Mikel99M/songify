package com.songify.domain.artist;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
class ArtistRetriever {

    private final ArtistRepository artistRepository;

    Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id.toString()));
    }

    Set<Artist> findAll(Pageable pageable) {
        return new HashSet<>(artistRepository.findAll(pageable).getContent());
    }

    boolean existsById(Long id) {
        return artistRepository.existsById(id);
    }
}
