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
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.graph.PathNotFoundException;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AlgorithmsTest{
    /*
     * Test the following methods:
     * 
     * shortestDistance
     * commonDownstreamVertices
     * commonUpstreamVertices
     * breadthFirstSearch
     * depthFirstSearch
     * 
     * Before each test, use the implementations:
     *
     *    AdjacencyListGraph
     *    AdjacencyMatrixGraph
     * 
     *      to construct the following graphs
     *  
     *    1. Empty Graph
     *    
     *    2. Vertices
     *          0, 1, 2, 3, 4, 5
     *       No edges
     *    
     *    2. Vertices
     *          0, 1, 2, 3, 4, 5, 6
     *       Edges
     *          0 -> 1
     *          0 -> 2
     *          1 -> 3
     *          2 -> 4
     *          3 -> 0
     *          3 -> 4
     *          5 -> 4 (5 has only downstream, 4 has only upstream)
     * 
     */
    
    static private Vertex v0; 
    static private Vertex v1; 
    static private Vertex v2; 
    static private Vertex v3; 
    static private Vertex v4; //no downstream 
    static private Vertex v5; //no upstream
    static private Vertex v6; //floating
    static private ArrayList<Vertex> testVertices;
    
    static private AdjacencyListGraph ls;
    static private AdjacencyMatrixGraph m;
    static private AdjacencyListGraph lsEmpty;
    static private AdjacencyMatrixGraph mEmpty;
    static private AdjacencyListGraph lsNoEdge;
    static private AdjacencyMatrixGraph mNoEdge;
   
    @BeforeClass
    public static void makeVertices(){
        
        testVertices = new ArrayList<Vertex>();
        
        v0 = new Vertex("zero"); 
        v1 = new Vertex("one"); 
        v2 = new Vertex("two"); 
        v3 = new Vertex("three"); 
        v4 = new Vertex("four"); 
        v5 = new Vertex("five"); 
        v6 = new Vertex("six");
        
        testVertices.add(v0);
        testVertices.add(v1);
        testVertices.add(v2);
        testVertices.add(v3);
        testVertices.add(v4);
        testVertices.add(v5);
        testVertices.add(v6);
        
    }
    
    
    @Before
    public void generateGraph(){
        
        makeEmptyGraph();
        makeNoEdgeGraph();
        makeGraph();
  
    }
    
    /**
     * Tests shortestDistance method. 
     * 
     * Possible inputs:
     *      Graphs (List & Matrix)
     *          No edges
     *          Regular
     *          
     *      Vertices
     *          Floating (v6)
     *          No upstream neighbours (v5)
     *          No downstream neighbours (v4)
     *          Both upstream and downstream neighbours (v0, v1)
     *          
     * Test inputs:
     *     1. No edge graph, v0, v1
     *     2. No edge graph, v0, v0
     *     3. Regular graph, v0, v0
     *     4. Regular graph, v0, v6
     *     5. Regular graph, v0, v5
     *     6. Regular graph, v5, v0
     *     7. Regular graph, v0, v4
     *     8. Regular graph, v4, v0
     *     9. Regular graph, v4, v5
     *     10. Regular graph, v5, v4
     *     
     *          
     * Expected outputs:
     *     1. PathNotFoundException
     *     2. 0
     *     3. 0
     *     4. PathNotFoundException
     *     5. PathNotFoundException
     *     6. PathNotFoundException
     *     7. 2
     *     8. PathNotFoundException
     *     9. PathNotFoundException
     *     10. 1
     *     
     * @throws PathNotFoundException 
     *     
     */
    
    @Test
    public void testShortestDistance() throws PathNotFoundException{
        
        //1 
        try{
            Algorithms.shortestDistance(lsNoEdge, v0, v1);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        try{
            Algorithms.shortestDistance(mNoEdge, v0, v1);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        //2
        assertEquals(Algorithms.shortestDistance(lsNoEdge, v0, v0), 0);
        assertEquals(Algorithms.shortestDistance(mNoEdge, v0, v0), 0);
        
        //3
        assertEquals(Algorithms.shortestDistance(ls, v0, v0), 0);
        assertEquals(Algorithms.shortestDistance(m, v0, v0), 0);
        
        //4
        try{
            Algorithms.shortestDistance(ls, v0, v6);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        try{
            Algorithms.shortestDistance(m, v0, v6);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        // 5       
        try{
            Algorithms.shortestDistance(ls, v0, v5);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        try{
            Algorithms.shortestDistance(m, v0, v5);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        // 6       
        try{
            Algorithms.shortestDistance(ls, v5, v0);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        try{
            Algorithms.shortestDistance(m, v5, v0);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        //7
        assertEquals(Algorithms.shortestDistance(ls, v0, v4), 2);
        assertEquals(Algorithms.shortestDistance(m, v0, v4), 2);
        
        //8
        try{
            Algorithms.shortestDistance(ls, v4, v0);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        try{
            Algorithms.shortestDistance(m, v4, v0);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        //9
        try{
            Algorithms.shortestDistance(ls, v4, v5);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        try{
            Algorithms.shortestDistance(m, v4, v5);
            fail("Expected exception not thrown.");
        } catch ( PathNotFoundException pnfe ){
            //Do nothing; exception expected.
        }
        
        //10
        assertEquals(Algorithms.shortestDistance(ls, v5, v4), 1);
        assertEquals(Algorithms.shortestDistance(m, v5, v4), 1);
        
    }

    
    @Test
    public void testCommonDownstreamVertices(){
        
    }
    
    @Test 
    public void testCommonUpstreamVertices(){
        
    }
    
    @Test
    public void testBreadthFirstSearch(){
        
        Set<List<Vertex>> expectedOutput = new HashSet<List<Vertex>>();
        
        List<Vertex> traversal0 = Arrays.asList(v0, v1, v2, v3, v4);
        List<Vertex> traversal1 = Arrays.asList(v1, v3, v0, v4, v2);
        List<Vertex> traversal2 = Arrays.asList(v2, v4);
        List<Vertex> traversal3 = Arrays.asList(v3, v0, v4, v1, v2);
        List<Vertex> traversal4 = Arrays.asList(v4);
        List<Vertex> traversal5 = Arrays.asList(v5, v4);
        
        //Input: Empty graph
        //Output: Empty set
        System.out.println("Empty graph: " + lsEmpty);
        System.out.println(Algorithms.breadthFirstSearch(lsEmpty) + "   vs    " + expectedOutput);
        assertEquals( Algorithms.breadthFirstSearch(lsEmpty), expectedOutput);
        assertEquals( Algorithms.breadthFirstSearch(mEmpty), expectedOutput);
        
        //Input: No edges graph
        //Output: Empty set
        assertEquals( Algorithms.breadthFirstSearch(lsNoEdge), expectedOutput);
        assertEquals( Algorithms.breadthFirstSearch(mNoEdge), expectedOutput);
        
        
        //Input: Regular graph
        //Output: 
//            0, 1, 2, 3, 4
//            1, 3, 0, 4, 2
//            2, 4
//            3, 0, 4, 1
//            4
//            5, 4
        expectedOutput.add(traversal0);
        expectedOutput.add(traversal1);
        expectedOutput.add(traversal2);
        expectedOutput.add(traversal3);
        expectedOutput.add(traversal4);
        expectedOutput.add(traversal5);
        
        assertEquals( Algorithms.breadthFirstSearch(ls), expectedOutput );
        assertEquals( Algorithms.breadthFirstSearch(m), expectedOutput );
               
        
    }
    
    @Test
    public void testDepthFirstSearch(){
        
        Set<List<Vertex>> expectedOutput = new HashSet<List<Vertex>>();
        
        List<Vertex> traversal0 = Arrays.asList(v0, v1, v3, v4, v2);
        List<Vertex> traversal1 = Arrays.asList(v1, v3, v0, v2, v4);
        List<Vertex> traversal2 = Arrays.asList(v2, v4);
        List<Vertex> traversal3 = Arrays.asList(v3, v0, v1, v2, v4);
        List<Vertex> traversal4 = Arrays.asList(v4);
        List<Vertex> traversal5 = Arrays.asList(v5, v4);
        
        //Input: Empty graph
        //Output: Empty set
        assertEquals( Algorithms.depthFirstSearch(lsEmpty), expectedOutput);
        assertEquals( Algorithms.depthFirstSearch(mEmpty), expectedOutput);
        
        //Input: No edges graph
        //Output: Empty set
        assertEquals( Algorithms.depthFirstSearch(lsNoEdge), expectedOutput);
        assertEquals( Algorithms.depthFirstSearch(mNoEdge), expectedOutput);
        
        
        //Input: Regular graph
        //Output: 
//            0, 1, 3, 4, 2
//            1, 3, 0, 2, 4
//            2, 4
//            3, 0, 1, 2, 4
//            4
//            5, 4
        expectedOutput.add(traversal0);
        expectedOutput.add(traversal1);
        expectedOutput.add(traversal2);
        expectedOutput.add(traversal3);
        expectedOutput.add(traversal4);
        expectedOutput.add(traversal5);
        
        assertEquals( Algorithms.depthFirstSearch(ls), expectedOutput );
        assertEquals( Algorithms.depthFirstSearch(m), expectedOutput );
        
    }
    
   
    /*** HELPER METHODS ***/
    
    public void makeEmptyGraph(){
        
        AdjacencyListGraph lsEmpty = new AdjacencyListGraph();
        AdjacencyMatrixGraph mEmpty = new AdjacencyMatrixGraph();
        
    }
    
    public void makeNoEdgeGraph(){
        
        AdjacencyListGraph lsNoEdge = new AdjacencyListGraph();
        AdjacencyMatrixGraph mNoEdge = new AdjacencyMatrixGraph();
        
        for( Vertex v : testVertices ){
            lsNoEdge.addVertex(v);
            mNoEdge.addVertex(v);
        }
        
    }
    
    public void makeGraph(){
        
        AdjacencyListGraph ls = new AdjacencyListGraph();
        AdjacencyMatrixGraph m = new AdjacencyMatrixGraph();
        
        for( Vertex v : testVertices ){
            ls.addVertex(v);
            m.addVertex(v);
        }
        
        ls.addEdge(v0,  v1);
        ls.addEdge(v0,  v2);
        ls.addEdge(v1,  v3);
        ls.addEdge(v2,  v4);
        ls.addEdge(v3,  v0);
        ls.addEdge(v3,  v4);
        ls.addEdge(v4,  v4);
        
        m.addEdge(v0,  v1);
        m.addEdge(v0,  v2);
        m.addEdge(v1,  v3);
        m.addEdge(v2,  v4);
        m.addEdge(v3,  v0);
        m.addEdge(v3,  v4);
        m.addEdge(v4,  v4);
        
        
    }
    
}