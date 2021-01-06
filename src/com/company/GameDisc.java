package com.company;

// Subclass of the super class
public class GameDisc extends Disc {
    private String PEGIRating;
    private String platform;


    public GameDisc(String title, String genre, String releaseDate, String PEGIRating, String gamePlatform){
        super(title, genre, releaseDate);
        this.PEGIRating = PEGIRating;
        this.platform = gamePlatform;
    }

    public String getPEGIRating(){
        return PEGIRating;
    }

    public void setPEGIRating(String PEGIRating) { this.PEGIRating = PEGIRating;}

    public String getPlatform(){ return platform; }

    public void setPlatform(String platform){  this.platform = platform;}




}
