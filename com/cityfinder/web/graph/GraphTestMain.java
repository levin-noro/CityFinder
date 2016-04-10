package com.cityfinder.web.graph;

import java.util.ArrayList;

import com.cityfinder.web.graph.datastructures.graph.City;
import com.cityfinder.web.graph.solution.CitySimilarity;

import java.util.ArrayList;

/**
 * Created by Victor on 11-Mar-16.
 *
 * The main class. Tests the graph.
 */
public class GraphTestMain {

    /**
     * Main method of program. Called by OS.
     * @param args Command-line args (unused).
     */
    public static void main(String[] args)
    {
        CitySimilarity cs = new CitySimilarity(); // Create a city similarity object to query
        int[] weights = new int[]{9, 2, 3, 4, 5, 6, 7, 4, 9, 10, 20}; // Use test weights
        String testName = "Brantford";
        ArrayList<City> sCs = cs.simCities(weights, testName); // Try to find similar cities

        /* Error check */
        if (sCs != null) // No errors
        {
            System.out.println("["); // Start the array

            for (int i = 0; i < sCs.size(); i++) {
                System.out.println(sCs.get(i) + ","); // Print the city
            }

            System.out.println("]"); // End the array
        }

        else // Errors detected
        {
            System.out.println("Error: the city \"" + testName + "\" doesn't exist.");
        }
    }
}
