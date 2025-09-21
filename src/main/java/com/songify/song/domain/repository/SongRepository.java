package com.songify.song.domain.repository;

import com.songify.song.infrastructure.controller.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    private final Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("Bitwa", "Kamraci"),
            2, new Song("Anthem of Serbia", "Some Bosniak"),
            3, new Song("Yo yo yo rap", "Jessy Pinkman"),
            4, new Song("My pierwsza brygda", "Józef Piłsudski")
    ));

    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    public Map<Integer, Song> findAll() {
        return database;
    }
}
