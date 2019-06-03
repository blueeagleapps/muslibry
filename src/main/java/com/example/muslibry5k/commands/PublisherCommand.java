package com.example.muslibry5k.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pk on 2019-06-03
 */

@Getter
@Setter
@NoArgsConstructor
public class PublisherCommand {
    private Long id;
    private String name;
    private String nip;
    private String address;
}
