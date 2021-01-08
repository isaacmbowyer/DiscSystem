package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static Scanner keyboard = new Scanner(System.in);

    // Menu system
    public static void main(){
        // Run the application until the user wishes to stop
        boolean quit = false; // Checks if user decides to quit or not
        do {
            ManagementFile fileManagement = new ManagementFile();
            ManagementDisc discManagement = new ManagementDisc();

            displayMenu();  // Display Menu to the user

            // Get user to input their choice
            System.out.println("Enter your choice:");
            int userChoice = getIntValidInput();

            // Check what the user chose and run that section of code
            switch (userChoice) {
                // Create a Music Disc
                case 1 -> {
                    System.out.println("Creating a Music Disc...");
                    // Create the Disc
                    MusicDisc musicDisc = discManagement.createAMusicDisc();
                    // Write to the file
                    fileManagement.writeToFile(musicDisc);
                }

                // Create a Game Disc
                case 2 -> {
                    System.out.println("Creating a Game Disc...");
                    // Create the Disc
                    GameDisc gameDisc = discManagement.createAGameDisc();
                    // Write to the file
                    fileManagement.writeToFile(gameDisc);
                }


                // Remove a dic
                case 3 -> {
                    Disc oldDisc = discManagement.removeDisc();
                    if (oldDisc != null)
                        fileManagement.deleteRecordFromFile(oldDisc);
                }
                // Search for a disc and display its details
                case 4 -> discManagement.getUserInputToSearchForDisc();

                // Reverse the records
                case 5 -> fileManagement.readAllRecords();

                // Edit the details of a disc ans update it in the record
                case 6 -> {
                    RecordValues<String, String, Disc> record = discManagement.editDisc();
                    if (record != null)
                       fileManagement.editRecord(record);
                }

                // Rename the CSV file
                case 7 -> fileManagement.getUserInputToRenameFile();

                // Terminate the program
                case 8 -> {
                    System.out.println("Goodbye...");
                    quit = true;
                }

                // Option not available
                default -> System.out.println("This option is not valid");
            }
        } while(!quit);
    }

    // Displays menu system to user
    private static void displayMenu(){
        System.out.println();
        System.out.println("Disc Information Management");
        System.out.println("-------------------------------------------------");
        System.out.println("Please select an option:");
        System.out.println("Press 1: Add Music Disc Details");
        System.out.println("Press 2: Add Games Disc Details");
        System.out.println("Press 3: Remove a Disc");
        System.out.println("Press 4: Search for a Disc");
        System.out.println("Press 5: Display Discs in reverse order");
        System.out.println("Press 6: Edit details for a Disc");
        System.out.println("Press 7: Rename the CSV File");
        System.out.println("Press 8: Quit");
    }

    // Gets user to input a Int and makes sure it is valid data type
    public static int getIntValidInput(){
        boolean validInput = false;
        int number = 0;
        do{
            try{
                number = keyboard.nextInt();
                validInput = true;
            }
            catch(InputMismatchException e){
                System.out.println("Please enter a number");
                keyboard.nextLine();
            }
        } while(!validInput);

        keyboard.nextLine();
        return number;
    }
}