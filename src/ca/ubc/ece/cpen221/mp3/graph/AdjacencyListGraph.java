package ca.ubc.ece.cpen221.mp3.graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph implements Graph {
// TODO: Implement this class

    private Map<Vertex, LinkedList<Vertex>> adjList = new HashMap<Vertex, LinkedList<Vertex>>();

    /**
     * Adds a vertex to the graph.
     *
     * requires: v is not already a vertex in the graph
     * effects: adds vertex v to the graph 
     * 
     * @param v : new vertex to add to graph
     */
    public void addVertex(Vertex v) {

        LinkedList<Vertex> adjVertices = new LinkedList<Vertex>(); 
        
        adjList.putIfAbsent(v, adjVertices);

    }

    /**
     * Adds an edge from v1 to v2. 
     *
     * requires: v1 and v2 are vertices in the graph
     * 
     * @param v1, v2 : vertices to form edge
     */
    public void addEdge(Vertex v1, Vertex v2) {
        
        if( adjList.containsKey(v1) ){
            adjList.get(v1).add(v2);
        }
        
    }

    /**
     * Check if there is an edge from v1 to v2.
     *
     * require: v1 and v2 are vertices in the graph 
     * effect: return true iff an edge from v1 to v2 exists
     * 
     * @param v1, v2 : vertices forming an edge
     * @return boolean
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {
        
        if( adjList.containsKey(v1) ){
            if( adjList.get(v1).indexOf(v2) != -1 ){
                return true;
            }
        }
        return false;
        
    }

    /**
     * Get an array containing all downstream vertices adjacent to v.
     *
     * requires: v is a vertex in the graph
     * effects: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     * 
     * @param v      : vertex to find all its downstream neighbours
     * @returns list : containing all downstream neighbours
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v) {

        List<Vertex> downstreamNeighbours = new ArrayList<Vertex>();
        
        if( adjList.containsKey(v) ){
            downstreamNeighbours.addAll(adjList.get(v));
        }
        
        return Collections.unmodifiableList( downstreamNeighbours );
    }

    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * requires: v is a vertex in the graph
     * effects: returns a list containing each vertex u such that there is
     * an edge from u to v. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no upstream neighbors.
     * 
     * @param v      : vertex to find all parents
     * @returns list : containing all parents
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v) {

        List<Vertex> upStreamVertices = new ArrayList<Vertex>(); 
        
        for( Vertex vertex : adjList.keySet() ){
            if( adjList.get(vertex).contains(v) ){
                upStreamVertices.add(vertex);
            }
        }

        return Collections.unmodifiableList( upStreamVertices );
    }

    /**
     * Get all vertices in the graph.
     *
     * effects: returns a list containing all vertices in the graph. This
     * method should return a list of size 0 iff the graph has no vertices.
     * 
     * @returns list : containing a set of all vertices in the graph
     */
    public List<Vertex> getVertices() {

        List<Vertex> allVertices = new ArrayList<Vertex>(adjList.keySet()); 
        
        return Collections.unmodifiableList( allVertices );
    }
}
