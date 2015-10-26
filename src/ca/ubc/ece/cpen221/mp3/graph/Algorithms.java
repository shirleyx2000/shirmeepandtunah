package ca.ubc.ece.cpen221.mp3.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
    * If no such path exists from a to b, then throw PathNotFoundException
    * 
    * Precondition: a and b must vertices on graph
    * Postcondition: Smallest number of edges traversed from a to b returned
    * 
    * @param graph
    * @param a
    * @param b
    * @return Number of edges traversed to get from vertex a to b
    *         if no path between a and b exists, a PathNotFoundException is thrown
    * @throws PathNotFoundException
    */
    public static int shortestDistance(Graph graph, Vertex a, Vertex b) throws PathNotFoundException {
       // TODO: Implement this method and others
       return 0;
    }
    
    
    
	/**
	 * Traverses an entire graph
	 * 
	 * @param graph    Graph to traverse
	 * 
	 * @return bfs     An immutable set of traversal routines
	 *                 Each traversal routine is a list of vertices in the order by which they were visited,
	 *                 Each traversal starts from a vertex in the graph
	 *                 If no traversal routines are found, an empty set is returned
	 */
	public static Set<List<Vertex>> breadthFirstSearch( Graph graph ){
	    //TODO: Implement this method
	    
	    Set<List<Vertex>> bfs = new HashSet<List<Vertex>>();
	    Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>(); 
	    Queue<Vertex> nextVertex = new LinkedList<Vertex>(); 
	    Graph g = graph; 
	    Vertex v; 
	    
	    //make visitation possible
        for (Vertex ev : g.getVertices()) {
           distance.putIfAbsent(ev, null);
        }
	    
        for (Vertex vertex : g.getVertices()) {
            List<Vertex> traversal = new ArrayList<Vertex>(); 

            //set first vertex as arbitrary starting point
            nextVertex.add(vertex);
            distance.put(vertex, 0);
            int path = 0; 
            
            //now pop queue and add to queue for ONE vertex
            while (!nextVertex.isEmpty()) {
                v = nextVertex.poll(); 
                path = distance.get(v) + 1; 
                //for each vertex adjacent to v, depth "path"
                for (Vertex child : g.getDownstreamNeighbors(v)) {
                    if (distance.get(child) == null) {
                        // not visited yet 
                        distance.put(child, path);
                        nextVertex.add(child);
                        traversal.add(child);
                    }
                }
            }
            
            //done traversing for this one node
            bfs.add(traversal);
        }

	    //Create queue (LinkedList) to hold vertices (a linked list of size-1 maps)
	    //Create size-1 map to represent each vertex (key) and its distance from the starting vertex (value) 
	        //i.e. Map<Vertex, Integer>
	        //Initial value of distance = null
	    
	    //For each vertex in the graph
	        //Create a list of vertices to be traversed    
	        //Enqueue the starting vertex into the queue
	        //Add the starting vertex to the list
	            //While queue is not empty
	                //Dequeue next vertex
    	            //Get downstream neighbours of vertex
        	            //For each downstream neighbour
        	                 //If distance = null
        	                        //Enqueue the neighbour
	                                //Add the neighbour to the list
        	                        //Increment its distance by 1

	    
	    return Collections.unmodifiableSet( bfs );
	}
	
	
	
	/**
	 * Traverses an entire graph
	 * 
	 * @param graph     Graph to traverse
	 * 
	 * @return dfs      An immutable set of traversal routines
     *                  Each traversal routine is a list of vertices in the order by which they were visited
     *                  Each traversal starts from a vertex in the graph
     *                  If no traversal routines are found, an empty set is returned
	 */
	public static Set<List<Vertex>> depthFirstSearch( Graph graph ){
	  //TODO: Implement this method
	    
	    Set<List<Vertex>> dfs = new HashSet<List<Vertex>>();
	    
	    //Create a stack
	    //For each vertex in the graph
	         //Push the vertex into the stack
	         //While the stack is not empty
	                //Pop the stack
	                //If the vertex not been visited  
	                    //Label the vertex as visited
	                    //For each downstream neighbour of vertex
	                           //Push neighbour onto stack
	    
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
