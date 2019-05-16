package com.example.muslibry5k.repositories;

import com.example.muslibry5k.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
