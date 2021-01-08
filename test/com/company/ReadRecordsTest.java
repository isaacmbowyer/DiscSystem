package com.company;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class ReadRecordsTest {
    ManagementFile managementFile =new ManagementFile();
    MusicDisc musicDisc =new MusicDisc("CoolSongs", "Pop", new Date(01/01/1970), "Mike", 12, 54);
    GameDisc gameDisc =new GameDisc("CoolGame", "Puzzle",  new Date(01/01/1970), "18", "PC");

    @Before
    public void Test(){
        managementFile.writeToFile(musicDisc);
        managementFile.writeToFile(gameDisc);
    }

    // A failing test
    @Test
    public void failTest(){
        fail();
    }

    // A test to make sure that there is at least one record in the file
    @Test
    public void someRecordsInFileTest(){
        ArrayList<String[]> isSomeRecords = managementFile.readAllRecords();
        assertFalse(isSomeRecords.isEmpty());
    }

    // A test to get first record in the list (Music Disc)
    @Test
    public void getFirstRecordTest(){
        ArrayList<String[]> records = managementFile.readAllRecords();
        String[] expectedRecord = {"CoolSongs", "Pop", "Mike", "01/01/1970", "12", "54"};
        assertArrayEquals(expectedRecord, records.get(0));
    }

    // A test to get the second record in the list (Game Disc)
    @Test
    public void getSecondRecordTest(){
        ArrayList<String[]> records = managementFile.readAllRecords();
        String[] expectedRecord = {"CoolGame", "Puzzle", "01/01/1970", "18", "PC"};
        assertArrayEquals(expectedRecord, records.get(1));
    }

    // A test to make sure all the records are passed into the list
    @Test
    public void getAllRecordsTest(){
        ArrayList<String[]> records = managementFile.readAllRecords();
        int expectedSize = 2;

        assertEquals(2, records.size());
    }

    // A test to make sure that the records in the list are reversed
    @Test
    public void reverseTheRecordListTest(){
        ArrayList<String[]> records = managementFile.readAllRecords();
        String[] expectedFirstRecord = {"CoolGame", "Puzzle", "01/01/1970", "18", "PC"};

        assertArrayEquals(expectedFirstRecord, records.get(0));
    }

}