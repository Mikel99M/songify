package com.songify.domain.song;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongRetriever {

    private final SongRepository songRepository;

    Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id %s not found".formatted(id)));
    }

    Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }
}