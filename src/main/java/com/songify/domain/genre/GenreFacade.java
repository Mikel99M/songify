package com.songify.domain.genre;

import com.songify.domain.genre.dto.GenreDto;
import com.songify.domain.genre.dto.GenreRequestDto;
import com.songify.domain.song.SongRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class GenreFacade {

    private final GenreRetriever genreRetriever;
    private final GenreAdder genreAdder;
    private final GenreDeleter genreDeleter;
    private final GenreUpdater genreUpdater;

    private final GenreMapper genreMapper;

    private final SongRepository songRepository;

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public Set<GenreDto> findAllGenres(Pageable pageable) {
        return genreRetriever.findAllGenres(pageable);
    }

    public void deleteGenreById(Long id) {
        Genre genre = genreRetriever.findById(id);
        if (!songRepository.existsByGenre(genre)) {
            genreDeleter.deleteGenreById(id);
        }
    }

    public GenreDto changeGenreNameById(final Long id, final @Valid GenreRequestDto dto) {
        Genre genre = genreUpdater.changeGenreNameById(id, dto);
        return genreMapper.mapGenreToGenreDto(genre);
    }
}
