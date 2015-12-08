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
    
    /**
     * Make RestaurantDBClient 
     * 
     * @param hostname
     * @param port
     * @throws IOException
     */
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
            RestaurantDBClient rdbc2 = new RestaurantDBClient("localhost", somePort);
            // send the requests and get the replies
            String[] testRequests = {
                    //Queries
                    "in(\"Telegraph Ave\")",
                    //"price(1..2)",
                    //"category(\"Chinese\") || category(\"Italian\")",
                    //"in(\"Telegraph Ave\") && price(1..2)",
                    //"in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)",
                    //"randomReview(\"Happy Valley\")",
                    "getRestaurant(\"ERRowW4pGO6pK9sVYyA1nQ\")" //Should return Happy Valley
            };
            
            for( String request : testRequests ){
                System.out.println("request " + request);
                rdbc.sendRequest(request);
//                rdbc2.sendRequest(request);
                System.out.println("reply " + rdbc.getReply());
//                System.out.println("reply 2 " + rdbc2.getReply());
            }
            
            rdbc.close();
            rdbc2.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}