package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.Triplet;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class TripletTest{
    
    /*
     * Input Cases:
     *      1) (Vertex, String, Integer)
     *      2) (null, null, null)
     */
    
    private static Triplet triplet;
    private static Triplet nullTriplet;
    
    @Before
    public void createTriplets(){
        
        Vertex v = new Vertex("zero");
        String s = "string";
        Integer i = 3;
           
        triplet = new Triplet( v, s, i );
        nullTriplet = new Triplet( null, null, null );
    }
    
    @Test
    public void testEquality(){
        
        
        Vertex vEqual = new Vertex("zero");
        String sEqual = "string";
        Integer iEqual = 0;
        
        Triplet equalTriplet = new Triplet( vEqual, sEqual, iEqual );
        assertTrue(triplet.equals(equalTriplet));
        assertFalse(nullTriplet.equals(equalTriplet));
        
        
        Vertex vNotEqual = new Vertex("one");
        String sNotEqual = "stringFoo";
        Integer iNotEqual = 1;
        
        Triplet notEqualTriplet = new Triplet( vNotEqual, sNotEqual, iNotEqual );
        assertFalse(triplet.equals(notEqualTriplet));
        assertFalse(nullTriplet.equals(notEqualTriplet));
        
        assertFalse(triplet.equals(nullTriplet));
        
        
    }
    
    @Test
    public void testToString(){
        
        String tripletString = "(zero, string, 0)";
        String nullTripletString = "(null, null, null)";
        
        assertTrue(triplet.toString().equals(tripletString));
        assertTrue(nullTriplet.toString().equals(nullTripletString));
        
    }
    
//    @Test 
//    public void testAssignment(){
//        
//        Vertex vNew = new Vertex("two");
//        String sNew = "stringFooFoo";
//        Integer iNew = 2;
//        
//    }

}