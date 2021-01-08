package com.company;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class SearchForDiscTest {

    ManagementDisc managementDisc =new ManagementDisc();
    MusicDisc musicDisc =new MusicDisc("CoolSongs", "POP", new Date(12/18/2018), "Mike", 12, 54);
    GameDisc gameDisc =new GameDisc("CoolGame", "Puzzle",  new Date(12/18/2018), "18", "PC");

    // A failing test
    @Test
    public void failTest(){
        fail();
    }

    // A test to make sure that if the list is empty, return false as nothing could be searched
    @Test
    public void listIsEmptyCheckTest(){
        boolean values = managementDisc.searchForDisc("Willow");
        assertFalse(values);
    }

    // A test to make sure that if a Music disc is on the list, return true as that disc could be found
    @Test
    public void findTheMusicDiscTest(){
        managementDisc.addDiscToList(musicDisc);
        boolean found = managementDisc.searchForDisc("CoolSongs");

        assertTrue(found);
    }

    // A test to make sure that if a Music disc is not on the list, return false as the disc could not be found
    @Test
    public void musicDiscCouldNotBeFoundTest(){
        managementDisc.addDiscToList(musicDisc);
        boolean found = managementDisc.searchForDisc("RubbishSongs");

        assertFalse(found);
    }

    // A test to make sure that if a Game Disc is on the list, return true as that disc could be found
    @Test
    public void findTheGameDiscTest(){
        managementDisc.addDiscToList(gameDisc);
        boolean found = managementDisc.searchForDisc("CoolGame");

        assertTrue(found);
    }

    // A test to make sure that if a Game disc is not on the list, return false as the disc could not be found
    @Test
    public void gameDiscCouldNotBeFoundTest(){
        managementDisc.addDiscToList(gameDisc);
        boolean found = managementDisc.searchForDisc("RubbishGame");

        assertFalse(found);
    }

    // Check if the found disc is an instance of the Music Disc Class so we can display its details
    @Test
    public void isInstanceOfMusicDiscClass(){
        managementDisc.addDiscToList(musicDisc);
        boolean isInstanceOfMusicDisc = managementDisc.searchForDisc("CoolSongs");

        assertTrue(isInstanceOfMusicDisc);
    }

    // Check if the found disc is an instance of the Game Disc Class so we can display its details
    @Test
    public void isInstanceOfGameDiscClass(){
        managementDisc.addDiscToList(gameDisc);
        boolean isInstanceOfGameDisc = managementDisc.searchForDisc("CoolGame");

        assertTrue(isInstanceOfGameDisc);
    }
}
