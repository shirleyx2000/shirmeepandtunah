package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 * 
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
		// TODO: Implement this method
	    
	    //Restaurant dealios 
	    Map<String, RestaurantStruct> nomNoms = new HashMap<String, RestaurantStruct>(); 
//	    Map<String, Map<String, String>> nomNoms = new HashMap<String, Map<String, String>>(); 
	    Map<String, String> nomNomInfo = new HashMap<String, String>();
	    List<String> nomFieldsStr = Arrays.asList("type", "business_id", "name", "neighbours", "full_address", 
	            "city", "state", "photo_url", "categories", "open", "schools", "url", "longitude", "latitude", "stars", "review_count", "price"); 
//	    List<String> nomFieldsInt = Arrays.asList("longitude", "latitude", "stars", "review_count", "price");
	    
	    //Review dealios 
	    
	    //User dealios
	    
	    //OPTION 1: EVALUATE EACH INDIVIDUAL LINE
	    BufferedReader in;
        try {
            in = new BufferedReader(
                    new FileReader("C:\\Users\\Shirley\\Documents\\Shirley2015\\CPEN221\\mp5-fall2015\\data\\" + restaurantJSONfilename));
            String str; 
            
            try {
                while ((str=in.readLine()) != null) {
//                  System.out.println(str); 
                  //str contains each line holding a restaurant
                    Object obj = JSONValue.parse(str);
                    JSONObject array = (JSONObject)obj; 
                    String name = (String) array.get("name");
                    System.out.println(name); 
                    RestaurantStruct nomNomStruct = new RestaurantStruct((String) array.get("type"), 
                                                                        (String) array.get("business_id"), 
                                                                        (String) array.get("name"),
                                                                        (String) array.get("full_address"),
                                                                        (String) array.get("city"),
                                                                        (String) array.get("state"),
                                                                        (String) array.get("photo_url"),
                                                                        (String) array.get("url"),
                                                                        (double) array.get("longitude"), (double) array.get("latitude"), 
                                                                        (double) array.get("stars"), (long) array.get("review_count"), 
                                                                        (long) array.get("price"), (boolean) array.get("open"), 
                                                                        (List<String>) array.get("categories"), 
                                                                        (List<String>) array.get("neighbours"), (List<String>) array.get("schools")); 
                    nomNoms.put(name, nomNomStruct);

                    System.out.println(nomNoms);
                      
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	//TO DELETE, testing only 
	public static void main (String [] args) {
	    RestaurantDB res = new RestaurantDB ("restaurants.json", "reviews.json", "users.json");
	}
	
	/** 
	 * 
	 * 
	 * 
	 * @param queryString
	 * @return Set<Restaurant> 
	 */
	public Set<Restaurant> query(String queryString) {
		// TODO: Implement this method
		// Write specs, etc.
	    // search that retrieves a set of restaurants given a query = 
	    // comb. of names, neighbourhoods, categories, rating, price 
		return null;
	}

}
