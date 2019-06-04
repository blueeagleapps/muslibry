package com.example.muslibry5k.repositories;

import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> getAllByArtistsIsContaining(Artist artist);
    Optional<Song> getFirstByIsmn(String ismn);

}
