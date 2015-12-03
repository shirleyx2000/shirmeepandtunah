package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
/**
 * Abstract datatype: List of maps per restaurant/review/users represented by their respective ADT
 * Rep invariant: dataset will always keep 3 data file information separate. 
 * 
 * @author Shirley
 *
 */
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
        // TODO: Generate a list of restaurants/reviews/users objects, instead of a list of java map exactly like json
        
        List<Map> obj_restuarant = new ArrayList<Map>(); 
        Map<String, Restaurant> each_restaurant = new HashMap<String, Restaurant>(); 
        
        List<Map> json_restaurant = generateDictionary(restaurantJSONfilename);
         
        for (Map e : json_restaurant) {
            //each restaurant ... 
            System.out.println(e);
            Iterator entries = e.entrySet().iterator();
            while (entries.hasNext()) {
                Entry thisEntry = (Entry) entries.next(); 
                System.out.println("KEY: " + thisEntry.getKey() + "    Value: " + thisEntry.getValue());
            }
            Restaurant new_res = new Restaurant((String) e.get("name"));
            new_res.setOpen((Boolean) e.get("open")); 
            new_res.setURL((String) e.get("url")); 
            new_res.setLong((double) e.get("longitude")); 
            new_res.setNeighbours((List<String>) e.get("neighborhoods"));
            new_res.setBusID((String) e.get("business_id"));
            new_res.setCategories((List<String>) e.get("categories"));
            new_res.setState((String) e.get("state"));
            new_res.setType((String) e.get("type"));
            new_res.setStars((double) e.get("stars"));
            new_res.setCity((String) e.get("city"));
            new_res.setAddr((String) e.get("full_address"));
            new_res.setReviewCnt((long) e.get("review_count"));
            new_res.setPhotoURL((String) e.get("photo_url"));
            new_res.setSchools((List<String>) e.get("schools"));
            new_res.setLat((double) e.get("latitude"));
            new_res.setPrice((long) e.get("price"));
            
            each_restaurant.putIfAbsent((String) e.get("name"), new_res);
        }
        
        System.out.println("\n\n\n");
        List<Map> json_reviews = generateDictionary(reviewsJSONfilename);
//        for (Map f : json_reviews) {
//            System.out.println(f);
//        }
        
        System.out.println("\n\n\n");
        List<Map> json_users = generateDictionary(usersJSONfilename);
//        for (Map g : json_users) {
//            System.out.println(g);
//        }
    }

    /**
     * Helper method to help constructor generate a java map from the JSON file
     * 
     * @param file
     * @return json 
     */
    private List<Map> generateDictionary(String file) {
        
        List<Map> allMaps = new ArrayList<Map>();  
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
//                        System.out.println(JSONValue.toJSONString(json)); // only one restaurant 
//                        System.out.println(json.get("type"));
                        allMaps.add(json);
                        
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
        
        return allMaps; 
    }
    
    /** 
     * Retrieves a set of restaurants given a query which consists of a combination of 
     * names, neighbourhoods, categories, rating, and price when client requests 
     * through command line
     * 
     * @param queryString
     * @return Set<Restaurant> 
     */
    public Set<Restaurant> query(String queryString) {
        // TODO: Implement this method
        // Write specs, etc.
        return null;
    }
    
       
    //TO DELETE, testing only 
//    public static void main (String [] args) {
//        RestaurantDB res = new RestaurantDB ("restaurants.json", "reviews.json", "users.json");
//    }

}
