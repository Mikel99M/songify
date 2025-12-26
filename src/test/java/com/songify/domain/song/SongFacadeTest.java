package com.songify.domain.song;

import com.songify.domain.song.dto.SongDto;
import com.songify.domain.song.dto.SongPatchRequestDto;
import com.songify.domain.song.dto.SongRequestDto;
import com.songify.domain.util.SongLanguageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongFacadeTest {

    @Mock
    SongRetriever songRetriever;
    @Mock
    SongAdder songAdder;
    @Mock
    SongUpdater songUpdater;
    @Mock
    SongDeleter songDeleter;
    @Mock
    SongAssigner songAssigner;
    @Mock
    SongMapper songMapper;

    @InjectMocks
    SongFacade songFacade;

    Instant releaseDate = Instant.now();

    @Test
    void addSong_should_delegate_to_SongAdder() {
        // given
        SongRequestDto dto = new SongRequestDto("name", releaseDate, 1L, SongLanguageDto.ENGLISH);
        SongDto expected = new SongDto(1L, "name", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        when(songAdder.addSong(dto)).thenReturn(expected);

        // when
        SongDto result = songFacade.addSong(dto);

        // then
        assertThat(expected).isEqualTo(result);
        verify(songAdder).addSong(dto);
    }

    @Test
    void findSong_should_delegate_to_SongRetriever() {
        // Given
        SongDto expectedSongDto = new SongDto(2L, "test", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);
        Song song = Song.builder()
                .id(2L)
                .name("test")
                .build();
        when(songRetriever.findSongById(2L)).thenReturn(song);
        when(songMapper.mapSongToSongDto(song)).thenReturn(expectedSongDto);

        // When
        SongDto result = songFacade.findSong(2L);

        // Then
        assertThat(result).isEqualTo(expectedSongDto);
        verify(songRetriever).findSongById(2L);

    }

    @Test
    void findAll_should_delegate_to_SongRetriever() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        SongDto expectedSongDto1 = new SongDto(1L, "test1", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);
        SongDto expectedSongDto2 = new SongDto(2L, "test2", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);
        SongDto expectedSongDto3 = new SongDto(3L, "test3", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);
        SongDto expectedSongDto4 = new SongDto(4L, "test4", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        Song song1 = Song.builder().id(1L).name("test1").build();
        Song song2 = Song.builder().id(2L).name("test2").build();
        Song song3 = Song.builder().id(3L).name("test3").build();
        Song song4 = Song.builder().id(4L).name("test4").build();

        Page<SongDto> expectedList = new PageImpl<>(List.of(expectedSongDto1, expectedSongDto2, expectedSongDto3, expectedSongDto4));
        Page<Song> songs = new PageImpl<>(List.of(song1, song2, song3, song4));

        when(songRetriever.findAll(pageable)).thenReturn(songs);
        when(songMapper.mapSongToSongDto(song1)).thenReturn(expectedSongDto1);
        when(songMapper.mapSongToSongDto(song2)).thenReturn(expectedSongDto2);
        when(songMapper.mapSongToSongDto(song3)).thenReturn(expectedSongDto3);
        when(songMapper.mapSongToSongDto(song4)).thenReturn(expectedSongDto4);

        // When
        Page<SongDto> result = songFacade.findAll(pageable);

        // Then
        assertThat(result).isEqualTo(expectedList);
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent().get(3).name()).isEqualTo("test4");

        verify(songRetriever).findAll(pageable);
        verify(songMapper).mapSongToSongDto(song1);
        verify(songMapper).mapSongToSongDto(song2);

    }

    @Test
    void patchSong_should_delegate_to_SongUpdater() {
        long id = 1L;
        SongPatchRequestDto req = new SongPatchRequestDto("new", null, null, null, null);

        SongDto expected = new SongDto(id, "new", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        when(songUpdater.patch(id, req)).thenReturn(expected);

        // when
        SongDto result = songFacade.patchSong(id, req);

        // then
        assertThat(expected).isEqualTo(result);
        verify(songUpdater).patch(id, req);
    }

    @Test
    void deleteSong_should_delegate_to_SongDeleter() {
        long id = 1L;

        // when
        songFacade.deleteSong(id);

        // then
        verify(songDeleter).deleteSong(id);
    }

    @Test
    void assignGenre_should_delegate_to_SongAssigner() {
        long songId = 10L;
        long genreId = 20L;

        SongDto dto = new SongDto(songId, "mapped", releaseDate, 1L, "default", SongLanguageDto.ENGLISH);

        when(songAssigner.assignGenre(songId, genreId)).thenReturn(dto);

        // when
        SongDto result = songFacade.assignGenre(songId, genreId);

        // then
        assertThat(dto).isEqualTo(result);
        verify(songAssigner).assignGenre(songId, genreId);
    }

}
