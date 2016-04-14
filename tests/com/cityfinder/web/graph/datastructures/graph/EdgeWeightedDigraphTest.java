package com.cityfinder.web.graph.datastructures.graph;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Victor on 13-Apr-16.
 *
 * Class which tests the EdgeWeightedDigraph class.
 */
public class EdgeWeightedDigraphTest {
    private static EdgeWeightedDigraph ewd; // Test digraph
    private static ArrayList<City> cs;
    private static City temp;

    /**
     * Sets up the graph for testing.
     */
    public static void setupBeforeClass()
    {
        cs = new ArrayList<>();
        temp = new City(1, "hi", 2, 3, 4, 5, 6, 7, "ON", "as", 7);
        temp.setCrimeScore(5);
        cs.add(temp);
    }

    /**
     * Tests adding an edge to the graph.
     * @throws Exception
     */
    @Test
    public void addEdge() throws Exception {

    }

    @Test
    public void adj() throws Exception {

    }

    @Test
    public void v() throws Exception {

    }

    @Test
    public void edges() throws Exception {

    }

    @Test
    public void vertices() throws Exception {

    }

    @Test
    public void setCategoryWeight() throws Exception {

    }

}