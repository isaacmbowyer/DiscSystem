package com.company;

import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Vector;

public class ManagementDisc {
    private static final Vector<Disc> discs =new Vector<>();     // Holds discs of MusicDiscs and GameDiscs
    private static final String[] PEGIRatings = {"3", "7", "12", "16", "18"};
    private static final String[] musicGenres = {"Pop", "Jazz", "Rock", "Hip hop", "Heavy Metal"};

    // Gets user to input details about their Music Disc they wish to create
    public static MusicDisc createAMusicDisc(){
        System.out.println("Title of Song:");
        String titleOfSong = inputTitle();

        // Get the Genre:
        String musicGenre = setOption(musicGenres, "Genres");

        System.out.println("Release Date:");
        String date = Menu.keyboard.nextLine();

        System.out.println("Artist Name:");
        String artistName = Menu.keyboard.nextLine();

        System.out.println("Number of Songs:");
        int numOfSongs = Menu.getIntValidInput();

        System.out.println("Duration of Songs");
        int durationOfSongs = Menu.getIntValidInput();

        // Create a Music Disc Object
        MusicDisc musicDisc =new MusicDisc(titleOfSong, musicGenre, date, artistName, numOfSongs, durationOfSongs);

        // Add the new created disc to the list
        discs.add(musicDisc);

        // Return the musicDisc so we can add it as a record to our csv file later on
        return musicDisc;
    }

    // Gets user to input details about their Game  Disc they wish to create
    public static GameDisc createAGameDisc(){
        System.out.println("Title of Game:");
        String titleOfGame = inputTitle();

        System.out.println("Genre:");
        String genre = Menu.keyboard.nextLine();

        System.out.println("Release Date:");
        String releaseDate = Menu.keyboard.nextLine();

        // Get the PEGIRating
        String chosenPEGIRating = setOption(PEGIRatings, "PEGIRatings");

        System.out.println("Platform:");
        String platform = Menu.keyboard.nextLine();

        GameDisc gameDisc =new GameDisc(titleOfGame, genre, releaseDate, chosenPEGIRating, platform);

        // Add the new created disc to the list
        discs.add(gameDisc);

        // Return the gameDisc so we can add it as a record to our csv file later on
        return gameDisc;

    }


    // Remove a disc from the discs list
    public static Disc removeDisc(){
        // Check if the list is empty:
        if (discs.isEmpty()){
            System.out.println("You have not made any discs");
            return null;
        }
        // Get user to type what Title of Disc they want
        System.out.println("Enter the Title of Disc you wish to remove:");
        String choice = Menu.keyboard.nextLine();

        int index = findDisc(choice); // Make sure the disc can be found

        // The disc could not be found so exit
        if (index == -1)
            return null;

        // Capture the disc so we can delete it in the file
        Disc oldDisc =discs.get(index);

        // Using index remove the disc from the list
        discs.remove(index);

        System.out.println(choice + " disc has been removed from the list");

        return oldDisc;

    }

    // Edit a Disc
    public static RecordValues<String, String, Disc> editDisc() {
        // Check if the list is empty:
        if (discs.isEmpty()){
            System.out.println("You have not made any discs");
            return null;
        }
        // Get user to type what Title of Disc they want
        System.out.println("Enter the Title of Disc you wish to edit:");
        String choice = Menu.keyboard.nextLine();

        int index = findDisc(choice);  // Make sure the disc can be found

        // If the disc does not exist - exit
        if (index == -1)
            return null;


        // Retrieve the disc from the index
        Disc disc = discs.get(index);


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
        if (disc instanceof MusicDisc){
            MusicDisc musicDisc = (MusicDisc) disc;


            // Check which option the user put and get a new value for it
            switch(editOption) {
                case "Title" -> {
                    String newValue = inputTitle();
                    musicDisc.setTitle(newValue);
                }
                case "Genre" -> {
                    String newValue = setOption(musicGenres, "Genres:");
                    musicDisc.setGenre(newValue);
                }
                case "Artist" -> {
                    String newValue = Menu.keyboard.nextLine();
                    musicDisc.setArtist(newValue);
                }
                case "Release Date" -> {
                    String newValue = Menu.keyboard.nextLine();
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
            switch(editOption) {
                case "Title" -> {
                    String newValue = inputTitle();
                    gameDisc.setTitle(newValue);
                }
                case "Genre" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setGenre(newValue);
                }
                case "Release Date" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setReleaseDate(newValue);
                }
                case "PEGIRating" -> {
                    String newValue = setOption(PEGIRatings, "PEGIRatings:");
                    gameDisc.setPEGIRating(newValue);
                }
                case "Platform" -> {
                    String newValue = Menu.keyboard.nextLine();
                    gameDisc.setPlatform(newValue);
                }
            }
        }

        System.out.println("Your " + disc.getTitle() + " disc has now been changed");

        /* We now need to update our file, so we can send in a Record which contains the edit option the user
            chose and the cd so we can update it
        */
        return new RecordValues<>(oldTitle, editOption, disc);
    }

    /* Searches for a specific disc byt Title from discs list and displays its details in a LinkedHashMap so its
    easier for the user to read
    */
    public static void searchForDisc(){
        // Check if the list is empty:
        if (discs.isEmpty()){
            System.out.println("You have not made any discs");
            return;
        }
        // Get user to type what Title of Disc they want
        System.out.println("Enter the Title of Disc you wish to search:");
        String choice = Menu.keyboard.nextLine();

        int index = findDisc(choice); // Make sure the disc can be found

        // If the disc does not exist - exit
        if (index == -1)
            return;

        displayDisc(index);

    }

    // Reverse the order of discs in discs list using recursion
    public static void reverseList(int startIndex, int endIndex) {
        // Base Case
        if (startIndex >= endIndex) {
            return; // no more discs to reverse in the list
        }

        // Using the start Index of the disc store it in tempDisc
        Disc tempDisc = discs.get(startIndex);

        // Get the last disc and replace it with the disc at the start
        discs.set(startIndex, discs.get(endIndex));

        // Using the tempDisc, replace the end disc with the disc we just replaced
        discs.set(endIndex, tempDisc);

        startIndex +=1;  // Move towards the end of list by 1
        endIndex -=1;    // Move towards the start of the list by 1

        // Call the reverseList again
        reverseList(startIndex, endIndex);

    }

    // Display all the reversed discs to the User
    public static void displayAllDiscs(){
        System.out.println("Your discs:");
        for(int i = 0; i < discs.size(); i++)
            displayDisc(i);
    }

    // Get the last index of the discs list in order to reverse the list
    public static int getLastIndexOfDiscs(){
        return discs.size() - 1;
    }

    // Display the details of the disc in a Hash Map so the user can see the variables and values easily
    private static void displayDisc(int index){
        LinkedHashMap<String, String> detailsOfDisc =new LinkedHashMap<>();
        Disc disc = discs.get(index);

        // Get the inherited details first
        detailsOfDisc.put("Title", disc.getTitle());
        detailsOfDisc.put("Genre", disc.getGenre());
        detailsOfDisc.put("Release Date", disc.getReleaseDate());

        // Check if disc is an instance of MusicDisc and add the details for that class
        if(disc instanceof MusicDisc){
            MusicDisc musicDisc = (MusicDisc) disc;
            detailsOfDisc.put("Artist", musicDisc.getArtist());
            detailsOfDisc.put("Number of Songs", String.valueOf(musicDisc.getNumOfSongs()));
            detailsOfDisc.put("Duration of Songs", String.valueOf(musicDisc.getDurationOfSongs()));
        }
        else { // disc would be an instance of GameDisc so add all the details for that class
            GameDisc gameDisc = (GameDisc) disc;
            detailsOfDisc.put("PEGIRating", gameDisc.getPEGIRating());
            detailsOfDisc.put("Platform", gameDisc.getPlatform());
        }

        // Display the details of the disc as a String
        System.out.println(detailsOfDisc);
    }

    // Using a given list, display the list and allow the user to pick an option.
    private static String setOption(String[] list, String message){
        boolean validOption;
        String choice;
        do{
            System.out.println(message); // Display what we are doing
            displayList(list);  // Display the Options to the user
            System.out.println("Please enter your choice:");
            int option = Menu.getIntValidInput(); // Get the position of the option in the list
            choice = getChosenOption(option, list); // Get the actual option the user chose
            validOption = !choice.equals(""); // If the user entered an invalid option, set to false and loop again
        } while(!validOption);

        return choice;
    }

    // Display the options available to the user to the user
    private static void displayList(String[] list) {
        for(int i = 0; i < list.length; i++){
            System.out.println("\t" + (i + 1) + ": " + list[i]);  // i + 1 to get the position
        }
    }

    // User inputs a number (the position) based on the option that they want - get what they actually chose
    private static String getChosenOption(int chosen, String[] list) {
        try {
            return (list[chosen - 1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("This is not a valid option. Please try again");
            return ""; // Return an empty string if the user has entered an invalid option
        }
    }

    // Make sure that Title of the Disc does exist
    private static int findDisc(String choice){
       for (int i = 0; i < discs.size(); i++){
            // Retrieve the first disc in the list at its index position
            Disc currentDisc = discs.get(i);
            // Check if the list exists based on the Title
            if (currentDisc.getTitle().equals(choice)){
                return i; // return the index position of that element
            }
        }
        // The disc is not found in the list - show message
        System.out.println(choice + " disc does not exist");
        return -1;
    }

    // Prevent user from naming multiple titles
    public static String inputTitle(){
        boolean validInput = false;
        String newTitle;
        do{
            newTitle = Menu.keyboard.nextLine();
            // Iterate over the list checking how many times the title has occurred
            int times = 0;
            for (Disc disc: discs) {
                String title = disc.getTitle();
                if(title.equals(newTitle))
                    times ++;
            }
            if(times > 0){
                System.out.println("This title already exists");
                System.out.println("Please enter again");
            }
            else
                validInput = true;

        }
        while(!validInput);
        return newTitle;

    }
}
