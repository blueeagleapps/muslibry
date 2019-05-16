package com.example.muslibry5k.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private String ismn;
    private String year;
    private String recordLabel;

    @ManyToMany
    private Set<Artist> artists = new HashSet<>();
    public Song() {
    }
    public Song(String title, String genre, String ismn, String year, String recordLabel) {
        this.title = title;
        this.genre = genre;
        this.ismn = ismn;
        this.year = year;
        this.recordLabel = recordLabel;
    }

    public Song(String title, String genre, String ismn, String year, String recordLabel, Set<Artist> artists) {
        this.title = title;
        this.genre = genre;
        this.ismn = ismn;
        this.year = year;
        this.recordLabel = recordLabel;
        this.artists = artists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsmn() {
        return ismn;
    }

    public void setIsmn(String ismn) {
        this.ismn = ismn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", ismn='" + ismn + '\'' +
                ", year='" + year + '\'' +
                ", recordLabel='" + recordLabel + '\'' +
                ", artists=" + artists +
                '}';
    }
}
