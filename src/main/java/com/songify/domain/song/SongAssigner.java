package com.songify.domain.song;

import com.songify.domain.genre.Genre;
import com.songify.domain.genre.GenreNotFoundException;
import com.songify.domain.genre.GenreRepository;
import com.songify.domain.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SongAssigner {

    private final SongRetriever songRetriever;
    private final SongMapper songMapper;

    private final SongRepository songRepository;
    private final GenreRepository genreRepository;

    SongDto assignGenre(Long songId, Long genreId) {

        Song song = songRetriever.findSongById(songId);
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id %s not found".formatted(genreId)));

        song.setGenre(genre);

        return songMapper.mapSongToSongDto(song);
    }

    void assignDefaultGenreToSong(final Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException("Song with id %s not found".formatted(songId)));
        assignGenre(song.getId(), 1L);
    }
}
