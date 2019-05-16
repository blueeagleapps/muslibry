package com.example.muslibry5k.tools;

import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Song;
import com.example.muslibry5k.repositories.ArtistRepository;
import com.example.muslibry5k.repositories.SongRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DBInflater implements ApplicationListener<ContextRefreshedEvent> {

    public DBInflater(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    private SongRepository songRepository;
    private ArtistRepository artistRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        Artist johnny = new Artist("Johnny", "Cash", "cash");
        Song aBoyNamedSue = new Song("A Boy Named Sue", "country", "1983129873",
                "1969", "Columbia Records");
        johnny.getSongs().add(aBoyNamedSue);
        aBoyNamedSue.getArtists().add(johnny);
        artistRepository.save(johnny);
        songRepository.save(aBoyNamedSue);


        Artist bob = new Artist("Bob", "Marley", "BoB");
        Song threeLittleBirds = new Song("Three little birds", "Raegge", "98172391123",
                "1977", "Island Records");
        bob.getSongs().add(threeLittleBirds);
        threeLittleBirds.getArtists().add(bob);
        artistRepository.save(bob);
        songRepository.save(threeLittleBirds);



        Artist freddie = new Artist("Freddie", "Mercury", "Freddie");
        Song callingAllGirls = new Song("Calling All Girls", "Many", "09309823091",
                "1982", "EMI");
        freddie.getSongs().add(callingAllGirls);
        callingAllGirls.getArtists().add(freddie);
        artistRepository.save(freddie);
        songRepository.save(callingAllGirls);

    }
}
