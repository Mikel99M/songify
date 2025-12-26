package com.songify.domain.song;

import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongPatchRequestDto;
import com.songify.domain.song.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SongFacade {

    private final SongRetriever songRetriever;
    private final SongAdder songAdder;
    private final SongUpdater songUpdater;
    private final SongDeleter songDeleter;
    private final SongAssigner songAssigner;
    private final SongMapper songMapper;

    public SongDto addSong(SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public Page<SongDto> findAll(Pageable pageable) {
        return songRetriever.findAll(pageable)
                .map(songMapper::mapSongToSongDto);
    }

    public SongDto findSong(Long id) {
        return songMapper.mapSongToSongDto(songRetriever.findSongById(id));
    }

    public SongDto patchSong(Long id, SongPatchRequestDto req) {
        return songUpdater.patch(id, req);
    }

    public void deleteSong(Long id) {
        songDeleter.deleteSong(id);
    }

    public SongDto assignGenre(Long songId, Long genreId) {
        return songAssigner.assignGenre(songId, genreId);
    }

}
