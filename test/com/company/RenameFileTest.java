package com.company;

import org.junit.Test;
import static org.junit.Assert.*;

public class RenameFileTest {

    ManagementFile managementFile =new ManagementFile();

    // A failing test
    @Test
    public void failTest(){
        fail();
    }

    // A test to make sure that given a new File path make sure that the current path is not the same
    @Test
    public void makeSurePathsDoNotEqualTest(){
        String actual = managementFile.renameFile("rename.csv");

        assertNotNull(actual);  // Making sure new file path did not send an empty string
    }

    // A test to make sure that given a new File path with no extension (.csv) make sure we add the extension
    @Test
    public void makeSureExtensionIsAddedTest(){
        String expected = "rename.csv";
        String actual = managementFile.renameFile("rename");

        assertEquals(expected, actual);
    }

    // A test to make sure that given a new File path with an extension, make sure we don't add another extension
    @Test
    public void makeSureExtensionIsNotAddedTest(){
        String expected = "rename.csv";
        String actual = managementFile.renameFile("rename.csv");

        assertEquals(expected, actual);
    }

    // A test to make sure that it is the first time running a program, make sure the an empty file is created
    @Test
    public void makeSureAFileExistsTest(){
        String expected = "TRUE";
        String actual = managementFile.renameFile("rename.csv");

        assertEquals(expected, actual);
    }

    // A test to make sure that given a new File path, make sure that path gets updated once file has been renamed
    @Test
    public void renameTest(){
        // path = "records.csv" -> "rename.csv"
        String expected = "rename.csv";

        String actual = managementFile.renameFile("rename.csv");

        assertEquals(expected, actual);

    }
}
