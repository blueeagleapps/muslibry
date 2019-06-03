package com.example.muslibry5k.controllers;

import com.example.muslibry5k.commands.ArtistCommand;
import com.example.muslibry5k.converters.ArtistCommandToArtist;
import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MuslibryController {

    @RequestMapping(value = {"/"})
    public String getArtists() {
        return "index";
    }

}
