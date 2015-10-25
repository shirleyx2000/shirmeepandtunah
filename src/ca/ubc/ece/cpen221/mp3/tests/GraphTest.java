package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class GraphTest {

    static private AdjacencyMatrixGraph m; 
    static private AdjacencyListGraph ls; 
    static private Vertex v0; 
    static private Vertex v1; 
    static private Vertex v2; 
    static private Vertex v3; 
    static private Vertex v4; //no downstream 
    static private Vertex v5; //no upstream
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        v0 = new Vertex("zero"); 
        v1 = new Vertex("one"); 
        v2 = new Vertex("two"); 
        v3 = new Vertex("three"); 
        v4 = new Vertex("four"); 
        v5 = new Vertex("five"); 
        
    }
    
    @Before 
    public void setUp() throws Exception {
        m = new AdjacencyMatrixGraph(); 
        ls = new AdjacencyListGraph(); 
    }
    
    @Test 
    //Cannot test other observer function because they require existing vertices/vertex or edge(s)
    public void testEmptyGraph() {
        //Assert that we indeed have empty graphs 
        assertEquals(m.getVertices(), Arrays.asList()); 
        assertEquals(ls.getVertices(), Arrays.asList());         
    }
    
    @Test 
    //Testing bidirectional vertices and floating vertices
    public void testUpDownStream() {
        //Add vertices to Matrix graph
        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        m.addVertex(v3);
        
        //Add vertices to List graph
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        ls.addVertex(v3);
        
        //Creating edges both up and downstream for v1; leave v3 floating
        m.addEdge(v0, v1);
        m.addEdge(v1, v0);
        m.addEdge(v1, v2);
        m.addEdge(v2, v1);
        
        assertTrue(m.edgeExists(v0, v1));
        assertTrue(m.edgeExists(v1, v0));
        assertTrue(m.edgeExists(v1, v2));
        assertTrue(m.edgeExists(v2, v1));
        
        assertFalse(m.edgeExists(v1, v3));
        
        //Creating edges both up and downstream for v1; leave v3 floating
        ls.addEdge(v0, v1);
        ls.addEdge(v1, v0);
        ls.addEdge(v1, v2);
        ls.addEdge(v2, v1);
        
        assertTrue(ls.edgeExists(v0, v1));
        assertTrue(ls.edgeExists(v1, v0));
        assertTrue(ls.edgeExists(v1, v2));
        assertTrue(ls.edgeExists(v2, v1));
        
        assertFalse(ls.edgeExists(v1, v3));

        //Create sets and mutable copies to check against duplicates in original neighbour lists. 
        Set<Vertex> setListUpstreamNeighbours = new HashSet<Vertex>(ls.getUpstreamNeighbors(v1));
        Set<Vertex> setListDownstreamNeighbours = new HashSet<Vertex>(ls.getDownstreamNeighbors(v1));
        Set<Vertex> setListUpstreamNeighboursFloatingVtx = new HashSet<Vertex>(ls.getUpstreamNeighbors(v3));
        Set<Vertex> setListDownstreamNeighboursFloatingVtx = new HashSet<Vertex>(ls.getUpstreamNeighbors(v3));
        
        List<Vertex> listUpstreamNeighbours = new ArrayList<Vertex>(ls.getUpstreamNeighbors(v1));
        List<Vertex> listDownstreamNeighbours = new ArrayList<Vertex>(ls.getDownstreamNeighbors(v1));
        List<Vertex> listUpstreamNeighboursFloatingVtx = new ArrayList<Vertex>(ls.getUpstreamNeighbors(v3));
        List<Vertex> listDownstreamNeighboursFloatingVtx = new ArrayList<Vertex>(ls.getDownstreamNeighbors(v3));
          
        Set<Vertex> setMatrixUpstreamNeighbours = new HashSet<Vertex>(m.getUpstreamNeighbors(v1));
        Set<Vertex> setMatrixDownstreamNeighbours = new HashSet<Vertex>(m.getDownstreamNeighbors(v1));
        Set<Vertex> setMatrixUpstreamNeighboursFloatingVtx = new HashSet<Vertex>(m.getUpstreamNeighbors(v3));
        Set<Vertex> setMatrixDownstreamNeighboursFloatingVtx = new HashSet<Vertex>(m.getDownstreamNeighbors(v3));
        
        List<Vertex> matrixUpstreamNeighbours = new ArrayList<Vertex>(m.getUpstreamNeighbors(v1));
        List<Vertex> matrixDownstreamNeighbours = new ArrayList<Vertex>(m.getDownstreamNeighbors(v1));
        List<Vertex> matrixUpstreamNeighboursFloatingVtx = new ArrayList<Vertex>(m.getUpstreamNeighbors(v3));
        List<Vertex> matrixDownstreamNeighboursFloatingVtx = new ArrayList<Vertex>(m.getDownstreamNeighbors(v3));
        
        
        //Assert no duplicates
//        System.out.println(listUpstreamNeighbours + "     vs     " + setListUpstreamNeighbours);
//        System.out.println(listDownstreamNeighbours + "     vs     " + setListDownstreamNeighbours);
//        System.out.println(listUpstreamNeighboursFloatingVtx + "     vs     " + setListUpstreamNeighboursFloatingVtx);
//        System.out.println(listDownstreamNeighboursFloatingVtx + "     vs     " + setListDownstreamNeighboursFloatingVtx);
//        
//        System.out.println(matrixUpstreamNeighbours + "     vs     " + setMatrixUpstreamNeighbours);
//        System.out.println(matrixDownstreamNeighbours + "     vs     " + setMatrixDownstreamNeighbours);
//        System.out.println(matrixUpstreamNeighboursFloatingVtx + "     vs     " + setMatrixUpstreamNeighboursFloatingVtx);
//        System.out.println(matrixDownstreamNeighboursFloatingVtx + "     vs     " + setMatrixDownstreamNeighboursFloatingVtx);
        
        for( Vertex v : setListUpstreamNeighbours ){
            listUpstreamNeighbours.remove(v);
            if( listUpstreamNeighbours.contains(v)){
                fail("Adjacency List Upstream Neighbours contains duplicates");
            }
        }
        assertTrue(listUpstreamNeighbours.isEmpty());
        
        for( Vertex v : setListDownstreamNeighbours ){
            listDownstreamNeighbours.remove(v);
            if( listDownstreamNeighbours.contains(v)){
                fail("Adjacency List Downstream Neighbours contains duplicates");
            }
        }
        assertTrue(listDownstreamNeighbours.isEmpty());
        
        for( Vertex v : setListUpstreamNeighboursFloatingVtx ){
            listUpstreamNeighboursFloatingVtx.remove(v);
            if( listUpstreamNeighboursFloatingVtx.contains(v)){
                fail("Adjacency List Upstream Neighbours Floating Vtx contains duplicates");
            }
        }
        assertTrue(listUpstreamNeighboursFloatingVtx.isEmpty());
        
        for( Vertex v : setListDownstreamNeighboursFloatingVtx ){
            listDownstreamNeighboursFloatingVtx.remove(v);
            if( listDownstreamNeighboursFloatingVtx.contains(v)){
                fail("Adjacency List Downstream Neighbours Floating Vtx contains duplicates");
            }
        }
        assertTrue(listDownstreamNeighboursFloatingVtx.isEmpty());
        
        for( Vertex v : setMatrixUpstreamNeighbours ){
            matrixUpstreamNeighbours.remove(v);
            if( matrixUpstreamNeighbours.contains(v)){
                fail("Adjacency Matrix Upstream Neighbours contains duplicates");
            }
        }
        assertTrue(matrixUpstreamNeighbours.isEmpty());
        
        for( Vertex v : setMatrixDownstreamNeighbours ){
            matrixDownstreamNeighbours.remove(v);
            if( matrixDownstreamNeighbours.contains(v)){
                fail("Adjacency Matrix Downstream Neighbours contains duplicates");
            }
        }
        assertTrue(matrixDownstreamNeighbours.isEmpty());
        
        for( Vertex v : setMatrixUpstreamNeighboursFloatingVtx ){
            matrixUpstreamNeighboursFloatingVtx.remove(v);
            if( matrixUpstreamNeighboursFloatingVtx.contains(v)){
                fail("Adjacency Matrix Upstream Neighbours FloatingVtx contains duplicates");
            }
        }
        assertTrue(matrixUpstreamNeighboursFloatingVtx.isEmpty());
        
        for( Vertex v : setMatrixDownstreamNeighboursFloatingVtx ){
            matrixDownstreamNeighboursFloatingVtx.remove(v);
            if( matrixDownstreamNeighboursFloatingVtx.contains(v)){
                fail("Adjacency Matrix Downstream Neighbours FloatingVtx contains duplicates");
            }
        }
        assertTrue(matrixDownstreamNeighboursFloatingVtx.isEmpty());
        
        //Check neighbours of List graph. At this point, no duplicates should exist. containsAll() is ok.
        assertTrue(ls.getUpstreamNeighbors(v1).containsAll((ls.getDownstreamNeighbors(v1))));
        assertTrue(ls.getDownstreamNeighbors(v1).containsAll((ls.getUpstreamNeighbors(v1))));
        assertTrue(ls.getUpstreamNeighbors(v3).containsAll(ls.getDownstreamNeighbors(v3)));
        assertTrue(ls.getDownstreamNeighbors(v3).containsAll(ls.getUpstreamNeighbors(v3)));
        
        //Check neighbours of Matrix graph
        assertTrue(m.getUpstreamNeighbors(v1).containsAll(m.getDownstreamNeighbors(v1)));
        assertTrue(m.getDownstreamNeighbors(v1).containsAll(m.getUpstreamNeighbors(v1)));
        assertTrue(m.getUpstreamNeighbors(v3).containsAll(m.getDownstreamNeighbors(v3)));
        assertTrue(m.getDownstreamNeighbors(v3).containsAll(m.getUpstreamNeighbors(v3)));
    }
    
    @Test 
    // Testing whether all vertices exists in graph regardless of edges
    public void testGetVertices() {
        //No vertices for initial empty graph
        assertEquals(m.getVertices(), Arrays.asList());
        assertEquals(ls.getVertices(), Arrays.asList());

        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        m.addVertex(v3);
        m.addVertex(v4);
        m.addVertex(v5);
        //Create an edge for matrix graph
        m.addEdge(v5, v1);
        
        assertEquals(m.getVertices().size(), Arrays.asList(v0, v1, v2, v3, v4, v5).size());
        assertTrue(m.getVertices().containsAll(Arrays.asList(v0, v1, v2, v3, v4, v5)));
        
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        ls.addVertex(v3);
        ls.addVertex(v4);
        ls.addVertex(v5);
        
        //Create edges for list graph
        ls.addEdge(v0, v2);
        ls.addEdge(v4, v3);
        
        assertEquals(ls.getVertices().size(), Arrays.asList(v0, v1, v2, v3, v4, v5).size());
        assertTrue(ls.getVertices().containsAll(Arrays.asList(v0, v1, v2, v3, v4, v5)));
        
        assertEquals(m.getVertices().size(), ls.getVertices().size());
        assertTrue(ls.getVertices().containsAll(m.getVertices()));
        assertTrue(m.getVertices().containsAll(ls.getVertices()));
        
        //System.out.println("m.getVertices " + m.getVertices() );
        //System.out.println("ls.getVertices " + ls.getVertices() );
    }

    @Test 
    // TODO: how to mix and match testing both implementations?
    public void testDownStream() {
        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        m.addVertex(v3);
        m.addVertex(v4);
        
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        ls.addVertex(v3);
        ls.addVertex(v4);
        
        m.addEdge(v0, v1);
        m.addEdge(v0, v2);
        m.addEdge(v1, v3);
        m.addEdge(v2, v4);
        m.addEdge(v3, v0);
        m.addEdge(v3, v4);
        
        ls.addEdge(v0, v1);
        ls.addEdge(v0, v2);
        ls.addEdge(v1, v3);
        ls.addEdge(v2, v4);
        ls.addEdge(v3, v0);
        ls.addEdge(v3, v4);
        
        List<Vertex> v0Ls = Arrays.asList(v1, v2);
        List<Vertex> v1Ls = Arrays.asList(v3);
        List<Vertex> v2Ls = Arrays.asList(v4);
        List<Vertex> v3Ls = Arrays.asList(v0, v4);
        List<Vertex> v4Ls = Arrays.asList(); //tested for no downstream 
        
        // no duplicates in the lists
        assertEquals(m.getDownstreamNeighbors(v0).size(), v0Ls.size());
        assertTrue(m.getDownstreamNeighbors(v0).containsAll(v0Ls));
        
        assertEquals(m.getDownstreamNeighbors(v1).size(), v1Ls.size());
        assertTrue(m.getDownstreamNeighbors(v1).containsAll(v1Ls));

        //compare ls only
        assertEquals(ls.getDownstreamNeighbors(v2).size(), v2Ls.size());
        assertTrue(ls.getDownstreamNeighbors(v2).containsAll(v2Ls));

        assertEquals(m.getDownstreamNeighbors(v3).size(), v3Ls.size() );
        assertTrue(m.getDownstreamNeighbors(v3).containsAll(v3Ls));

        //compare ls and m - mix and match 
        assertEquals(m.getDownstreamNeighbors(v4).size(), v4Ls.size()); 
        assertTrue(ls.getDownstreamNeighbors(v4).containsAll(v4Ls));

    }
    
    @Test 
    public void testUpStream() {
        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        m.addVertex(v3);
        m.addVertex(v4);
        m.addVertex(v5);
        
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        ls.addVertex(v3);
        ls.addVertex(v4);
        ls.addVertex(v5);
        
        m.addEdge(v0, v1);
        m.addEdge(v0, v2);
        m.addEdge(v1, v3);
        m.addEdge(v2, v4);
        m.addEdge(v3, v0);
        m.addEdge(v3, v4);
        m.addEdge(v5, v4);
        
        ls.addEdge(v0, v1);
        ls.addEdge(v0, v2);
        ls.addEdge(v1, v3);
        ls.addEdge(v2, v4);
        ls.addEdge(v3, v0);
        ls.addEdge(v3, v4);
        ls.addEdge(v5, v4);
        
        List<Vertex> v0Ls = Arrays.asList(v3);
        List<Vertex> v1Ls = Arrays.asList(v0);
        List<Vertex> v2Ls = Arrays.asList(v0);
        List<Vertex> v3Ls = Arrays.asList(v1);
        List<Vertex> v4Ls = Arrays.asList(v2, v3, v5);
        List<Vertex> v5Ls = Arrays.asList(); //tested for no upstream
        
        // no duplicates in the lists
        assertEquals(m.getUpstreamNeighbors(v0).size(), v0Ls.size());
        assertTrue(m.getUpstreamNeighbors(v0).containsAll(v0Ls));

        assertEquals(m.getUpstreamNeighbors(v1).size(), v1Ls.size());
        assertTrue(m.getUpstreamNeighbors(v1).containsAll(v1Ls));

        //compare ls only
        assertEquals(ls.getUpstreamNeighbors(v2).size(), v2Ls.size());
        assertTrue(ls.getUpstreamNeighbors(v2).containsAll(v2Ls));

        assertEquals(m.getUpstreamNeighbors(v3).size(), v3Ls.size());
        assertTrue(m.getUpstreamNeighbors(v3).containsAll(v3Ls));

        //compare ls and m - mix and match 
        assertEquals(m.getUpstreamNeighbors(v4).size(), v4Ls.size());
        assertTrue(ls.getUpstreamNeighbors(v4).containsAll(v4Ls));

        //compare ls only 
        assertEquals(ls.getUpstreamNeighbors(v5).size(), v5Ls.size() );
        assertTrue(ls.getUpstreamNeighbors(v5).containsAll(v5Ls));

    }
    
    @Test 
    //Testing the directionality of a simple 3 vtx graph
    public void testEdgeExist() {

        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        
        assertFalse(m.edgeExists(v1, v2));
        assertFalse(m.edgeExists(v0, v2));
        assertFalse(m.edgeExists(v0, v1));
        
        assertFalse(ls.edgeExists(v1, v2));
        assertFalse(ls.edgeExists(v0, v2));
        assertFalse(ls.edgeExists(v0, v1));
        
        m.addEdge(v2, v0);
        ls.addEdge(v2, v0);

        assertTrue(m.edgeExists(v2, v0));
        assertTrue(ls.edgeExists(v2, v0));

        assertFalse(m.edgeExists(v0, v2)); 
        assertFalse(ls.edgeExists(v0, v2));
    }

}
