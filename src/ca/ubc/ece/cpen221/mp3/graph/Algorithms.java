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
import java.util.Stack;

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
        
        Map<Vertex, Integer> visited = new HashMap<Vertex, Integer>(); 
        Queue<Vertex> path = new LinkedList<Vertex>();
        Vertex nextVertex; 
        int minDistance = Integer.MAX_VALUE;
        
        //IFFY because did you create a path to itself? 
        if (a == b) {
            return 0; 
        }
        
        for (Vertex eachV : graph.getVertices()) {
            visited.put(eachV, null);
        }
        
        path.add(a); 
        visited.put(a, 0);
        
        while (!path.isEmpty()) {
            nextVertex = path.poll();
            for (Vertex v : graph.getDownstreamNeighbors(nextVertex)) {
                if (visited.get(v) == null){
                    visited.put(v,visited.get(nextVertex)+1);
                    path.add(v);
                    if (v.equals(b)) {
                        //note down the depth.
                        minDistance = Integer.min(minDistance, visited.get(v));
                    }
                }
            }
        }
        
        if (minDistance == Integer.MAX_VALUE){
            throw new PathNotFoundException(); 
        } else {
            return minDistance; 
        }
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
	    
	    Set<List<Vertex>> bfs = new HashSet<List<Vertex>>();
	    Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>(); 
	    Queue<Vertex> nextVertex = new LinkedList<Vertex>(); 
	    Vertex v; 
	    
        //now traverse through the graph with each vertex as the starting node
        for (Vertex vertex : graph.getVertices()) {
            List<Vertex> traversal = new ArrayList<Vertex>(); 

            //refresh visitation flag as UNVISITED 
            for (Vertex ev : graph.getVertices()) {
               distance.put(ev, null);
            }
            
            //set first vertex as arbitrary starting point
            nextVertex.add(vertex);
            traversal.add(vertex);
            distance.put(vertex, 0);
            int path = 0; 
            
            //now pop queue and add to queue for ONE vertex
            while (!nextVertex.isEmpty()) {
                v = nextVertex.poll(); 
                path = distance.get(v) + 1; 
                //for each vertex adjacent to v, depth "path"
                for (Vertex child : graph.getDownstreamNeighbors(v)) {
                    if (distance.get(child) == null) {
                        // not visited yet 
                        distance.put(child, path);
                        nextVertex.add(child);
                        traversal.add(child);
                    }
                }
            }
            
            //done traversing for this one node
            if (!traversal.isEmpty()) {
                bfs.add(traversal);
            }
        }

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
	    
	    Set<List<Vertex>> dfs = new HashSet<List<Vertex>>();
	    Vertex currentVertex; 
	    Map<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>(); 
	    
	    //for each vertex in the graph, have your time in the spotlight
	    for (Vertex vertex : graph.getVertices()) {
	        for (Vertex v : graph.getVertices()) {
	            visited.put(v, false);
	        }
            List<Vertex> traversal = new ArrayList<Vertex>(); 

	        Stack<Vertex> parents = new Stack<Vertex>(); 
	        //push in the first parent vertex
	        parents.push(vertex); 
	        traversal.add(vertex);
	        //traversal list starts here
	        while (!parents.isEmpty()) {
	            // this parent 
	            currentVertex = parents.pop(); 
	            if (!visited.get(currentVertex)) {
	                //this vertex has not been visited
	                visited.put(currentVertex, true);
	                // find all parent's children
	                for (Vertex child : graph.getDownstreamNeighbors(currentVertex)){
	                    // children growing up to become parents
	                    parents.push(child);
	                    if (!visited.get(child)) {
	                        traversal.add(child);
	                    }
	                }
	            }
	        }
	        //done traversal for this vertex
	        dfs.add(traversal);
	    }
	    
	    return Collections.unmodifiableSet( dfs );
	}
	
    
    
	/**
	 * Returns list of common upstream vertices between two given vertices in a graph
	 * 
	 * @param graph
	 * @param a        a must be a vertex in graph
	 * @param b        b must be a vertex in graph
	 * @return cuv     Immutable list of all vertices u such that there is an edge from u to a and an edge from u to b
	 *                 If no such vertices exist, an empty list is returned 
	 */
	public static List<Vertex> commonUpstreamVertices( Graph graph, Vertex a, Vertex b ){
	    
	    List<Vertex> cuv = new ArrayList<Vertex>();
	    
	    for (Vertex v : graph.getUpstreamNeighbors(a)) {
	        if (graph.getUpstreamNeighbors(b).contains(v)) {
	            cuv.add(v);
	        }
	    }
	    
	    return Collections.unmodifiableList( cuv );
	}
	
	
	
	/**
	 * Returns a list of common downstream vertices between two given vertices in a graph
	 * 
	 * @param graph    
	 * @param a        a must be a vertex in graph
	 * @param b        b must be a vertex in graph
	 * @return cdv     Immutable list of all vertices v such that there is an edge from a to v and an edge from b to v
	 *                 if no such vertices exist, an empty list is returned
	 */
	public static List<Vertex> commonDownstreamVertices( Graph graph, Vertex a, Vertex b ){
	    
	    List<Vertex> cdv = new ArrayList<Vertex>();
	    
        for (Vertex v : graph.getDownstreamNeighbors(a)) {
            if (graph.getDownstreamNeighbors(b).contains(v)) {
                cdv.add(v);
            }
        }
	    
        return Collections.unmodifiableList( cdv );
    }

	
}
