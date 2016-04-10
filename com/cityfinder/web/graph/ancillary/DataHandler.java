package com.cityfinder.web.graph.ancillary;

/* Java imports */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* My classes */
import com.cityfinder.web.graph.datastructures.graph.City;

/**
 * Abstracts the low-level parsing - handles reading files. Can be changed to read from database instead. Provides an API for other
 * classes to get access to the data.
 */
public class DataHandler
{
    private ArrayList<City> cities; // List of cities loaded from the data
    private Quick quick; // Quicksort class
    private HashMap<String, City> citiesByName; // Stores cities by name for fast lookup
    private HashMap<City, Integer> indicesByCity; // Stores array indices (for cities arraylist) by city object for fast lookup

    /**
     * Constructor. Calls the various data-loading functions to load the data.
     */
    public DataHandler()
    {
        /* Set up vars */
        cities = new ArrayList<City>(); // Create the list of cities
        quick = new Quick(); // Create quicksort class
        citiesByName = new HashMap<>(); // Create name->city hashmap
        indicesByCity = new HashMap<>(); // Create city->index hashmap

        /* Load the data */
        loadWellBeingData(); // Load the well-being data
        loadCrimeData(); // Load crime data from crime file and add it to the appropriate city
    }

    /**
     * Loads crime data for each city from the dataset and associates it with a city.
     */
    private void loadCrimeData()
    {
        BufferedReader crimeReader; // Reader for file
        String curLine; // Current line in file
        String[] lSplit; // Holds split line
        String cName; // City's name
        String cProv; // City's province
        City addCity; // The city to which we are adding a crime score

        try
        {
            crimeReader = new BufferedReader(new FileReader(new File("Crime Index.csv"))); // Open the crime index file for reading
            crimeReader.readLine(); // Skip the headings

            while ((curLine = crimeReader.readLine()) != null) // Read until EOF
            {
                //System.out.println(curLine); // DEBUGGING
                lSplit = curLine.split(","); // Split line into fields
                cName = lSplit[1].trim(); // Store the name of the city and remove all leading and trailing spaces
                cProv = lSplit[2].trim(); // Store the city's province and remove all leading and trailing spaces
                //System.out.println("Reading data for " + cName + ", " + cProv);

                /* Check if the city exists */
                if (citiesByName.keySet().contains(cName)) // This city was read from thr well-being index dataset
                {
                    addCity = citiesByName.get(cName); // Fetch the city to which we will add the crime index

                    /* Determine if there is a crime score */
                    if (!lSplit[9].equals("..")) // There is data
                    {
                        //System.out.println("Have crime score " + Double.parseDouble(lSplit[9]));
                        addCity.setCrimeScore(Double.parseDouble(lSplit[9])); // The last field is the crime index value. Parse it and pass it to the city for storage.
                    } else // No data
                    {
                        //System.out.println("No crime score");
                        addCity.setCrimeScore(-1); // Use -1 to indicate no data
                    }
                }
            }

            crimeReader.close(); // Close the reader when we finish
        }

        catch (FileNotFoundException fnfe) // Couldn't find crime file
        {
            System.err.println("DataHandler.loadCrimeData: couldn't find crime file!\nException: " + fnfe);
            System.exit(1);
        }

        catch (IOException ioe) // I/O error
        {
            System.err.println("DataHandler.loadCrimeData: I/O error occurred\nException: " + ioe);
            System.exit(3);
        }

        catch (NullPointerException npe) // Null pointer exception
        {
            System.err.println("DataHandler.loadCrimeData: null pointer exception occurred!\nException: " + npe + "\nMessage: " + npe.getMessage());
            System.exit(2);
        }
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
        int citInd = 0; // Index of city object
        City temp; // Temporary holder for city object

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

                    temp = new City(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]), parsePercentage(fields[7]), fields[8], fields[9], Integer.parseInt(fields[10])); // Create the new city from this line and store a temporary reference to it
                    cities.add(temp); // Add a new city object representing this city to the list
                    citiesByName.put(temp.getStrVal(0), temp); // Store city object in hashmap of cities by name for later lookup
                    indicesByCity.put(temp, citInd); // Store index of city in arraylist
                    citInd++; // Increment index for next loop
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