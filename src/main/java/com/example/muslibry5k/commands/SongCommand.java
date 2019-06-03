package com.example.muslibry5k.commands;

import com.example.muslibry5k.model.Artist;
import com.example.muslibry5k.model.Publisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pk on 2019-06-03
 */

@Getter
@Setter
@NoArgsConstructor
public class SongCommand {
    private Long id;
    private String title;
    private String genre;
    private String ismn;
    private String year;
    private Long publisherId;
    private Long artistId;
}
