package com.example.muslibry5k.converters;

import com.example.muslibry5k.commands.ArtistCommand;
import com.example.muslibry5k.commands.PublisherCommand;
import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Publisher;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by pk on 2019-06-03
 */
@Component
public class PublisherCommandToPublisher implements Converter<PublisherCommand, Publisher> {

    @Synchronized
    @Nullable
    @Override
    public Publisher convert(PublisherCommand source) {
        if (source == null) {
            return null;
        }

        final Publisher publisher = new Publisher();
        publisher.setName(source.getName());
        publisher.setNip(source.getNip());
        publisher.setAddress(source.getAddress());

        return publisher;
    }
}
