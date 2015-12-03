package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a server that will instantiate a database and process queries from multiple clients concurrently. 
 * Returns JSON formatted string to client if client requests or database updates are illegal/invalid/incorrect.
 * 
 * @author guess_000
 */

public class RestaurantDBServer {

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param filename1
	 * @param filename2
	 * @param filename3
	 * @throws IOException 
	 */
	public RestaurantDBServer(int port, String filename1, String filename2, String filename3) throws IOException {

	    String restaurantDetailsFile = filename1;
	    String reviewsDetailsFile = filename2;
	    String usersDetailsFile = filename3;
	    
	    try( 
	            ServerSocket rdbsSocket = new ServerSocket( port );
	            Socket clientSocket = rdbsSocket.accept(); //Waits for client to connect, blocks other threads until successful
	            //Set output stream to client 
	            PrintWriter output = new PrintWriter( clientSocket.getOutputStream(), true ); //autoflush on every println, printf, format
	            //Get input stream from client
	            BufferedReader input = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
	    ){
	        
	        //Client connection successful here! 
	        //Return output according to client query.
	        
	        String inputLine = input.toString();
	        Set<Restaurant> outputSet = new HashSet<Restaurant>();
	        
	        //Create instance of RDB
	        RestaurantDB rdb = new RestaurantDB( restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
	        //Get set of restaurants to return
	        outputSet = rdb.query( inputLine );
	        
	        //Convert Set<Restaurant> to String in JSON format
	        String outputString = "";
	        for( Restaurant restaurant : outputSet ){
	            outputString += convertRestaurantToJSON( restaurant );
	        }
	        //Return resulting string to client
	        output.print( outputString );

	        
	    } catch ( IOException ioe ){
	        System.out.println("Exception caught when trying to listen on port " + port + "or listening for a connection");
	        System.out.println( ioe.getMessage() );
	    }
	    
	    
	    //TODO: Make TestClient class
	    
	}
	
	public String randomReview( String restaurantName ){
	    //return random review in JSON format
	    //if more than one restaurant matches name, choose either of one
	    return null;
	}
	
	public String getRestaurant( String businessId ){
	    return null;
	}
	
	public void addRestaurant( String restaurantJSON ){
	    //add restaurant to RestaurantDB object
	}
	
	public void addUser( String userJSON ){
	    //add user to RestaurantDB object
	}
	
	public void addReview( String reviewJSON ){
	    //add review to RestaurantDB object
	}
	
	//TODO: PARSING METHODS; discuss with Shirley how to implement these methods
	
	//Converts a Restaurant into a JSON formatted string
	private String convertRestaurantToJSON( Restaurant restaurant ){
	    return null;
	}
	
	//Converts a Review into a JSON formatted string
    private String convertReviewToJSON( Review review ){
        return null;
    }
    
    //Converts a User into a JSON formatted string
    private String convertUserToJSON( User user ){
        return null;
    }
	
	//Converts a JSON formatted string that represents a Restaurant into its Java object form
	private Restaurant convertToRestaurant( String restaurantJSON ){
	    return null;
	}
	
	//Converts a JSON formatted string that represents a Review into its Java object form
    private Review convertToReview( String reviewJSON ){
        return null;
    }
    
    //Converts a JSON formatted string that represents a User into its Java object form
    private User convertToUser( String userJSON ){
        return null;
    }
	
    //MAIN
    
//	public static void main( String[] args ) throws ReservedPortException, IOException{
//	    
//	    //Check number of arguments passed
//	    if( args.length != 4 ){
//	        System.err.println("Usage: java RestaurantDBServer /n"
//	                + "    <port number>/n"
//	                + "    <name of file containing restaurants>/n"
//	                + "    <name of file containing review>/n"
//	                + "    <name of file containing users>");
//	        System.exit(1);
//	    }
//	    
//	    //Get command line arguments
//	    int port = Integer.parseInt(args[0]);
//	    String restaurantDetailsFile = args[1];
//        String reviewsDetailsFile = args[2];
//        String usersDetailsFile = args[4];
//        boolean listening = true;
//	    
//	    //Don't forget to check for reserved (well-known) port numbers
//	    if( port >=0 && port <= 1023 ){
//	        throw new ReservedPortException("Using well-known port " + port);
//	    }
//	    
//	    //Create instance of RDBS using args values, loop infinitely
//	    RestaurantDBServer rdbs = new RestaurantDBServer( port, restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
//	    //while( listening );
//	    
//	}

}
