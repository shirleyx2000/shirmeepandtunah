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
    
    //array of linked lists 
    private List<LinkedList<Vertex>> adjList = new ArrayList<LinkedList<Vertex>>(); //vertices adj to ith
    
    /**
     * Constructs AdjacencyListGraph <-- needed? 
     */
    public void AdjacencyListGraph() {
        
    }

    /**
     * Adds a vertex to the graph.
     *
     * requires: v is not already a vertex in the graph
     * effects: adds vertex v to the graph 
     * 
     * @param v : new vertex to add to graph
     */
    public void addVertex(Vertex v) {
        // add to second dimension array
        LinkedList<Vertex> newLinkedLs = new LinkedList<Vertex>(); 
        newLinkedLs.add(v);
        // add to the first dimension array
        adjList.add(newLinkedLs);
    }

    /**
     * Adds an edge from v1 to v2. 
     *
     * requires: v1 and v2 are vertices in the graph
     * 
     * @param v1, v2 : vertices to form edge
     */
    public void addEdge(Vertex v1, Vertex v2) {
        //Vertices are stored in arbitrary order
        for (LinkedList<Vertex> vertLs : adjList) {
            if (vertLs.getFirst() == v1) {
                vertLs.addLast(v2);
            }
        }
    }

    /**
     * Check if there is an edge from v1 to v2.
     *
     * require: v1 and v2 are vertices in the graph 
     * effect: return true iff an edge from v1 connects to v2
     * 
     * @param v1, v2 : vertices forming an edge
     * @return boolean
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {
        
        //must be directional, so head of list = v1
        for (LinkedList<Vertex> vertLs : adjList) {
            if (vertLs.getFirst() == v1) {
                ListIterator <Vertex> vertIt = vertLs.listIterator();  
                while (vertIt.hasNext()) {
                    if(vertIt.next() == v2) { return true; } 
                    else { return false; }
                }
            }
        }
        
        //iterated through the whole graph and not found
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
     * @param v      : vertex to find all its children
     * @returns list : containing all children
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v) {
        //return an unmodifiable list of vertices to prevent invariant exposure

        for (LinkedList<Vertex> thisLs : adjList) {
            //find v in first dimension 
            if (thisLs.getFirst() == v) {
                return Collections.unmodifiableList(thisLs); 
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
        //return an unmodifiable list of vertices to prevent invariant exposure
        List<Vertex> upStreamVertices = new ArrayList<Vertex>(); 
        
        for (LinkedList<Vertex> vertLs : adjList) {
            ListIterator <Vertex> vertIt = vertLs.listIterator(); 
            // find v in second dimension 
            while (vertIt.hasNext()) {
                if (vertIt.next() == v) {
                    // append first dimension element
                    upStreamVertices.add(vertLs.getFirst());
                }
            }
        }
                
        return Collections.unmodifiableList(upStreamVertices);
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
        //return an unmodifiable list of vertices to prevent invariant exposure
        List<Vertex> allVertices = new ArrayList<Vertex>(); 
        
        for (LinkedList<Vertex> vertLs : adjList) {
            for (Vertex vtx : vertLs) {
                if (!allVertices.contains(vtx)) {
                    allVertices.add(vtx);
                }
            }
        }
        
        return Collections.unmodifiableList(allVertices);
    }
}
