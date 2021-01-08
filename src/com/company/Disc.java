package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public abstract class Disc{
    private String title;
    private String genre;
    private Date releaseDate;

    public Disc(String name, String genre, Date date){
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

    // Return the release Date to the user as as a String
    public String getReleaseDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.releaseDate);

    }
    public void setReleaseDate(Date releaseDate){ this.releaseDate = releaseDate;}

    // Classes that inherit this class must have a method which displays the details of the disc in a Linked Hash Map
    public abstract LinkedHashMap<String, String> displayDetailsOfDisc();

}
