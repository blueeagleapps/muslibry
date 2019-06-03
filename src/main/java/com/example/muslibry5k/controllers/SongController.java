package com.example.muslibry5k.controllers;

import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SongController {

    private SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @RequestMapping(value = {"/songs" , "song/list"})
    public String getSongs(Model model) {
        model.addAttribute("songs", songRepository.findAll());
        return "song/list";
    }

    @RequestMapping("/song/{id}/show")
    public String getSongDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("song", songRepository.findById(id).get());
        return "song/show";
    }

    @RequestMapping("/song/{id}/delete")
    public String deleteSong(@PathVariable("id") Long id) {
        songRepository.deleteById(id);
        return "redirect:/songs";
    }
}
