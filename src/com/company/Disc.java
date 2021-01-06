package com.company;


// Super abstract class
public abstract class Disc{
    private String title;
    private String genre;
    private String releaseDate;

    public Disc(String name, String genre, String date){
        this.title = name;
        this.genre = genre;
        this.releaseDate = date;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){ this.title = title; }

    public String getGenre(){
        return genre;
    }

    public void  setGenre(String genre){ this.genre = genre;}

    public String getReleaseDate(){ return releaseDate; }

    public void setReleaseDate(String releaseDate){ this.releaseDate = releaseDate;}

}
