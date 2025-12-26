package com.songify.domain.song;

import com.songify.domain.genre.Genre;
import com.songify.domain.genre.GenreNotFoundException;
import com.songify.domain.genre.GenreRepository;
import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongPatchRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {

    private final SongRetriever songRetriever;
    private final GenreRepository genreRepository;
    private final SongMapper songMapper;

    SongDto patch(Long id, SongPatchRequestDto req) {

        Song song = songRetriever.findSongById(id);

        if (req.name() != null) song.setName(req.name());
        if (req.releaseDate() != null) song.setReleaseDate(req.releaseDate());
        if (req.duration() != null) song.setDuration(req.duration());
        if (req.language() != null)
            song.setLanguage(SongLanguage.valueOf(req.language().name()));

        if (req.genreId() != null) {
            Genre genre = genreRepository.findById(req.genreId())
                    .orElseThrow(() -> new GenreNotFoundException("Genre with id " + req.genreId() + " not found"));
            song.setGenre(genre);
        }

        return songMapper.mapSongToSongDto(song);
    }

}