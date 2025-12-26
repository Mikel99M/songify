package com.songify.domain.song;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {

    private final SongRepository songRepository;

    void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    void deleteAllSongs(Set<Long> ids) {
        songRepository.deleteByIdIn(ids);
    }
}
