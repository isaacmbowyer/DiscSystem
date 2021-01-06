package com.company;


public class MusicDisc extends Disc{
    private String artist;
    private int numOfSongs;
    private int durationOfSongs;

    public MusicDisc(String title, String genre, String releaseDate, String artistName, int songs, int songsDuration){
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


}
