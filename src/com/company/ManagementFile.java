package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ManagementFile {
    private static String path = "records.csv";

    // Write a disc to the file
    public void writeToFile(Disc disc) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));

            if (disc instanceof MusicDisc) {
                // Retrieve all the attributes in Music disc
                MusicDisc musicDisc = (MusicDisc) disc;
                String title = musicDisc.getTitle();
                String genre = musicDisc.getGenre();
                String releaseDate = musicDisc.getReleaseDate();
                String artist = musicDisc.getArtist();
                String numOfSongs = String.valueOf(musicDisc.getNumOfSongs());
                String durationOfSongs = String.valueOf(musicDisc.getDurationOfSongs());

                // Write the record
                writer.write(title + "," +
                        genre + "," +
                        releaseDate + "," +
                        artist + "," +
                        numOfSongs + "," +
                        durationOfSongs);

            } else {
                // Retrieve all the attributes in Game disc
                GameDisc gameDisc = (GameDisc) disc;
                String title = gameDisc.getTitle();
                String genre = gameDisc.getGenre();
                String releaseDate = gameDisc.getReleaseDate();
                String PEGIRating = gameDisc.getPEGIRating();
                String platform = gameDisc.getPlatform();

                // Write the record
                writer.write(title + "," +
                        genre + "," +
                        releaseDate + "," +
                        PEGIRating + "," +
                        platform);

            }

            // New line for the next disc
            writer.newLine();

            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gets all the records and puts them in an array
    public ArrayList<String[]> readAllRecords() {
        String line = "";

        File file = new File(path);

        // If there is no discs stored, then output a message
        if (file.length() == 0) {
            System.out.println("There is no discs stored");
            return null;
        }
            // Store each line into an List of arrays
            ArrayList<String[]> records = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            // Get all the records in order from the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }

            // Reverse the order of the list
            listInReverse(records, 0, records.size() - 1);

            // Display the reversed list
            for (String[] record : records)
                displayRecordInHashMap(record);


        } catch (IOException e){
            e.printStackTrace();
        }

        return records;

    }
    // Change the order of the list in reverse
    public void listInReverse(ArrayList<String[]> records, int startIndex, int endIndex){
        // Base Case
        if (startIndex > endIndex) {
           return;  // no more records to reverse in the list
        }

        // Using the start Index of the record store it in tempDisc
        String[] tempRecord = records.get(startIndex);

        // Get the last record and replace it with the record at the start
        records.set(startIndex, records.get(endIndex));

        // Using the tempRecord, replace the end record with the disc we just replaced
        records.set(endIndex, tempRecord);

        startIndex +=1;  // Move towards the end of list by 1
        endIndex -=1;    // Move towards the start of the list by 1

        // Call the reverseList again
        listInReverse(records, startIndex, endIndex);


    }
    // Show a HashMap of the details of the record depending on the size of the array
    public void displayRecordInHashMap(String[] currentRecord){
        LinkedHashMap<String, String> detailsOfDisc =new LinkedHashMap<>();
        // Get all the attributes for that record
        if (currentRecord.length == 6){
            detailsOfDisc.put("Title", currentRecord[0]);
            detailsOfDisc.put("Genre", currentRecord[1]);
            detailsOfDisc.put("Release Date", currentRecord[2]);
            detailsOfDisc.put("Artist", currentRecord[3]);
            detailsOfDisc.put("Number of Songs", currentRecord[4]);
            detailsOfDisc.put("Duration of Songs", currentRecord[5]);
        } else{
            detailsOfDisc.put("Title", currentRecord[0]);
            detailsOfDisc.put("Genre", currentRecord[1]);
            detailsOfDisc.put("Release Date", currentRecord[2]);
            detailsOfDisc.put("PEGIRating", currentRecord[3]);
            detailsOfDisc.put("Platform", currentRecord[4]);
        }
        // Show the Hash Map to the user
        System.out.println(detailsOfDisc);
    }

    // Edit a disc on the file
    public void editRecord(RecordValues<String, String, Disc> record) {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<String[]> records = new ArrayList<>();

            // While the next line is not null - take that line
            while ((line = reader.readLine()) != null) {
                // Parse that line into an array
                String[] values = line.split(",");

                // Check Title first, removing checking for further discs, if it wasn't the edited one
                if (values[0].equals(record.getOldTitle())) {
                    Disc disc = record.getDisc();

                    // Get what the user wanted to change in the disc so we can update it
                    String editOption = record.getEditOption();

                    if (record.getDisc() instanceof MusicDisc) {
                        MusicDisc musicDisc = (MusicDisc) disc;
                        // [Title, Genre, ReleaseDate, Artist, NumOfSongs, DurationOfSongs]

                        // Check which field has been changed
                        switch (editOption) {
                            case "Title" -> values[0] = musicDisc.getTitle();
                            case "Genre" -> values[1] = musicDisc.getGenre();
                            case "Release Date" -> values[2] = musicDisc.getReleaseDate();
                            case "Artist" -> values[3] = musicDisc.getArtist();
                            case "Number of Songs" -> values[4] = String.valueOf(musicDisc.getNumOfSongs());
                            default -> values[5] = String.valueOf(musicDisc.getDurationOfSongs());

                        }

                    } else {  // Edited disc was a Game Disc
                        // [Title, Genre, ReleaseDate, PEGIRatings, Platform]
                        GameDisc gamedisc = (GameDisc) disc;

                        // Check which field has been changed
                        switch (editOption) {
                            case "Title" -> values[0] = gamedisc.getTitle();
                            case "Genre" -> values[1] = gamedisc.getGenre();
                            case "Release Date" -> values[2] = gamedisc.getReleaseDate();
                            case "PEGIRating" -> values[3] = gamedisc.getReleaseDate();
                            default -> values[4] = gamedisc.getPlatform();
                        }
                    }
                }

                // Add records to list
                records.add(values);
            }

            // Wipe out everything
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));

            // Update file with updated records
            for (String[] newRecord : records) {
                updateFile(newRecord);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete the disc from the file
    public void deleteRecordFromFile(Disc disc){
        // Get the title from the deleted disc
        String removeRecord = disc.getTitle();
        String line = "";
        try {
            ArrayList<String[]> records = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(path));

            // While the next line is not null - take that line
            while ((line = reader.readLine()) != null) {
                // Parse that line into an array

                String[] recordValues = line.split(",");

                // Check what row number the record is at so we can delete it
                if (!(recordValues[0].equals(removeRecord)))
                    records.add(recordValues);

            }
            // Wipe out everything
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));

            // Update file with records we want to keep
            for(String[] record: records){
                updateFile(record);
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile(String[] record){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));

            // Get each value in the record and write it to the file
            for(int i = 0; i < record.length; i++){
                writer.write(record[i]);
                // We don't want a comma after the last value
                if(!(i == record.length -1)){
                    writer.write(",");
                }
            }
            // New line for the next record
            writer.newLine();

            // Close the writer
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gets the user to input a new path name for the file
    public void getUserInputToRenameFile(){
        System.out.println("Current File Name: " + path);
        System.out.println("Please enter the new File Name:");
        String newPath = Menu.keyboard.nextLine();
        renameFile(newPath);
    }

    // Rename the csv file
    public String renameFile(String newPath) {
        // If the NewPath equals the current path, display a message to user
        if(newPath.equals(path)) {
            System.out.println("This is currently already the path name");
            return null;
        }

        // If the newPath does not contain an extension, add .csv to the new path
        if(!(newPath.contains(".csv")))
            newPath += ".csv";

        File file = new File(path);


        File newFile = new File(newPath);

        // Rename the current file name to the new file name
        file.renameTo(newFile);

        // Update the path
        path = newPath;

        System.out.println("The file's path is now: " + path );

        return path;
    }
}