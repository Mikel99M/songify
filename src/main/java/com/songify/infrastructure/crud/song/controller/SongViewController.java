package com.songify.infrastructure.crud.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    private Map<Integer, String> database = new HashMap<>(Map.of(
            1, "Bitwa",
            2, "Anthem of Serbia",
            3, "Yo yo yo rap",
            4, "My pierwsza brygda"
    ));

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {

        model.addAttribute("songMap", database);
        return "songs";
    }
}
