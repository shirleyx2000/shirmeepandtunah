package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class RestaurantDBClientMulti{
    
    public static void startClient(String requestMessage){
        //GOAL: time slicing in the server 
        
        //Create a RDBClient = starting a new client thread to leech
        (new Thread() {
            RestaurantDBClient rdbc;
            @Override 
            public void run() {
                try {
//                    Socket s = new Socket("localhost", 4949 );
//                    BufferedWriter out = new BufferedWriter (
//                            new OutputStreamWriter(s.getOutputStream()));
//                    
//                    BufferedReader in = new BufferedReader ( 
//                            new InputStreamReader(s.getInputStream()));
                    
                    rdbc = new RestaurantDBClient( "localhost", 4949 );
                    
                    //send request
                    rdbc.sendRequest(requestMessage);
                 // reply
                    rdbc.getReply();
                    
                    
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        rdbc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); 
        
        //
        
    }
    
    public static void main (String[] args) throws IOException {
        
        String[] testRequests = {
                //Queries
//                "in(\"Telegraph Ave\")",
//                "addUser({\"url\": \"http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog\", "
//                        + "\"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14}, "
//                        + "\"review_count\": 29, "
//                        + "\"type\": \"user\", "
//                        + "\"user_id\": \"1234567890abcdefghi\", "
//                        + "\"name\": \"Chris Mickdeezus.\", "
//                        + "\"average_stars\": 3.89655172413793})",
//                "price(1..2)",
                "category(\"Chinese\") || category(\"Italian\")",
//                "in(\"Telegraph Ave\") && price(1..2)",
                "in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)",
//                "randomReview(\"Happy Valley\")",
                //"getRestaurant(\"1234abcdeEFGH\")" //Should return Happy Valley
        };
        
        for( String s : testRequests ){
            startClient(s);
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        
    }
    
}