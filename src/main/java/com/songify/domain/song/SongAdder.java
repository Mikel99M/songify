package com.songify.domain.song;

import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
class SongAdder {

    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final SongAssigner songAssigner;

    SongDto addSong(SongRequestDto dto) {
        Song song = songMapper.mapSongRequestDtoToSong(dto);
        Song saved = songRepository.save(song);
        songAssigner.assignDefaultGenreToSong(saved.getId());
        return songMapper.mapSongToSongDto(saved);
    }

}
