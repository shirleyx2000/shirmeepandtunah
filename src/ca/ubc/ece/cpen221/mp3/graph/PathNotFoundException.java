package ca.ubc.ece.cpen221.mp3.graph;

/** Exception thrown when ... is encountered
 * by the program. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */

public class PathNotFoundException extends Exception {

    /**
     * Constructs a PathNotFoundException with no message
     */
    public PathNotFoundException(){
        super(); //refers to a constructor of the superclass i.e. Exception
    };
    
    /**
     * Constructs a PathNotFoundException with the detail message
     * 
     * @param message
     */
    public PathNotFoundException( String message ){
        super( message );
    }
    
}
