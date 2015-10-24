package ca.ubc.ece.cpen221.mp3.graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    static private Vertex v6; //no down or upstream
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        v0 = new Vertex("zero"); 
        v1 = new Vertex("one"); 
        v2 = new Vertex("two"); 
        v3 = new Vertex("three"); 
        v4 = new Vertex("four"); 
        v5 = new Vertex("five"); 
        v6 = new Vertex("six");
        
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
        
        //Creating edges both up and downstream for v1; leave v3 floating
        ls.addEdge(v0, v1);
        ls.addEdge(v1, v0);
        ls.addEdge(v1, v2);
        ls.addEdge(v2, v1);
        
        
        //Create set of neighbours to check against duplicates in original lists
        Set<Vertex> setListUpstreamNeighbours = new HashSet<Vertex>();
        Set<Vertex> setListDownstreamNeighbours = new HashSet<Vertex>();
        Set<Vertex> setMatrixUpstreamNeighbours = new HashSet<Vertex>();
        Set<Vertex> setMatrixDownstreamNeighbours = new HashSet<Vertex>();
        
        //Does not matter which vertex we use to get neighbours
        
        for (Vertex v : ls.getUpstreamNeighbors(v3)) {
            setListUpstreamNeighbours.add(v);
        }
        
        for (Vertex v : ls.getDownstreamNeighbors(v3)) {
            setListDownstreamNeighbours.add(v);
        }
        
        for (Vertex v : m.getDownstreamNeighbors(v1)) {
            setMatrixDownstreamNeighbours.add(v);
        }
        
        for (Vertex v : m.getUpstreamNeighbors(v1)) {
            setMatrixDownstreamNeighbours.add(v);
        }

        //Assert no duplicates
        Arrays.asList("three");
        System.out.println(ls.getUpstreamNeighbors(v3) + "     vs     " + setListUpstreamNeighbours);
        assertTrue(ls.getUpstreamNeighbors(v3).equals(Arrays.asList("three")));
        assertTrue(ls.getDownstreamNeighbors(v3).equals(setListDownstreamNeighbours));
        assertTrue(m.getUpstreamNeighbors(v1).equals(setMatrixUpstreamNeighbours));
        assertTrue(m.getDownstreamNeighbors(v1).equals(setMatrixDownstreamNeighbours));
        
        //Check edge properties of List graph; no duplicate edges should exist
        assertFalse(ls.edgeExists(v1, v3));
        System.out.println(ls.getUpstreamNeighbors(v1) + "     vs     " + ls.getDownstreamNeighbors(v1));
        
        assertTrue((ls.getUpstreamNeighbors(v1)).equals((ls.getDownstreamNeighbors(v1))));
        assertTrue(ls.getUpstreamNeighbors(v3).equals(ls.getDownstreamNeighbors(v3)));
        
      //Check edge properties of Matrix graph
        assertFalse(m.edgeExists(v1, v3));
        assertTrue(m.getUpstreamNeighbors(v1).equals(m.getDownstreamNeighbors(v1)));
        assertTrue(m.getUpstreamNeighbors(v3).equals(m.getDownstreamNeighbors(v3)));
    }
    
    @Test 
    // Testing whether all vertices exists in graph regardless of edges
    public void testGetVertices() {
        //When list is empty, set vertex to be 
        assertEquals(m.getVertices(), Arrays.asList());
        assertEquals(ls.getVertices(), Arrays.asList());

        m.addVertex(v0);
        m.addVertex(v1);
        m.addVertex(v2);
        m.addVertex(v3);
        m.addVertex(v4);
        m.addVertex(v5);
        //input random edges for m
        m.addEdge(v5, v1);
        assertEquals(m.getVertices(), Arrays.asList(v0, v1, v2, v3, v4, v5));
        
        ls.addVertex(v0);
        ls.addVertex(v1);
        ls.addVertex(v2);
        ls.addVertex(v3);
        ls.addVertex(v4);
        ls.addVertex(v5);
        //input random edges for ls
        ls.addEdge(v0, v2);
        ls.addEdge(v4, v3);
        
        assertEquals(m.getVertices(), ls.getVertices());
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
        boolean equalLists = m.getDownstreamNeighbors(v0).size() == v0Ls.size() 
                          && m.getDownstreamNeighbors(v0).containsAll(v0Ls);
        assertTrue(equalLists);
        equalLists = m.getDownstreamNeighbors(v1).size() == v1Ls.size() 
                  && m.getDownstreamNeighbors(v1).containsAll(v1Ls);
        assertTrue(equalLists);
        //compare ls only
        equalLists = ls.getDownstreamNeighbors(v2).size() == v2Ls.size() 
                  && ls.getDownstreamNeighbors(v2).containsAll(v2Ls);
        assertTrue(equalLists);
        equalLists = m.getDownstreamNeighbors(v3).size() == v3Ls.size() 
                  && m.getDownstreamNeighbors(v3).containsAll(v3Ls);
        assertTrue(equalLists);
        //compare ls and m - mix and match 
        equalLists = m.getDownstreamNeighbors(v4).size() == v4Ls.size() 
                  && ls.getDownstreamNeighbors(v4).containsAll(v4Ls);
        assertTrue(equalLists);
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
        List<Vertex> v2Ls = Arrays.asList(v3);
        List<Vertex> v3Ls = Arrays.asList(v1);
        List<Vertex> v4Ls = Arrays.asList(v2, v3, v5);
        List<Vertex> v5Ls = Arrays.asList(); //tested for no upstream
        
        // no duplicates in the lists
        boolean equalLists = m.getUpstreamNeighbors(v0).size() == v0Ls.size() 
                          && m.getUpstreamNeighbors(v0).containsAll(v0Ls);
        assertTrue(equalLists);
        equalLists = m.getUpstreamNeighbors(v1).size() == v1Ls.size() 
                  && m.getUpstreamNeighbors(v1).containsAll(v1Ls);
        assertTrue(equalLists);
        //compare ls only
        equalLists = ls.getUpstreamNeighbors(v2).size() == v2Ls.size() 
                  && ls.getUpstreamNeighbors(v2).containsAll(v2Ls);
        assertTrue(equalLists);
        equalLists = m.getUpstreamNeighbors(v3).size() == v3Ls.size() 
                  && m.getUpstreamNeighbors(v3).containsAll(v3Ls);
        assertTrue(equalLists);
        //compare ls and m - mix and match 
        equalLists = m.getUpstreamNeighbors(v4).size() == v4Ls.size() 
                  && ls.getUpstreamNeighbors(v4).containsAll(v4Ls);
        assertTrue(equalLists);
        //compare ls only 
        equalLists = ls.getUpstreamNeighbors(v5).size() == v5Ls.size() 
                && ls.getUpstreamNeighbors(v5).containsAll(v5Ls);
        assertTrue(equalLists);
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
        
        boolean mEdge, lsEdge;  
        m.addEdge(v2, v0);
        ls.addEdge(v2, v0);

        mEdge = ls.edgeExists(v2, v0);
        lsEdge = ls.edgeExists(v2, v0);
        assertTrue(mEdge); 
        assertTrue(lsEdge);

        mEdge = ls.edgeExists(v0, v2);
        lsEdge = ls.edgeExists(v0, v2);
        assertFalse(mEdge); 
        assertFalse(lsEdge);
    }

}
