package ca.ubc.ece.cpen221.mp3.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	 * Traverses an entire graph
	 * 
	 * @param graph    Non-null graph to traverse
	 * 
	 * @return bfs     An immutable set of traversal routines
	 *                 Each traversal routine is a list of vertices in the order by which they were visited,
	 *                 One traversal starts from a vertex in the graph
	 */
	public static Set<List<Vertex>> breadthFirstSearch( Graph graph ){
	    //TODO: Implement this method
	    
	    Set<List<Vertex>> bfs = new HashSet<List<Vertex>>();
	    
	    //Create queue (LinkedList) to hold vertices (a linked list of size-1 maps)
	    //Create size-1 map to represent each vertex (key) and tuple of predecessor and distance (value) (see below)
	        //i.e. Map<Vertex, Map<Vertex, Integer>>
	        //Create size-1 map to represent tuple of predecessor (key) and distance from starting vertex (value)
	            //i.e. Map<Vertex, Integer>
	            //Initial predecessor & distance = null
	    
	    //Iterate through all the vertices in the graph
	        //Push the starting vertex into the queue
	            //Set distance = 0
	            //Pop the starting vertex
	            //Get downstream neighbours of vertex
	            //If distances
	    
	    
	    return Collections.unmodifiableSet( bfs );
	}
	
	
	
	/**
	 * Traverses an entire graph
	 * 
	 * @param graph     Non-null graph to traverse
	 * 
	 * @return dfs      An immutable set of traversal routines
     *                  Each traversal routine is a list of vertices in the order by which they were visited
     *                  Each traversal starts from a vertex in the graph
	 */
	public static Set<List<Vertex>> depthFirstSearch( Graph graph ){
	  //TODO: Implement this method
	    
	    Set<List<Vertex>> dfs = new HashSet<List<Vertex>>();
	    
	    return Collections.unmodifiableSet( dfs );
	}
	
    
    
	/**
	 * Returns list of common upstream vertices between two given vertices in a graph
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return cuv     Immutable list of all vertices u such that there is an edge from u to a and an edge from u to b
	 *                 If no such vertices exist, an empty list is returned 
	 */
	public static List<Vertex> commonUpstreamVertices( Graph graph, Vertex a, Vertex b ){
	  //TODO: Implement this method
	    
	    List<Vertex> cuv = new ArrayList<Vertex>();
	    
	    return Collections.unmodifiableList( cuv );
	}
	
	
	
	/**
	 * Returns a list of common downstream vertices between two given vertices in a graph
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return cdv     Immutable list of all vertices v such that there is an edge from a to v and an edge from b to v
	 *                 if no such vertices exist, an empty list is returned
	 */
	public static List<Vertex> commonDownstreamVertices( Graph graph, Vertex a, Vertex b ){
	  //TODO: Implement this method
	    
	    List<Vertex> cdv = new ArrayList<Vertex>();
	    
        return Collections.unmodifiableList( cdv );
    }

	
}
