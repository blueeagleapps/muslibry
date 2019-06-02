package com.example.muslibry5k.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nip;
    private String address;

    public Publisher() {
    }
    public Publisher(String name) {
        this.name = name;
    }
    public Publisher(String name, String nip, String address) {
        this.name = name;
        this.nip = nip;
        this.address = address;
    }

}
