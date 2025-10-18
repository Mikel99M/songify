package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.util.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Transactional
class SongAdder {

    private final SongRepository songRepository;

    SongDto addSong(final SongRequestDto dto) {
        SongLanguageDto language = dto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());

        Song song = new Song(dto.name(), dto.releaseDate(), songLanguage, dto.duration());
        log.info("adding new song: " + song);
        Song savedSong = songRepository.save(song);
        return new SongDto(savedSong.getId(), savedSong.getName());
    }
}
