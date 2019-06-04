package com.example.muslibry5k.repositories;

import com.example.muslibry5k.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Optional<Artist> getFirstByFirstNameAndLastName(String firstName, String lastName);
    Optional<Artist> getFirstByFirstName(String firstName);
}
