package ca.ubc.ece.cpen221.mp3.graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyMatrixGraph implements Graph {
// TODO: Implement this class
    
    //RI : Square matrix. M=N 
    private ArrayList<Vertex> vertices= new ArrayList<Vertex>(); 
    private Map<Vertex,Vertex> edges  = new HashMap<Vertex,Vertex>(); 
    
    /**
     * Adds a vertex to the graph.
     *
     * require: v is not already a vertex in the graph
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    /**
     * Adds an edge from v1 to v2.
     *
     * require: v1 and v2 are vertices in the graph
     */
    public void addEdge(Vertex v1, Vertex v2) {
        //Assumption: v1 = v2 ok (i.e. edge between same vertex)
        edges.putIfAbsent(v1, v2);
    }

    /**
     * Check if there is an edge from v1 to v2.
     * 
     * 
     * Precondition: v1 and v2 are vertices in the graph Postcondition: return
     * true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {
        
        for( Vertex origin : edges.keySet() ){
            if( origin.equals(v1) && edges.get(v1).equals(v2) ){
                return true; 
            }
        }

        return false; 
    }

    /**
     * Get an array containing all downstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v){
        
        List<Vertex> downstreamNeighbours = new ArrayList<Vertex>();
        
        //Iterate through keys and check if key->value == v
        for( Vertex origin : edges.keySet() ){
            if( origin.equals(v) ){
                downstreamNeighbours.add( edges.get( origin ) ); 
            }
        }
        
        return Collections.unmodifiableList( downstreamNeighbours );
    }

    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex u such that there is
     * an edge from u to v. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no upstream neighbors.
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v){
        
        List<Vertex> upstreamNeighbours = new ArrayList<Vertex>();
        
        //Iterate through keys
        for( Vertex origin : edges.keySet() ){
          //Check value of key
          //If value equals v, append the key to list of upstreamNeighbours
            if( edges.get(origin).equals(v) ){
                upstreamNeighbours.add(origin);
            }
        }
        
        return Collections.unmodifiableList( upstreamNeighbours );
        
    }

    /**
     * Get all vertices in the graph.
     *
     * Postcondition: returns a list containing all vertices in the graph. This
     * method should return a list of size 0 iff the graph has no vertices.
     */
    public List<Vertex> getVertices(){
        
        return Collections.unmodifiableList( vertices );
    }
}
