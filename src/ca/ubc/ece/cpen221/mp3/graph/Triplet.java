package ca.ubc.ece.cpen221.mp3.graph;

/* 
 * This class instantiates an immutable tuple of three elements i.e. triplet
 * Each element can be different data types
 * 
 */
public class Triplet implements Cloneable {
    
    private final Object x;
    private final Object y;
    private final Object z;

    
    /**
     * Create a new triplet given three objects
     * 
     * @param x
     * @param y
     * @param z
     * 
     */
    public Triplet( Object x, Object y, Object z ){
        
        this.x = x;
        this.y = y;
        this.z = z;
        
    }
    
    /**
     * Check equality of triplets. This method overrides equals( ) in Object.
     * 
     * @return true if this triplet is equal to the obj otherwise return false
     */
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Triplet)){
            return false;
        }
        Triplet other = (Triplet) obj;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }
    
    
    /**
     * Obtain a string representation of the triplet
     * 
     * @return the triplet as its string
     *         representation.
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


}