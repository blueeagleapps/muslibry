package com.example.muslibry5k.controllers;

import com.example.muslibry5k.repositories.ArtistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArtistController {

    private ArtistRepository artistRepository;

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @RequestMapping(value = {"/artists", "/artist/list"})
    public String getArtists(Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "artist/list";
    }

    @RequestMapping("/artist/{id}/show")
    public String getArtistDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("artist", artistRepository.findById(id).get());
        return "artist/show";
    }

    @RequestMapping("/artist/{id}/delete")
    public String deleteArtist(@PathVariable("id") Long id) {
        artistRepository.deleteById(id);
        return "redirect:/artists";
    }
}
