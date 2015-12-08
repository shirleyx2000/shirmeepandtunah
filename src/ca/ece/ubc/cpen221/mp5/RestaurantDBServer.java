package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a server that will instantiate a database and process queries from multiple clients concurrently. 
 * Returns JSON formatted string to client if client requests or database updates are illegal/invalid/incorrect.
 * 
 * @author guess_000
 * Referenced from https://github.com/CPEN-221/example16
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
	    try {
	        System.out.println("Trying to initalized socket");
	        serverSocket = new ServerSocket( port );
	    } catch (IOException ioex) {
	        ioex.printStackTrace();
	    }
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
	                    } catch (IOException ioe){
	                        ioe.printStackTrace(); 
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
        BufferedReader in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                clientSocket.getOutputStream()), true);
        
        System.err.println("Should be getting client request here");
        try {
            //TODO: Modify this to account for other client requests! If request ill-formatted, throw RequestFormatException()
            //Each request is a single-line string
            for( String request = in.readLine(); request != null; request = in.readLine() ){
                //try {
                    //Get query reply from database
                    StringBuilder replyJson = new StringBuilder("");
                    System.err.println("query : " + request);

                    if (request.contains("randomReview")) {
                        request = request.substring(14, request.length()-2);
                        replyJson.append(randomReview(request));
                    } else if (request.contains("getRestaurant")) {
                        request = request.substring(15, request.length()-2);
                        replyJson.append(getRestaurant(request));
                    }
                    else {
                        Set<Restaurant> restaurants = rdb.query( request );
                        for( Restaurant r : restaurants ){
                            //Separate each restaurant with a new line
                            System.err.println(r.getJSONStr());
                            replyJson.append(r.getJSONStr() + "; ");
                        }
                    }
                    
                    System.err.println("query after : " + request);
                    
                    System.err.println("reply : " + replyJson); 
                    out.println(replyJson.toString());
//                } catch (QueryFormatException qfe){
//                    System.err.println("reply : err");
//                    out.println("err\n");
//                }
            }
        } finally {
            System.err.println("closing server client");
            out.close();
            in.close();
        }
	}
	
	/**
	 * Gets a random review from a restaurant. If more than one restaurant matches name, either one is chosen.
	 * 
	 * @param restaurantName
	 * @return randomReviewJSON    Random review of restaurant in JSON format
	 *                             If restaurant not found or no review exists, empty string returned.
	 */
	private String randomReview( String restaurantName ){
	    
	    String randomReviewJSON = "";
	   
	    //Get business_id of restaurant
	    Map<String, Restaurant> allRestaurants = rdb.getAllRestaurants();
        String businessId = allRestaurants.get( restaurantName ).business_id;
	    //Gets all reviews that matches the businessId
        Collection<Review> allReviews = rdb.getAllReviews().values();
        ArrayList<Review> thisRestaurantReviews = new ArrayList<Review>();
        for( Review r : allReviews ){
            if( r.getBusinessId().equals(businessId) ){
                thisRestaurantReviews.add(r);
            }
        }
        //Return a review as JSON string at random index of list of reviews for restaurant
        Random randomGen = new Random(); //Create new random generator
        int randomIndex = randomGen.nextInt(thisRestaurantReviews.size());
        //Make sure that reviews for restaurant actually exist!
        if( thisRestaurantReviews.size() > 0 ){
            randomReviewJSON = thisRestaurantReviews.get(randomIndex).getJsonStr();
        }
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
	    
	    String restaurantJSON = "";
	    
	    //Get the collection of values (Restaurant objects) from all_restaurants map
	    Collection<Restaurant> allRestaurants = rdb.getAllRestaurants().values();
	    //Iterate through restaurant objects 
	    for( Restaurant r : allRestaurants ){
	        //Check if restuarant.businessId equals businessId
	        if( r.business_id.equals(businessId) ){
	            return r.getJSONStr();
	        }
	    }
	    return restaurantJSON;
	}
	
	/**
	 * Adds a restaurant to Restaurant database.
	 *
	 * requires: restaurant's business id must not be in the database
	 * 
	 * @param restaurantJSON   Restaurant in JSON format
	 */
	private void addRestaurant( String restaurantJSON ){
	    rdb.addRestaurant( restaurantJSON );
	}
	
	/**
	 * Adds a user to Restaurant database
	 * 
	 * requires: user is not in database.
	 * 
	 * @param userJSON     User in JSON format
	 */
	private void addUser( String userJSON ){
	    rdb.addUser( userJSON );
	}
	
	/**
	 * Adds a review to Restaurant database
	 * 
	 * requires: review is not in database
	 * 
	 * @param reviewJSON   Review in JSON format
	 */
	private void addReview( String reviewJSON ){
	    rdb.addReview( reviewJSON );
	}
	
	/**
	 * Given a client request, checks if format is valid.
	 * 
	 * @param request
	 * @throws RequestFormatException
	 */
	private void checkRequestFormat( String request ) throws RequestFormatException {
	    
//	    String strCloseParen = "\")";
//	    String randomReviewRegex = "randomReview(\"";
//	    String getRestaurantRegex = "getRestaurant(\"";
//	    String addRestaurantRegex = "addRestaurant(\"";
//	    String addUserRegex = "addUser(\"";
//	    String addReviewRegex = "addReview(\"";
//	    
//	    //Check if request contains any of the strings:
//	    if( (request.indexOf(randomReviewRegex) > -1) 
//	        && (request.indexOf(strCloseParen) > request.indexOf(randomReviewRegex)) ){
//	            return;
//	    } else if( (request.indexOf(getRestaurantRegex) > -1) 
//	            && (request.indexOf(strCloseParen) > request.indexOf(getRestaurantRegex)) ){
//            return;
	    
	}
	
    //MAIN
    
	public static void main( String[] args ) throws IOException{
	    
	    //Check number of arguments passed
	    System.out.println(args[0]);
	    System.out.println(args[1]);
	    System.out.println(args[2]);
	    System.out.println(args[3]);
	    
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
        String usersDetailsFile = args[3];
	    
	    //Create instance of RDBS, returns only if IOException
	    try{
//	        RestaurantDBServer rdbs = new RestaurantDBServer( 4949, "restaurants.json", "reviews.json", "users.json" );
	        RestaurantDBServer rdbs = new RestaurantDBServer( port, restaurantDetailsFile, reviewsDetailsFile, usersDetailsFile );
	        rdbs.serve();
	    } catch ( IOException ioe ){
	        ioe.printStackTrace();
	    }
	    
	}

}
