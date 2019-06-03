package com.example.muslibry5k.controllers;

import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ArtistController {

    private ArtistRepository artistRepository;
    private SongRepository songRepository;

    public ArtistController(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @RequestMapping(value = {"/artists", "/artist/list"})
    public String getArtists(Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "artist/list";
    }

    @RequestMapping("/artist/{id}/songs")
    public String getArtistSongs(Model model, @PathVariable("id") Long id) {
        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {
            model.addAttribute("songs", songRepository.getAllByArtistsIsContaining(artist.get()));
            model.addAttribute("filter", "artist: " + artist.get().getFirstName() + " " + artist.get().getLastName());
        } else {
            model.addAttribute("songs", new ArrayList<>());
            model.addAttribute("filter", "artist for this id doesn't exist");
        }

        return "song/list";
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
