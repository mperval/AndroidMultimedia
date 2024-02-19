package ies.carrillo.android.androidmultimedia.models;

import java.io.Serializable;

public class Song implements Serializable {

    private String title;
    private String artist;

    public Song() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
