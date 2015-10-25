package ca.ubc.ece.cpen221.mp3.graph;

import java.util.List;
import java.util.Set;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {

    
	/**
	 * *********************** Algorithms ****************************
	 * 
	 * Please see the README for the machine problem for a more detailed
	 * specification of the behavior of each method that one should implement.
	 */
	
    
    
	/**
	 * Traverses an entire graph
	 * 
	 * @return A set of traversal routines;
	 *             each traversal routine is a list of vertices in the order by which they were visited
	 */
	public static Set<List<Vertex>> breadthFirstSearch(){
	    //TODO: Implement this method
	    return null;
	}
	
	
	
	/**
	 * Traverses an entire graph
	 * 
	 * @return A set of traversal routines;
     *            each traversal routine is a list of vertices in the order by which they were visited
	 */
	public static Set<List<Vertex>> depthFirstSearch(){
	  //TODO: Implement this method
	    return null;
	}
	
	
	
	 /**
     * 
     * Finds shortest distance between two vertices a and b in an unweighted graph G. 
     * The shortest distance is the number of edges traversed to get to a from b.
     * Distance between a vertex and itself is 0. 
     * If no such path exists from a to b, then TODO: [insert appropriate action]
     * 
     * @param graph
     * @param a
     * @param b
     * @return Number of edges traversed to get from vertex a to b
     *         if no path between a and b exists, TODO: [insert alt. return value]
     */
    public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
        // TODO: Implement this method and others
        return 0;
    }
	
    
    
	/**
	 * Returns list of common upstream vertices between two given vertices in a graph
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return List of all vertices u such that there is an edge from u to a and an edge from u to b
	 *         if no such vertices exist, an empty list is returned 
	 */
	public static List<Vertex> commonUpstreamVertices( Graph graph, Vertex a, Vertex b ){
	  //TODO: Implement this method
	    return null;
	}
	
	
	
	/**
	 * Returns a list of common downstream vertices between two given vertices in a graph
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return List of all vertices v such that there is an edge from a to v and an edge from b to v
	 *         if no such vertices exist, an empty list is returned
	 */
	public static List<Vertex> commonDownstreamVertices( Graph graph, Vertex a, Vertex b ){
	  //TODO: Implement this method
        return null;
    }

	
}
