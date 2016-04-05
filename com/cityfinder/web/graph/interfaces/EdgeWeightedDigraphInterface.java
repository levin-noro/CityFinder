package com.cityfinder.web.graph.interfaces;

import com.cityfinder.web.graph.datastructures.graph.City;
import com.cityfinder.web.graph.datastructures.graph.WeightedDirectedEdge;

/**
 * Created by Victor on 03-Apr-16.
 */

import com.cityfinder.web.graph.datastructures.graph.City;
import com.cityfinder.web.graph.datastructures.graph.WeightedDirectedEdge;

/**
 * Created by Victor on 11-Mar-16.
 *
 * Interface for an edge-weighted digraph.
 */
public interface EdgeWeightedDigraphInterface<T> {
    /**
     * This method adds a new weighted, directed edge to the graph.
     *
     * @param c The city which the edge leaves.
     * @param wde The weighted, directed edge to add. It points from the given city to another city. The second city is stored in the edge.
     */
    void addEdge(City c, WeightedDirectedEdge wde);

    /**
     * Fetches all edges connected to a particular city, or returns null if there aren't any.
     * @param c The city to find the edges of.
     * @return An Iterable containing the adjacent edges.
     */
    Iterable<WeightedDirectedEdge> adj(City c);

    /**
     * Fetches the number of vertices.
     * @return The number of vertices in the graph.
     */
    int V(); // Number of vertices

    /**
     * Fetches the number of edges.
     * @return The number of edges in the graph.
     */
    int E(); // Number of edges

    /**
     * Fetches all edges in the graph.
     * @return An Iterable containing all edges in the graph.
     */
    Iterable<WeightedDirectedEdge> edges();

    /**
     * Returns all nodes in the graph in a datastructure which can be iterated over.
     * @return All nodes in the graph.
     */
    Iterable<City> vertices();

    /**
     * Sets the weight of all edges of the given category to the given weight.
     * @param category The category of the edges to set. Must be a number in the ranges [0, 10]/[0, 11).
     * @param weight The new weight for this type of edge.
     */
    void setCategoryWeight(int category, int weight);
}

