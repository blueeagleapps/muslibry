package com.example.muslibry5k.repositories;

import com.example.muslibry5k.model.Publisher;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    Optional<Publisher> getPublisherByName(String name);
}
