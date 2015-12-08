package ca.ece.ubc.cpen221.mp5;

/** Exception thrown when client sends an ill-formatted request to a 
 * RestaurantDB server. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */


public class RequestFormatException extends Exception{
    
    /**
     * Constructs a RequestFormatException with no message
     */
    public RequestFormatException(){
        super(); //refers to a constructor of the superclass i.e. Exception
    };
    
    /**
     * Constructs a RequestFormatException with the detail message
     * 
     * @param message
     */
    public RequestFormatException( String message ){
        super( message );
    }
}
