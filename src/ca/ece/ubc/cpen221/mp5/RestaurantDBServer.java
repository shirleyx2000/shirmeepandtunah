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
 * Referenced from https://github.com/CPEN-221/example15 SocialServer.java
 * 
 */

public class RestaurantDBServer {
    
    int port;
    RestaurantDB rdb;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param filename1
	 * @param filename2
	 * @param filename3
	 */
	public RestaurantDBServer(int port, String filename1, String filename2, String filename3) throws IOException {

	    String restaurantDetailsFile = filename1;
	    String reviewsDetailsFile = filename2;
	    String usersDetailsFile = filename3;
	    
	    rdb = new RestaurantDB( restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
	    this.port = port;
	    
	}
	
	public void serve(){
        
        try{
            
            ServerSocket rdbsSocket = new ServerSocket( port );
            
            while( true ){
                //Blocks other threads until a client connects
                final Socket clientSocket = rdbsSocket.accept(); 
                
                //Start a new thread to handle connection
                Thread thread = new Thread( new Runnable() {
                    public void run(){
                        //Thread belongs to current client socket; not touched again by main thread
                        handle( clientSocket );
                    }
                });
                
                thread.start();
                //Thread stops when client disconnects
            }

        } catch ( IOException ioe ){
            System.out.println("Exception caught when trying to listen on port " + port + "or listening for a connection");
            System.out.println( ioe.getMessage() );
            return;
        }
	}
	
	/**
	 * Handles a single client connection. Returns when client disconnects.
	 * @param clientSocket
	 * @throws IOException if connection has error or terminates unexpectedly
	 */
	public void handle( Socket clientSocket ) {
	    
	    try{
	      //Set output stream to client 
	        PrintWriter output = new PrintWriter( clientSocket.getOutputStream(), true ); //autoflush on every println, printf, format
	        //Get input stream from client
	        BufferedReader input = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
	    
	        //input and output are thread-confined
	        
	        try{
	          //Return output according to client query.
	            String inputLine = input.toString();
	            Set<Restaurant> outputSet = new HashSet<Restaurant>();
	            
	            //Get set of restaurants to return
	            outputSet = rdb.query( inputLine );
	            
	            //Convert Set<Restaurant> to String in JSON format
	            String outputString = "";
	            for( Restaurant restaurant : outputSet ){
	                outputString += convertRestaurantToJSON( restaurant );
	            }
	            //Return resulting string to client; autoflush on
	            output.println( outputString ); 
	        } finally {
	            output.close();
	            input.close();
	            clientSocket.close();
	        }
	    } catch ( IOException ioe ){
	        ioe.printStackTrace();
	    }
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
//	    
//	    //Don't forget to check for reserved (well-known) port numbers
//	    if( port >=0 && port <= 1023 ){
//	        throw new ReservedPortException("Using well-known port " + port);
//	    }
//	    
//	    //Create instance of RDBS, returns only if IOException
//	    RestaurantDBServer rdbs = new RestaurantDBServer( port, restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
//	    
//	}

}
