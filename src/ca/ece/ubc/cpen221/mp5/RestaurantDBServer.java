package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    private ServerSocket serverSocket;
    private RestaurantDB rdb;

	/**
	 * Makes a RestaurantDBServer that listens for connections on port
	 * 
	 * @param port         Port number, requires 0 <= port <= 65535
	 * @param filename1    Restaurant details in JSON format
	 * @param filename2    Reviews details in JSON format
	 * @param filename3    Users details in JSON format
	 */
	public RestaurantDBServer(int port, String filename1, String filename2, String filename3) throws IOException {

	    String restaurantDetailsFile = filename1;
	    String reviewsDetailsFile = filename2;
	    String usersDetailsFile = filename3;
	    
	    rdb = new RestaurantDB( restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
	    serverSocket = new ServerSocket( port );
	    
	}
	
	/**
	 * Run the server, listening for connections and handling them
	 * 
	 * @throws IOException     
	 *             if the main server socket is broken
	 */
	public void serve() throws IOException {
	    while ( true ){
	        //Blocks other thread until a client connects
	        final Socket clientSocket = serverSocket.accept();
	        //Create a new thread to handle current client
	        Thread handler = new Thread( new Runnable() {
	            public void run() { //Define thread execution
	                try {
	                    try {
	                        handle( clientSocket );
	                    } finally {
	                        clientSocket.close();
	                    }
	                } catch ( IOException ioe ){
	                    //Exception doesn't terminate server() b/c on diff thread.
	                    ioe.printStackTrace();
	                }
	            }
	        });
	        //Start thread execution
	        handler.start();
	    }
	}
	
	/**
	 * Handles a single client connection. Input from client includes:
	 *     Queries
	 *     Requests
	 *         randomReview("Restaurant Name")
	 *         getRestaurant("businessId")
	 *         addRestaurant("Restaurant Details in JSON format")
	 *         addUser("User details in JSON format")
	 *         addReview("Review details in JSON format")
	 * Returns when client disconnects.
	 * 
	 * @param clientSocket
	 * @throws IOException if connection has error or terminates unexpectedly
	 */
	private void handle( Socket clientSocket ) throws IOException {
	    System.err.println("client connected huzzah");
	    
	    //Get client socket's input stream. 
	    //InputStreamReader converts byte stream to character stream.
	    //BufferedReader allow reading one line at a time.
        BufferedReader in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        
        //PrintWriter is easy to use. Auto-flush on.
        //Wrap client's output stream with OutputStreamWriter; converts character stream to byte stream
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                clientSocket.getOutputStream()), true);
        
        try {
            //TODO: Modify this to account for other client requests!
            //Each query is a single-line string
            for( String query = in.readLine(); query != null; query = in.readLine() ){
                System.err.println("query : " + query);
                try {
                    //Get query reply from database
                    String replyJson = "";
                    Set<Restaurant> restaurants = rdb.query( query );
                    for( Restaurant r : restaurants ){
                        //Separate each restaurant with a new line
                        replyJson.concat(r.toString() + "\n");
                    }
                    System.err.println("reply : " + replyJson);
                    out.println(replyJson);
                } catch (QueryFormatException qfe){
                    System.err.println("reply : err");
                    out.println("err\n");
                }
            }
        } finally {
            out.close();
            in.close();
        }
	}
	
	/**
	 * Gets a random review from a restaurant.
	 * 
	 * @param restaurantName
	 * @return randomReviewJSON    Random review of restaurant in JSON format
	 *                             If restaurant not found, empty string returned.
	 */
	private String randomReview( String restaurantName ){
	    //return random review in JSON format
	    //if more than one restaurant matches name, choose either of one
	    
	    String randomReviewJSON = "";
	    
	    //Iterate through map of all restaurants
	    //Get the Restaurant object (value) given the name (key)
	    //Restaurant object should have collection of reviews
	    //Get random review from collection
	    //Return randomReview.toJsonString();
	    return randomReviewJSON;
	}
	
	/**
	 * Gets a restaurant from the Restaurant database with an associated businessId
	 * 
	 * @param businessId       
	 * @return restaurantJSON     Restaurant in JSON format
	 *                            If restaurant not found, empty string returned. 
	 */
	private String getRestaurant( String businessId ){
	    //return restaurant in JSON format
	    
	    String restaurantJSON = "";
	    
	    //Get the collection of values (Restaurant objects) from all_restaurants map
	    //Iterate through restaurant objects 
	        //Check if restuarant.businessId equals businessId
	            //return restaurant.toJsonString();
	    return restaurantJSON;
	}
	
	/**
	 * Adds a restaurant to Restaurant database.
	 * 
	 * @param restaurantJSON   Restaurant in JSON format
	 */
	private void addRestaurant( String restaurantJSON ){
	    //add restaurant to RestaurantDB object
	    rdb.addRestaurant( restaurantJSON );
	}
	
	/**
	 * Adds a user to Restaurant database
	 * 
	 * @param userJSON     User in JSON format
	 */
	private void addUser( String userJSON ){
	    //add user to RestaurantDB object
	    rdb.addUser( userJSON );
	}
	
	/**
	 * Adds a review to Restaurant database
	 * 
	 * @param reviewJSON   Review in JSON format
	 */
	private void addReview( String reviewJSON ){
	    //add review to RestaurantDB object
	    rdb.addReview( reviewJSON );
	}
	
    //MAIN
    
	public static void main( String[] args ) throws ReservedPortException, IOException{
	    
	    //Check number of arguments passed
	    if( args.length != 4 ){
	        System.err.println("Usage: java RestaurantDBServer /n"
	                + "    <port number>/n"
	                + "    <name of file containing restaurants>/n"
	                + "    <name of file containing review>/n"
	                + "    <name of file containing users>");
	        System.exit(1);
	    }
	    
	    //Get command line arguments
	    int port = Integer.parseInt(args[0]);
	    String restaurantDetailsFile = args[1];
        String reviewsDetailsFile = args[2];
        String usersDetailsFile = args[4];
	    
	    //Create instance of RDBS, returns only if IOException
	    try{
	        RestaurantDBServer rdbs = new RestaurantDBServer( port, restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
	        rdbs.serve();
	    } catch ( IOException ioe ){
	        ioe.printStackTrace();
	    }
	    
	}

}
