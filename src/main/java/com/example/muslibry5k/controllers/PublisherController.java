package com.example.muslibry5k.controllers;

import com.example.muslibry5k.commands.PublisherCommand;
import com.example.muslibry5k.converters.PublisherCommandToPublisher;
import com.example.muslibry5k.model.Publisher;
import com.example.muslibry5k.repositories.PublisherRepository;
import com.example.muslibry5k.repositories.PublisherRepository;
import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PublisherController {

    private PublisherRepository publisherRepository;
    private PublisherCommandToPublisher publisherCommandToPublisher;

    public PublisherController(PublisherRepository publisherRepository, PublisherCommandToPublisher publisherCommandToPublisher) {
        this.publisherRepository = publisherRepository;
        this.publisherCommandToPublisher = publisherCommandToPublisher;
    }

    @RequestMapping(value = {"/publishers", "/publisher/list"})
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherRepository.findAll());
        return "publisher/list";
    }

    @RequestMapping("/publisher/{id}/show")
    public String getPublisherDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("publisher", publisherRepository.findById(id).get());
        return "publisher/show";
    }

    @RequestMapping("/publisher/{id}/delete")
    public String deletePublisher(@PathVariable("id") Long id) {
        publisherRepository.deleteById(id);
        return "redirect:/publishers";
    }

    @GetMapping
    @RequestMapping("/publisher/new")
    public String newSong(Model model){
        model.addAttribute("publisher", new PublisherCommand());
        return "publisher/addedit";
    }

    @PostMapping("publisher")
    public String saveOrUpdate(@ModelAttribute PublisherCommand command){

        Optional<Publisher> publisherOptional = publisherRepository.getPublisherByName(command.getName());

        if (!publisherOptional.isPresent()) {
            Publisher detachedPublisher = publisherCommandToPublisher.convert(command);
            Publisher savedPublisher = publisherRepository.save(detachedPublisher);
            return "redirect:/publisher/" + savedPublisher.getId() + "/show";
        } else {
            //TODO: error message to template
            System.out.println("Sorry, there's such publisher in db");
            return "redirect:/publisher/" + publisherOptional.get().getId() + "/show";
        }
    }
}
