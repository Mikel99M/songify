package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.Song;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song addSong(Song song) {
        log.info("adding new song: " + song);
        songRepository.saveToDatabase(song);
        return song;
    }

}
