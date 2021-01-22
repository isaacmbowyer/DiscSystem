package com.company;


import java.util.Date;
import java.util.LinkedHashMap;


public class GameDisc extends Disc {
    private String PEGIRating;
    private String platform;

    public GameDisc(String title, String genre, Date releaseDate, String PEGIRating, String gamePlatform){
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

    // Display the details of the Game disc using a Linked Hash Map
    @Override
    public LinkedHashMap<String, String> displayDetailsOfDisc() {
        LinkedHashMap<String, String> detailsOfDisc = new LinkedHashMap<>();
        detailsOfDisc.put("Title", this.getTitle());
        detailsOfDisc.put("Genre", this.getGenre());
        detailsOfDisc.put("Release Date", this.getReleaseDate());
        detailsOfDisc.put("PEGIRating", this.PEGIRating);
        detailsOfDisc.put("Platform", this.platform);
        return detailsOfDisc;
    }


}
