package ca.ubc.ece.cpen221.mp3.graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph implements Graph {
// TODO: Implement this class

    private List<LinkedList<Vertex>> adjList = new ArrayList<LinkedList<Vertex>>();

    /**
     * Adds a vertex to the graph.
     *
     * requires: v is not already a vertex in the graph
     * effects: adds vertex v to the graph 
     * 
     * @param v : new vertex to add to graph
     */
    public void addVertex(Vertex v) {

        LinkedList<Vertex> vertex = new LinkedList<Vertex>(); 
        
        if ( !adjList.contains(v) ){
            vertex.add( v ); //Head of linked list is the vertex itself.
            adjList.add( vertex );
        }
    }

    /**
     * Adds an edge from v1 to v2. 
     *
     * requires: v1 and v2 are vertices in the graph
     * 
     * @param v1, v2 : vertices to form edge
     */
    public void addEdge(Vertex v1, Vertex v2) {
        //Order of adjacent vertices does not matter
        for ( LinkedList<Vertex> vertex : adjList ) {
            if ( vertex.getFirst().equals(v1) ) {
                vertex.add( v2 );
            }
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
        
        //Must be directional, so head of list = v1
        for (LinkedList<Vertex> vertex : adjList) {
            if ( vertex.getFirst().equals(v1) ) {
                ListIterator <Vertex> vertIt = vertex.listIterator();  
                while ( vertIt.hasNext() ) {
                    if( vertIt.next().equals(v2) ) { 
                        return true;
                    } 
                }
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
        
        for (LinkedList<Vertex> vertex : adjList) { 
            if (vertex.getFirst().equals(v)) {
                downstreamNeighbours.addAll(vertex);
                downstreamNeighbours.remove(0); //Removes head of list i.e. vertex itself
                return Collections.unmodifiableList(downstreamNeighbours); 
            }
        }
        
        return Collections.<Vertex> emptyList();
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
        
        for (LinkedList<Vertex> vertex : adjList) {
            ListIterator <Vertex> vertIt = vertex.listIterator();  
            vertIt.next(); //Start pointing to element after head.
            while ( vertIt.hasNext() ) {
                if ( vertIt.next().equals(v) ) {
                    upStreamVertices.add( vertex.getFirst() );
                }
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

        List<Vertex> allVertices = new ArrayList<Vertex>(); 
        
        for ( LinkedList<Vertex> vertex : adjList ) {
            allVertices.add(vertex.getFirst());
        }
        
        return Collections.unmodifiableList( allVertices );
    }
}
