package com.example.muslibry5k.controllers.integrations;

import com.example.muslibry5k.commands.integrations.SpotifyAlbumSearchForm;
import com.example.muslibry5k.model.Publisher;
import com.example.muslibry5k.model.Song;
import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.PublisherRepository;
import com.example.muslibry5k.repositories.SongRepository;
import com.example.muslibry5k.services.integrations.SpotifyService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;


/**
 * Created by pk on 2019-06-01
 */
@Controller
public class SpotifyController {

    private SongRepository songRepository;
    private ArtistRepository artistRepository;
    private SpotifyService spotifyService;
    private PublisherRepository publisherRepository;

    public SpotifyController(SongRepository songRepository, ArtistRepository artistRepository, SpotifyService spotifyService, PublisherRepository publisherRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.spotifyService = spotifyService;
        this.publisherRepository = publisherRepository;
    }

    private final String CLIENT_ID = "51f21918672b4e71ad5db600317e1d61";
    private final String CLIENT_SECRET = "ea2371fa0a2449e6b265f6d494322fec";

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .build();

    private final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    private ClientCredentials clientCredentials = null;

    @RequestMapping(value = {"/spotify/albums"}, method = RequestMethod.GET)
    public String showAlbumSearch(Model model) {

        SpotifyAlbumSearchForm albumSearchForm = new SpotifyAlbumSearchForm();
        model.addAttribute("albumSearchForm", albumSearchForm);

        return "integrations/spotify/album/search";
    }

    @RequestMapping(value = {"/spotify/albums"}, method = RequestMethod.POST)
    public String showAlbumsForImport(Model model,
              @ModelAttribute("albumSearchForm") SpotifyAlbumSearchForm albumSearchForm) {

        String query = albumSearchForm.getQuery();

        if (query != null && query.length() > 0) {
            model.addAttribute("albums", spotifyService.getAlbums(query));
        }

        return "integrations/spotify/album/list";
    }

    @RequestMapping("/spotify/album/{albumId}/show")
    public String getAlbumDetails(Model model, @PathVariable("albumId") String albumId) {
        model.addAttribute("album", spotifyService.getAlbum(albumId));
        model.addAttribute("tracks", spotifyService.getAlbumsTracks(albumId));
        return "integrations/spotify/album/show";
    }

    @RequestMapping(value = {"/spotify/album/{albumId}/save"}, method = RequestMethod.GET)
    public String addAlbumSongsToDB(@PathVariable("albumId") String albumId) {

        Track[] tracks = spotifyService.getAlbumsTracks(albumId);

        for (Track track : tracks) {
            Artist artist = new Artist(track.getArtists()[0].getName(), "[Spotify]", "Spotify: NDA");
            Optional<Song> songOptional = songRepository.getFirstByIsmn(track.getId());

            Optional<Publisher> publisherOptional = publisherRepository.getPublisherByName("Spotify: NDA");

            Song song = songOptional.isPresent() ? songOptional.get() : new Song(track.getName(),
                    "Spotify: NDA", track.getId(), "Spotify: NDA");

            if (publisherOptional.isPresent()) {
                song.setPublisher(publisherOptional.get());
            } else {
                song.setPublisher(publisherRepository.getPublisherByName("Unknown").get());
            }

            Optional<Artist> fromDatabaseArtist = artistRepository.getFirstByFirstName(artist.getFirstName());
            if (fromDatabaseArtist.isPresent()) {
                song.getArtists().add(fromDatabaseArtist.get());
                fromDatabaseArtist.get().getSongs().add(song);
            } else {
                song.getArtists().add(artist);
                artist.getSongs().add(song);
                artistRepository.save(artist);
            }
            songRepository.save(song);
        }

        return "redirect:/songs";
    }


}
