package com.songify.domain.song;

import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongRequestDto;
import com.songify.domain.util.SongLanguageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongAdderTest {

    @Mock
    SongRepository songRepository;

    @Mock
    SongMapper songMapper;

    @Mock
    SongAssigner songAssigner;

    @InjectMocks
    SongAdder songAdder;

    Instant releaseDate = Instant.now();

    @Test
    void addSong_should_save_and_return_dto() {
        // Given
        SongRequestDto dto = new SongRequestDto("name", Instant.now(), 200L, SongLanguageDto.ENGLISH);
        Song mappedSong = new Song("mapped");
        Song savedSong = new Song("saved");
        SongDto resultDto = new SongDto(1L, "saved", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        when(songMapper.mapSongRequestDtoToSong(dto)).thenReturn(mappedSong);
        when(songRepository.save(mappedSong)).thenReturn(savedSong);
        when(songMapper.mapSongToSongDto(savedSong)).thenReturn(resultDto);

        // When
        SongDto result = songAdder.addSong(dto);

        // Then
        assertThat(result).isEqualTo(resultDto);
        assertThat(result.genre()).isEqualTo("default");

        verify(songMapper).mapSongRequestDtoToSong(dto);
        verify(songRepository).save(mappedSong);
        verify(songMapper).mapSongToSongDto(savedSong);
    }
}
