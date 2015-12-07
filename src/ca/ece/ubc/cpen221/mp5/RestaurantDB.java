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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


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

    private Map<String, Restaurant> all_restaurants;
    private Map<String, Review> all_reviews; 
    private Map<String, User> all_users; 
    
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

        //this map contains "name" -> restaurant object
        all_restaurants = new HashMap<String, Restaurant>(); 
        all_reviews = new HashMap<String, Review>(); 
        all_users = new HashMap<String, User>(); 
        
        List<Map> json_restaurant = generateDictionary(restaurantJSONfilename);
         
        for (Map e : json_restaurant) {
            //Testing only...
//            Iterator entries = e.entrySet().iterator();
//            while (entries.hasNext()) {
//                Entry thisEntry = (Entry) entries.next(); 
//                System.out.println("KEY: " + thisEntry.getKey() + "    Value: " + thisEntry.getValue());
//            }
            Restaurant new_res = new Restaurant((String) e.get("name"));
            new_res.setOpen((Boolean) e.get("open")); 
            new_res.setURL((String) e.get("url")); 
            new_res.setLong((double) e.get("longitude")); 
            new_res.setNeighbours((List<String>) e.get("neighborhoods"));
            new_res.setBusID((String) e.get("business_id"));
            new_res.setCategories((List<String>) e.get("categories"));
            new_res.setState((String) e.get("state"));
            new_res.setStars((double) e.get("stars"));
            new_res.setCity((String) e.get("city"));
            new_res.setAddr((String) e.get("full_address"));
            new_res.setReviewCnt((long) e.get("review_count"));
            new_res.setPhotoURL((String) e.get("photo_url"));
            new_res.setSchools((List<String>) e.get("schools"));
            new_res.setLat((double) e.get("latitude"));
            new_res.setPrice((long) e.get("price"));
            new_res.setJSONStr((String) e.get("JSONStr"));
            
            all_restaurants.putIfAbsent((String) e.get("name"), new_res);
        }
        
        System.out.println("\n\n\n");
        List<Map> json_reviews = generateDictionary(reviewsJSONfilename);
        System.out.println(json_reviews.size());
        for (Map f : json_reviews) {
            
            //Testing only 
//            Iterator entries = f.entrySet().iterator();
//            while (entries.hasNext()) {
//                Entry thisEntry = (Entry) entries.next(); 
//                System.out.println("KEY: " + thisEntry.getKey() + "    Value: " + thisEntry.getValue());
////                System.out.println(thisEntry.getValue().getClass());
//            }
            
            Review new_rev = new Review(); 
            new_rev.setBusinessId((String) f.get("business_id"));
            new_rev.setVotes((Map<String, Integer>) f.get("votes")); 
            new_rev.setReviewId((String) f.get("review_id"));
            new_rev.setText((String) f.get("text"));
            new_rev.setStars((long) f.get("stars"));
            new_rev.setUserId((String) f.get("user_id"));
            new_rev.setDate((String) f.get("date"));
            new_rev.setJSONStr((String) f.get("JSONStr"));
            
            all_reviews.putIfAbsent((String) f.get("review_id"), new_rev);
        }
        
        System.out.println("\n\n\n");
        List<Map> json_users = generateDictionary(usersJSONfilename);
        for (Map g : json_users) {
            
            //TESTING only....
//            Iterator entries = g.entrySet().iterator();
//            while (entries.hasNext()) {
//                Entry thisEntry = (Entry) entries.next(); 
//                System.out.println("KEY: " + thisEntry.getKey() + "    Value: " + thisEntry.getValue());
//                System.out.println(thisEntry.getValue().getClass());
//            }
            
            User new_user = new User((String) g.get("name"));
            new_user.setUserId((String) g.get("user_id"));
            new_user.setAverageStars((double) g.get("average_stars"));
            new_user.setJSONStr((String) g.get("JSONStr"));
            new_user.setUrl((String) g.get("url"));
            new_user.setVotes((Map<String,Integer>) g.get("votes"));
            new_user.setReviewCount((long) g.get("review_count"));
            
            all_users.putIfAbsent((String) g.get("name"), new_user);
        }
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
                        //add one more non-JSON file element 
                        json.put("JSONStr",str);
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
     * @param queryString
     * @return Set<Restaurant> 
     */
    public Set<Restaurant> query(String queryString) {
        // TODO: Implement this method
        
        //in("Telegraph Ave") && (category("Chinese") || category("Italian")) && price(1..2)
        CharStream stream = new ANTLRInputStream(queryString);
        RestaurantDBLexer lexer = new RestaurantDBLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        
        RestaurantDBParser parser = new RestaurantDBParser(tokens);
        ParseTree tree = parser.root(); 
        ((RuleContext)tree).inspect(parser);
        
        ParseTreeWalker walker = new ParseTreeWalker();
        RestaurantDBListener listener = new RestaurantDBBaseListener(); 
        
        
        return null;
    }
    
    
    /**
     * Precondition: input must be a single element JSON string
     * 
     * @param restaurantJSON
     */
    public void addRestaurant (String restaurantJSON) {
        Map json = new HashMap(); 

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
            json = (Map)parser.parse(restaurantJSON, containerFactory);
            Iterator iter = (Iterator) json.entrySet().iterator(); 
            while(iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next(); 
            }
            //add one more non-JSON file element 
            json.put("JSONStr",restaurantJSON);
//            System.out.println(JSONValue.toJSONString(json)); // only one restaurant 
//            System.out.println(json.get("type"));
            
            Restaurant new_res = new Restaurant((String) json.get("name"));
            new_res.setOpen((Boolean) json.get("open")); 
            new_res.setURL((String) json.get("url")); 
            new_res.setLong((double) json.get("longitude")); 
            new_res.setNeighbours((List<String>) json.get("neighborhoods"));
            new_res.setBusID((String) json.get("business_id"));
            new_res.setCategories((List<String>) json.get("categories"));
            new_res.setState((String) json.get("state"));
            new_res.setStars((double) json.get("stars"));
            new_res.setCity((String) json.get("city"));
            new_res.setAddr((String) json.get("full_address"));
            new_res.setReviewCnt((long) json.get("review_count"));
            new_res.setPhotoURL((String) json.get("photo_url"));
            new_res.setSchools((List<String>) json.get("schools"));
            new_res.setLat((double) json.get("latitude"));
            new_res.setPrice((long) json.get("price"));
            new_res.setJSONStr((String) json.get("JSONStr"));
            
            all_restaurants.putIfAbsent((String) json.get("name"), new_res);
            
        } catch (ParseException pe) {
            System.out.println(pe);
        }
          
    }
    
//    public void addReview (String reviewJSON);
//       
//    public void addUser (String userJSON);
    
    //TO DELETE, testing only 
    public static void main (String [] args) {
        RestaurantDB res = new RestaurantDB ("restaurants.json", "reviews.json", "users.json");
        String queryString0 = "in(\"Telegraph Ave\")"; 
        String queryString3 = "price(1..2)";
        String queryString4 = "category(\"Chinese\") || category(\"Italian\")";
        String queryString1 = "in(\"Telegraph Ave\") && price(1..2)";
        String queryString2 = "in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)";
        res.query(queryString0);
    }

}
