package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class RestaurantDBClient {
    
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    
    //Rep invariant: socket, in, out != null
    
    public RestaurantDBClient(String hostname, int port) throws IOException {
        clientSocket = new Socket( hostname, port );
        in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ));
        out = new PrintWriter( new OutputStreamWriter( clientSocket.getOutputStream() ));
    }
    
    public void sendRequest( String clientRequest ) throws IOException {
        out.print(clientRequest + "\n");
        out.flush(); //ensures request gets sent
    }
    
    public String getReply() throws IOException {
        String reply = in.readLine();
        if( reply == null ){
            throw new IOException("connection terminated unexpectedly");
        }
        return reply;
    }
    
    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
    public static void main(String[] args){
        int somePort = 4949;
        
        try{
            RestaurantDBClient rdbc = new RestaurantDBClient("localhost", somePort);
            
            // send the requests and get the replies
            String[] testRequests = {
                    //Queries
                    "in(\"Telegraph Ave\")",
                    "price(1..2)",
                    "category(\"Chinese\") || category(\"Italian\")",
                    "in(\"Telegraph Ave\") && price(1..2)",
                    "in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)",
                    //Requests
                    "randomReview(\"Happy Valley\")",
                    "getRestaurant(\"ERRowW4pGO6pK9sVYyA1nQ\")", //Should return Happy Valley
                    "addRestaurant({\"open\": true, "
                                    + "\"url\": \"http://www.yelp.com/biz/aw-hells-yeah\", "
                                    + "\"longitude\": -122.260408, "
                                    + "\"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], "
                                    + "\"business_id\": \"1234abcdeEFGH\", "
                                    + "\"name\": \"Aw Hells Yeah\", "
                                    + "\"categories\": [\"Cafes\", \"Restaurants\"], "
                                    + "\"state\": \"CA\", \"type\": "
                                    + "\"business\", "
                                    + "\"stars\": 2.0, "
                                    + "\"city\": \"Berkeley\", "
                                    + "\"full_address\": \"2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701\", "
                                    + "\"review_count\": 9, "
                                    + "\"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", "
                                    + "\"schools\": [\"University of California at Berkeley\"], "
                                    + "\"latitude\": 37.867417, "
                                    + "\"price\": 1})",
                    "addUser({\"url\": \"http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog\", "
                                + "\"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14}, "
                                + "\"review_count\": 29, "
                                + "\"type\": \"user\", "
                                + "\"user_id\": \"_NH7Cpq3qZkByP5xR4gXog\", "
                                + "\"name\": \"Chris Mickdeezus.\", "
                                + "\"average_stars\": 3.89655172413793})",
                    "addReview({\"type\": \"review\", "
                                + "\"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", "
                                + "\"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, "
                                + "\"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", "
                                + "\"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\n\n"
                                             + "Oh, and the pasta is even worse than the pizza. blah blah blah\", "
                                + "\"stars\": 2, "
                                + "\"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", "
                                + "\"date\": \"2006-07-26\"})"
            };
            
            for( String request : testRequests ){
                rdbc.sendRequest(request);
                System.out.println("request " + request);
                System.out.println("reply " + rdbc.getReply());
            }
            
            rdbc.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}