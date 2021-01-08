package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;

public class ManagementDisc {
    /*  Used enums because they are useful for storing constant values. PEGIRatings
        and Music Genres can be stored in an enum because we will never change these values.
     */

    private enum MusicGenre{
        POP{
            // override these methods to display a nice text to the user
            @Override
            public String toString() {
                return "Pop";
            }
        },
        JAZZ {
            @Override
            public String toString() {
                return "Jazz";
            }
        },
        ROCK{
            @Override
            public String toString() {
                return "Rock";
            }
        },
        HIPHOP{
            @Override
            public String toString() {
                return "Hip Hop";
            }
        },
        METAL {
            @Override
            public String toString() {
                return "Heavy Metal";
            }
        },
        OPERA{
            @Override
            public String toString() {
                return "Opera";
            }
        };

        // Retrieve all the values in the Enum and put them into an array
        static String[] getValues(){
            int index = 0;
            String[] values = new String[6];
            for (MusicGenre genre: MusicGenre.values()) {
                values[index] = genre.toString();  // Make sure it is in the nice format
                index++;
            }
            return values;
        }
    }

    private enum PEGIRating{
        U,
        PG,
        TWELVE {
            @Override
            public String toString() { return "12"; }
        },
        FIFTEEN {
            @Override
            public String toString() { return "15"; }
        },
        EIGHTEEN{
            @Override
            public String toString() { return "18"; }
        };

        // Retrieve all the values in the Enum and put them into an array
        static String[] getValues(){
            int index = 0;
            String[] values = new String[5];
            for (PEGIRating rating: PEGIRating.values()) {
                values[index] = rating.toString();
                index++;
            }
            return values;
        }
    }

    // Holds discs of MusicDiscs and GameDiscs
    // REMINDER: This is static because it needs to be the same for each instance created
    private static final Vector<Disc> discs = new Vector<>();

    // Get values for both enums
    private final String[] PEGIRatingValues = PEGIRating.getValues();
    private final String[] musicGenreValues = MusicGenre.getValues();

    // Get am/ov date for the creation/editing of discs
    public Date inputDate(){
        Date releaseDate = null;
        boolean valid = false;
        System.out.println("Release Date in format of DD/MM/YYYY:");
        do {
            String inputDate = Menu.keyboard.nextLine();
            try {
                releaseDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
                valid = true;
            } catch (ParseException e) {
                System.out.println("Invalid date. Please try again");
                Menu.keyboard.nextLine();
            }
        } while(!valid);

        return releaseDate;
    }

    // Gets user to input details about their Music Disc they wish to create
    public MusicDisc createAMusicDisc() {
        System.out.println("Title of Song:");
        String titleOfSong = Menu.keyboard.nextLine();

        // Get the genre
        String musicGenre = setOption(musicGenreValues, "Genres");

        // Get the date
        Date date = inputDate();

        System.out.println("Artist Name:");
        String artistName = Menu.keyboard.nextLine();

        System.out.println("Number of Songs:");
        int numOfSongs = Menu.getIntValidInput();

        System.out.println("Duration of Songs");
        int durationOfSongs = Menu.getIntValidInput();

        // Create a Music Disc Object
        MusicDisc musicDisc = new MusicDisc(titleOfSong, musicGenre, date, artistName, numOfSongs, durationOfSongs);

        // Add the new created disc to the list
        discs.add(musicDisc);

        // Return the musicDisc so we can add it as a record to our csv file later on
        return musicDisc;
    }

    // Gets user to input details about their Game  Disc they wish to create
    public GameDisc createAGameDisc() {
        System.out.println("Title of Game:");
        String titleOfGame = Menu.keyboard.nextLine();

        System.out.println("Genre:");
        String genre = Menu.keyboard.nextLine();

        // Get the date
        Date date = inputDate();

        // Get the PEGIRating
        String chosenPEGIRating = setOption(PEGIRatingValues, "PEGIRatings");

        System.out.println("Platform:");
        String platform = Menu.keyboard.nextLine();

        GameDisc gameDisc = new GameDisc(titleOfGame, genre, date, chosenPEGIRating, platform);

        // Add the new created disc to the list
        discs.add(gameDisc);

        // Return the gameDisc so we can add it as a record to our csv file later on
        return gameDisc;

    }

    // Remove a disc from the discs list
    public Disc removeDisc() {
        // Check if the list is empty:
        if (discs.isEmpty()) {
            System.out.println("You have not made any discs");
            return null;
        }
        // Get user to type what Title of Disc
        System.out.println("Enter the Title of Disc you wish to remove");
        String choice = Menu.keyboard.nextLine();

        // Find disc
        final int INDEX = findDisc(choice);

        // If the disc does not exist - exit
        if (INDEX == -1)
            return null;

        // Capture the disc so we can delete it in the file
        Disc oldDisc = discs.get(INDEX);

        // Using index remove the disc from the list
        discs.remove(INDEX);

        System.out.println(choice + " disc has been removed from the list");

        return oldDisc;
    }

    // Edit a Disc
    public RecordValues<String, String, Disc> editDisc() {
        // Check if the list is empty:
        if (discs.isEmpty()) {
            System.out.println("You have not made any discs");
            return null;
        }
        // Get user to type what Title of Disc
        System.out.println("Enter the Title of Disc you wish to edit");
        String choice = Menu.keyboard.nextLine();

        // Find disc
        final int INDEX = findDisc(choice);

        // If the disc does not exist - exit
        if (INDEX == -1)
            return null;

        // Retrieve the disc from the index
        Disc disc = discs.get(INDEX);

        // The user will be prevented with different fields to change based on what disc they made
        String[] options;
        if (disc instanceof MusicDisc)
            options = new String[]{"Title", "Genre", "Release Date", "Artist", "Number of Songs", "Duration of Songs"};

        else
            options = new String[]{"Title", "Genre", "Release Date", "PEGIRating", "Platform"};

        // Get the user to input the field they wish to change
        String editOption = setOption(options, "Options to edit for " + choice + " disc:");

        // User will now input a new value for the disc
        System.out.println("Please enter the new value for " + editOption + " of the " + choice + " disc");

        // Used for the file handling
        String oldTitle = disc.getTitle();

        // Check if the disc is an instance of the MusicDisc
        if (disc instanceof MusicDisc) {
            MusicDisc musicDisc = (MusicDisc) disc;


            // Check which option the user put and get a new value for it
            switch (editOption) {
                case "Title" -> {
                    String newValue = Menu.keyboard.nextLine();
                    musicDisc.setTitle(newValue);
                }
                case "Genre" -> {
                    String newValue = setOption(musicGenreValues, "Genres:");
                    musicDisc.setGenre(newValue);
                }
                case "Artist" -> {
                    String newValue = Menu.keyboard.nextLine();
                    musicDisc.setArtist(newValue);
                }
                case "Release Date" -> {
                    Date newValue = inputDate();
                    musicDisc.setReleaseDate(newValue);
                }
                case "Number of Songs" -> {
                    int newValue = Menu.getIntValidInput();
                    musicDisc.setNumOfSongs(newValue);
                }
                case "Duration of Songs" -> {
                    int newValue = Menu.getIntValidInput();
                    musicDisc.setDurationOfSongs(newValue);
                }
            }
        }
        // Check if the disc is an instance of GameDisc
        else {
            GameDisc gameDisc = (GameDisc) disc;

            // Check which option the user put and get a new value for it
            switch (editOption) {
                case "Title" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setTitle(newValue);
                }
                case "Genre" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setGenre(newValue);
                }
                case "Release Date" -> {
                    Date newValue = inputDate();
                    gameDisc.setReleaseDate(newValue);
                }
                case "PEGIRating" -> {
                    String newValue = setOption(PEGIRatingValues, "PEGIRatings:");
                    gameDisc.setPEGIRating(newValue);
                }
                case "Platform" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setPlatform(newValue);
                }
            }
        }

        System.out.println("Your " + disc.getTitle() + " disc has now been changed");

        /* We now need to update our file, so we can send in a Record which contains the oldTitle, edit option the user
            chose and the disc object so we can update it on the file
        */
        return new RecordValues<>(oldTitle, editOption, disc);
    }

    // Get user input to search for a Disc by Title
    public void getUserInputToSearchForDisc(){
        // Prevent user from inputting anything if the list is empty
        if (discs.isEmpty()) {
            System.out.println("You have not made any discs");
            return;
        }

        // Get the user input
        System.out.println("Enter the Title of Disc you wish to search");
        String title = Menu.keyboard.nextLine();
        searchForDisc(title);
    }

    // Search for a disc by title and display its details
    public boolean searchForDisc(String title) {
        // Find disc
        final int INDEX = findDisc(title);

        // If the disc does not exist - exit
        if(INDEX == -1)
            return false;

        // Retrieve the disc at the index position
        Disc disc = discs.get(INDEX);

        LinkedHashMap<String, String> discValues;
        // Check if disc is an instance of MusicDisc
        if (disc instanceof MusicDisc) {
            MusicDisc musicDisc = (MusicDisc) disc;
            // Get the details for that class
            discValues = musicDisc.displayDetailsOfDisc();
        } else { // disc would be an instance of GameDisc
            GameDisc gameDisc = (GameDisc) disc;
            // Get the details for that class
            discValues = gameDisc.displayDetailsOfDisc();

        }
        // Display the details to user
        System.out.println(discValues);

        // Disc was displayed to the user
        return true;
    }

    // Make sure that Title of the Disc does exist
    private int findDisc(String choice) {
        for (int i = 0; i < discs.size(); i++) {
            // Retrieve the first disc in the list at its index position
            Disc currentDisc = discs.get(i);
            // Check if the list exists based on the Title
            if (currentDisc.getTitle().equals(choice)) {
                return i; // return the index position of that element
            }
        }
        // If the disc could not be found in the list - show message
        System.out.println(choice + " disc does not exist");
        return -1;
    }

    // Using a given list, display the list and allow the user to pick an option.
    private String setOption(String[] list, String message) {
        boolean validOption;
        String choice;
        do {
            System.out.println(message); // Display what we are doing
            displayList(list);  // Display the Options to the user
            System.out.println("Please enter your choice:");
            int option = Menu.getIntValidInput(); // Get the position of the option in the list
            choice = getChosenOption(option, list); // Get the actual option the user chose
            validOption = !choice.equals(""); // If the user entered an invalid option, set to false and loop again
        } while (!validOption);

        return choice;
    }

    // Display the options available to the user
    private void displayList(String[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.println("\t" + (i + 1) + ": " + list[i]);  // i + 1 to get the position
        }
    }

    // User inputs a number (the position) based on the option that they want - get what they actually chose
    private String getChosenOption(int chosen, String[] list) {
        try {
            return (list[chosen - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("This is not a valid option. Please try again");
            return ""; // Return an empty string if the user has entered an invalid option
        }
    }

    // Add a disc to the list
    public void addDiscToList(Disc disc){
        discs.add(disc);
    }

}
