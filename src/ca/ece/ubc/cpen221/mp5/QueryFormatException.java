package ca.ece.ubc.cpen221.mp5;

/** Exception thrown when a reserved (well-known) port (0-1023) is passed to
 * RestaurantDBServer.java. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */


public class QueryFormatException extends Exception{
    
    /**
     * Constructs a ReservedPortException with no message
     */
    public QueryFormatException(){
        super(); //refers to a constructor of the superclass i.e. Exception
    };
    
    /**
     * Constructs a StackEmptyException with the detail message
     * 
     * @param message
     */
    public QueryFormatException( String message ){
        super( message );
    }
}
