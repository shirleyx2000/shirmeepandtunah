package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	    
	    Map json = generateDictionary(restaurantJSONfilename);
        
        System.out.println("\n\n\n");
        Map json2 = generateDictionary(reviewsJSONfilename);
        
        System.out.println("\n\n\n");
        Map json3 = generateDictionary(usersJSONfilename);
        
	}

	/**
	 * This helps the constructor generate a java map from the JSON file
	 * 
	 * @param file
	 * @return json 
	 */
	public Map generateDictionary(String file) {
	    
        BufferedReader in;
        Map json = new HashMap(); 
        try {
            in = new BufferedReader(
                    new FileReader("C:\\Users\\Shirley\\Documents\\Shirley2015\\CPEN221\\mp5-fall2015\\data\\" + file));
            String str; 
            
            try {
                while ((str=in.readLine()) != null) {
                    JSONParser parser = new JSONParser(); 
                    ContainerFactory containerFactory = new ContainerFactory() {
                        public List createArrayContainer() {
                            return new LinkedList(); 
                        }
                        
                        public Map createObjectContainer() {
                            return new LinkedHashMap(); 
                        }

                      @Override
                      public List creatArrayContainer() {
                          // TODO Auto-generated method stub
                          return null;
                      }
                    };
                    
                    try {
                        json = (Map)parser.parse(str, containerFactory);
                        Iterator iter = (Iterator) json.entrySet().iterator(); 
                        while(iter.hasNext()) {
                            Map.Entry entry = (Map.Entry)iter.next(); 
                        }
                        System.out.println(JSONValue.toJSONString(json));
                        System.out.println(json.get("type"));
                        
                    } catch (ParseException pe) {
                        System.out.println(pe);
                    }
                      
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return json; 
	}
	
	
	//TO DELETE, testing only 
	public static void main (String [] args) {
	    RestaurantDB res = new RestaurantDB ("restaurants.json", "reviews.json", "users.json");
	}
	
	/** 
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
