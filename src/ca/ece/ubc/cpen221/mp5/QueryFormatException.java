package ca.ece.ubc.cpen221.mp5;

/** Exception thrown when a client sends an ill-formatted query to a 
 * RestaurantDB server. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */


public class QueryFormatException extends Exception{
    
    /**
     * Constructs a QueryFormatException with no message
     */
    public QueryFormatException(){
        super(); //refers to a constructor of the superclass i.e. Exception
    };
    
    /**
     * Constructs a QueryFormatException with the detail message
     * 
     * @param message
     */
    public QueryFormatException( String message ){
        super( message );
    }
}
