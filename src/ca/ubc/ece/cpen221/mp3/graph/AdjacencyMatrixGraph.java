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
    private Map<Vertex, Map<Vertex, Integer>> verticesPtr = new HashMap<Vertex, Map<Vertex, Integer>>();
    
    /**
     * Adds a vertex to the graph.
     *
     * require: v is not already a vertex in the graph
     */
    public void addVertex(Vertex v) {
        
        //Assign empty edge map to new vertex 
        Map<Vertex, Integer> edgeExistence = new HashMap<Vertex, Integer>();
        //Add vertex with empty edge map to vertices
        verticesPtr.put(v, edgeExistence);
        
    }

    /**
     * Adds an edge from v1 to v2.
     *
     * require: v1 and v2 are vertices in the graph
     */
    public void addEdge(Vertex v1, Vertex v2) {
        //Assumption: v1 = v2 okay (i.e. edge between same vertex)
        
        //Iterate through verticesPtr keys
        for( Vertex v_origin : verticesPtr.keySet() ){
          //Find key that equals v1
            if( v_origin.equals(v1) ){
                //Get value i.e. edge map for v1
                //If the edge already exists
                if( verticesPtr.get(v_origin).containsKey(v2) ){
                  //Then increment value by 1
                    Integer edgeExistence = verticesPtr.get(v_origin).get(v2);
                    verticesPtr.get(v_origin).put(v2, edgeExistence + 1);
                }
                //Else, create the edge
                else {
                  //Add v2 as new key to v1's edge map, set value to 1 
                  verticesPtr.get(v_origin).put(v2, 1);
                } 
            }
        }
    }

    /**
     * Check if there is an edge from v1 to v2.
     * 
     * Precondition: v1 and v2 are vertices in the graph 
     * Postcondition: return true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {
        
        //An edge exists if a key == v1 from verticesPtr i.e. edge map has a key == v2 and the value is > 0
        if( verticesPtr.containsKey(v1) ){
            if( verticesPtr.get(v1).containsKey(v2) ){
                if( verticesPtr.get(v1).get(v2) > 0 ){
                    return true;
                }
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
        
        downstreamNeighbours.addAll(verticesPtr.get(v).keySet());
        
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
        
        //Iterate through verticesPtr's keys i.e. vertices in matrix
            //Iterate through the edge map keys
                //If the vertex' edge map's key (i.e. element of verticesPtr.get(v_origin).keySet()) equals v
                    //Then add the key to upStreamNeighbours
        
        for( Vertex v_origin : verticesPtr.keySet() ){
            for( Vertex v_end : verticesPtr.get(v_origin).keySet() ){
                if( v_end.equals(v) ){
                    upstreamNeighbours.add(v_origin);
                }
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
        
        List<Vertex> allVertices = new ArrayList<Vertex>();
        
        allVertices.addAll(verticesPtr.keySet());
        
        return Collections.unmodifiableList( allVertices );
    }
}
