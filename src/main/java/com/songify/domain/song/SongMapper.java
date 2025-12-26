package com.songify.domain.song;

import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongRequestDto;
import com.songify.domain.util.SongLanguageDto;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public SongDto mapSongToSongDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getReleaseDate(),
                song.getDuration(),
                song.getGenre().getName(),
                SongLanguageDto.valueOf(song.getLanguage().name()));
    }

    Song mapSongRequestDtoToSong(SongRequestDto dto) {
        return new Song(
                dto.name(),
                dto.releaseDate(),
                SongLanguage.valueOf(dto.language().name()),
                dto.duration()
        );
    }
}
