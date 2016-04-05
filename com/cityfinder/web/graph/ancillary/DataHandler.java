package com.cityfinder.web.graph.ancillary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.cityfinder.web.graph.datastructures.graph.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Abstracts the low-level parsing - handles reading files. Can be changed to read from database instead. Provides an API for other
 * classes to get access to the data.
 */
public class DataHandler
{
    private ArrayList<City> cities; // List of cities loaded from the data

    /**
     * Constructor. Calls the various data-loading functions to load the data.
     */
    public DataHandler()
    {
        /* Set up vars */
        cities = new ArrayList<City>(); // Create the list of cities

        /* Load the data */
        loadWellBeingData(); // Load the well-being data
    }

    /**
     * Fetches the list of cities loaded from the datasets.
     * @return The list of cities.
     */
    public ArrayList<City> getCityList()
    {
        return cities;
    }

    /**
     * Loads data from the well-being files and creates the com.cityfinder.web.graph.datastructures.graph.City objects.
     */
    private void loadWellBeingData()
    {
            /* Load data from well-being file */
        Scanner wellbeingScanner; // Scanner used to read from well-being file
        int lno = 1; // Line number (used to skip files)
        String curLine;

        try
        {
            /** Read the well-being file **/
            wellbeingScanner = new Scanner(new File("Canada well being.csv")); // Load the well-being file

            while (wellbeingScanner.hasNext()) // For each line
            {
                curLine = wellbeingScanner.nextLine(); // Get the next line
                //System.out.println(curLine);

                if (lno != 1) // Ignore headings
                {
                    String[] fields = curLine.split(","); // Split line into fields

                    for (int i = 0; i < fields.length; i++)
                    {
                        if (fields[i].equals("")) // Empty string
                        {
                            fields[i] = "-1"; // Set it to -1 to mark it as no data
                        }
                    }

                    cities.add(new City(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), parsePercentage(fields[7]), fields[8], fields[9], Integer.parseInt(fields[10]))); // Add a new city object representing this city to the list
                }

                lno++; // Increment line count
            }

            wellbeingScanner.close();
        }

        catch (FileNotFoundException fnfe) // File not found
        {
            System.err.println("Couldn't open file.\nMessage: " + fnfe.getMessage());
        }
    }

    /**
     * Parses an integer percentage from a string in the format "[0-9]+%".
     * @param s The string to parse.
     * @return An integer representing the string.
     */
    private int parsePercentage(String s)
    {
        String numStr = s.substring(0, s.length()-1); // A substring from the beginning of the string to one before the percentage - the number
        return Integer.parseInt(numStr); // Return the string as an integer
    }
}