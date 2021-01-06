package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ManagementFile {
    private static String path = "discsRecords.csv";

    // Write a disc to the file
    public static void writeToFile(Disc disc) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));

            if (disc instanceof MusicDisc) {
                // Retrieve all the attributes in Music disc
                MusicDisc musicDisc = (MusicDisc) disc;
                String title = musicDisc.getTitle();
                String genre = musicDisc.getGenre();
                String artist = musicDisc.getArtist();
                String releaseDate = musicDisc.getReleaseDate();
                String numOfSongs = String.valueOf(musicDisc.getNumOfSongs());
                String durationOfSongs = String.valueOf(musicDisc.getDurationOfSongs());

                // Write the record
                writer.write(title + "," +
                        genre + "," +
                        artist + "," +
                        releaseDate + "," +
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
    public static boolean readAllRecords() {
        String line = "";
        try {
            File file = new File(path);

            // If there is no discs stored, then output a message
            if (file.length() == 0) {
                System.out.println("There is no discs stored");
                return false;
            }
            BufferedReader reader = new BufferedReader(new FileReader(path));

             // Get all the records in order
            ArrayList<String[]> records = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }

            // Change the order of the list
            ListInReverse(records, 0, records.size() - 1);

            // Display the reversed list
            for (String[] record : records)
                displayRecordInHashMap(record);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    // Change the order of the list in reverse
    public static void ListInReverse(ArrayList<String[]> records, int startIndex, int endIndex){
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
        ListInReverse(records, startIndex, endIndex);


    }
    // Show a HashMap of the details of the record depending on the size of the array
    public static void displayRecordInHashMap(String[] currentRecord){
        LinkedHashMap<String, String> detailsOfDisc =new LinkedHashMap<>();
        // Get all the attributes for that record
        if (currentRecord.length == 6){
            detailsOfDisc.put("Title", currentRecord[0]);
            detailsOfDisc.put("Genre", currentRecord[1]);
            detailsOfDisc.put("Artist", currentRecord[2]);
            detailsOfDisc.put("Release Date", currentRecord[3]);
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
    public static void editRecord(RecordValues<String, String, Disc> record) {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<String[]> records = new ArrayList<>();

            // While the next line is not null - take that line
            while ((line = reader.readLine()) != null) {
                // Parse that line into an array
                String[] values = line.split(",");

                // Check Title first, remove checking for further records
                if (values[0].equals(record.getOldTitle())) {
                    Disc disc = record.getDisc();

                    // Get what the user wanted to change in the disc so we can update it
                    String editOption = record.getEditOption();

                    if (record.getDisc() instanceof MusicDisc) {
                        MusicDisc musicDisc = (MusicDisc) disc;
                        // [Title, Genre, Artist, ReleaseDate, NumOfSongs, DurationOfSongs]

                        // Check which field has been changed
                        switch (editOption) {
                            case "Title" -> values[0] = musicDisc.getTitle();
                            case "Genre" -> values[1] = musicDisc.getGenre();
                            case "Artist" -> values[2] = musicDisc.getArtist();
                            case "Release Date" -> values[3] = musicDisc.getReleaseDate();
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
    public static void deleteRecordFromFile(Disc disc){
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

    public static void updateFile(String[] record){
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

    // In order for this to show the program must need to end otherwise it takes a long time to update
    public static boolean renameFile(){
        System.out.println("Current File Name: " + path);
        System.out.println("Please enter the new File Name:");
        String newFilePath = Menu.keyboard.nextLine();

        // If the user has not put the extension, add it
        if (!newFilePath.contains(".csv"))
            newFilePath += ".csv";

        File file = new File(path);
        File newFile = new File(newFilePath);

        // Rename the current file name to the new file name
        if(file.renameTo(newFile)){
            // Update the path
            path = newFilePath;
            System.out.println("The new File Name is now: " + path);
            return true;
        }
        // If it cannot be renamed:
        System.out.println("This is already the File's name");
        return false;

    }
}




