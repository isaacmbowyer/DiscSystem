package com.company;

import java.util.Date;
import java.util.LinkedHashMap;

public class MusicDisc extends Disc{
    private String artist;
    private int numOfSongs;
    private int durationOfSongs;

    public MusicDisc(String title, String genre, Date releaseDate, String artistName, int songs, int songsDuration){
        super(title, genre, releaseDate);
        this.artist = artistName;
        this.numOfSongs = songs;
        this.durationOfSongs = songsDuration;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist(String artist){ this.artist = artist;}

    public int getNumOfSongs(){
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) { this.numOfSongs = numOfSongs;}

    public int getDurationOfSongs(){ return durationOfSongs; }

    public void setDurationOfSongs(int durationOfSongs) { this.durationOfSongs = durationOfSongs;}

    // Display the details of the Music disc using a Linked Hash Map
    @Override
    public LinkedHashMap<String, String> displayDetailsOfDisc() {
        LinkedHashMap<String, String> detailsOfDisc = new LinkedHashMap<>();
        detailsOfDisc.put("Title", this.getTitle());
        detailsOfDisc.put("Genre", this.getGenre());
        detailsOfDisc.put("Release Date",this.getReleaseDate());
        detailsOfDisc.put("Artist", this.artist);
        detailsOfDisc.put("Number of Songs", String.valueOf(this.numOfSongs));
        detailsOfDisc.put("Duration of Songs", String.valueOf(this.durationOfSongs));
        return detailsOfDisc;
    }
}
