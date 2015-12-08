package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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
    private Set<Restaurant> filtered_restaurants;
    
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
        for (Map f : json_restaurant) {
            generateRestaurants(f);
        }
        
        System.out.println("\n\n\n");
        List<Map> json_reviews = generateDictionary(reviewsJSONfilename);
        for (Map g : json_reviews) {
            generateReviews(g);
        }
        System.out.println(json_reviews.size());
        
        
        System.out.println("\n\n\n");
        List<Map> json_users = generateDictionary(usersJSONfilename);
        for (Map h: json_users) {
            generateUsers(h);
        }

    }

    
    /** 
     * Getter methods for restaurant, review, user
     * 
     */
    public Map<String, Restaurant> getAllRestaurants() {
        return new HashMap<String, Restaurant>(all_restaurants);
    }
    
    public Map<String, Review> getAllReviews() {
        return new HashMap<String, Review>(all_reviews);
    }
    
    public Map<String, User> getAllUsers() {
        return new HashMap<String, User>(all_users);
    }
    
    
    
    /** 
     * Retrieves a set of restaurants given a query which consists of a combination of 
     * names, neighbourhoods, categories, rating, and price when client requests
     * @param queryString
     * @return Set<Restaurant> 
     */
    public Set<Restaurant> query(String queryString) {
        //TODO: throw QFException
        CharStream stream = new ANTLRInputStream(queryString);
        RestaurantDBLexer lexer = new RestaurantDBLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        
        //Feed tokens into the parser
        RestaurantDBParser parser = new RestaurantDBParser(tokens);
        
        //Generate the parse tree using starter rule 
        ParseTree tree = parser.root(); 
        
        //Print tree 
        ((RuleContext)tree).inspect(parser);
        System.err.println(tree.toStringTree(parser));
        
        ParseTreeWalker walker = new ParseTreeWalker();
        RestaurantDBListener listener = new RestaurantDBListener_Advanced();  //need to extend baseListener!
        walker.walk(listener, tree);
        
//        return listener.getFormula();
        return null; 
    }
    
    /**
     * Private class to walk through each parser of the ANTLR tree
     * 
     * @author Shirley
     *
     */
    private class RestaurantDBListener_Advanced extends RestaurantDBBaseListener {
        
        private Stack<String> ORANDExpression = new Stack<String>(); 
        //**!!
        private Stack<ArrayList<Restaurant>> fullStack = new Stack<ArrayList<Restaurant>>(); 
        List<Restaurant> ANDR = new ArrayList<Restaurant>(); 
        List<Restaurant> ANDL = new ArrayList<Restaurant>(); 
        List<Restaurant> ANDF = new ArrayList<Restaurant>(); 

        List<Restaurant> ORR = new ArrayList<Restaurant>(); 
        List<Restaurant> ORL = new ArrayList<Restaurant>(); 
        List<Restaurant> ORF = new ArrayList<Restaurant>(); 

        //**!!
        private List<Restaurant> categoryls;
        private ArrayList<Restaurant> inls; 
        private List<Restaurant> pricels; 
        private List<Restaurant> ratingls;
        private List<Restaurant> namels; 
        
        private List<ArrayList<Restaurant>> ORList = new ArrayList<ArrayList<Restaurant>>();
        private List<ArrayList<Restaurant>> ANDList = new ArrayList<ArrayList<Restaurant>>();

        
        @Override
        public void enterAndExp (RestaurantDBParser.AndExpContext ctx) {
            System.err.println("\n==============> entering && expression");
            System.err.println("Child count: " + ctx.getChildCount());
        }
        
        @Override
        public void exitAndExp (RestaurantDBParser.AndExpContext ctx) {
            System.err.println("<============== exiting && expression\n");
            int addCount = 0; 
            
            //Check if this AndExp contains && operator
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("&&")) {
                    System.err.println("AND operator exists");
                    addCount++; 
                }
            }
            
            //Confirms this expression contains && operator
            for (int i = 0; i<addCount; i++) {
                System.err.println("I am intersecting");
                ANDL = fullStack.pop();
                ANDR = fullStack.pop();
                // ANDF = ANDL & ANDR; 
                ANDF = ANDL; 
                ANDF.retainAll(ANDR);
                fullStack.push((ArrayList<Restaurant>) ANDF);
            }
            addCount = 0; 
            
            System.err.println("AND LIST finalized to be: \n" + fullStack.peek());
            for (Restaurant r : fullStack.peek()) {
                System.err.println(r.name);
            }
        }
        
        @Override 
        public void enterIn (RestaurantDBParser.InContext ctx) {
            System.err.println("\nentering IN expression");
            String subStringCtx; 
            
            inls = new ArrayList<Restaurant>(); 
            List<String> testls = new ArrayList<String>(); 
            
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
              //per restaurant 
              for (String s : entry.getValue().getNeighbours()) {
                  subStringCtx = ctx.getChild(2).toString().substring(1, ctx.getChild(2).toString().length()-1);
                  if (s.equals(subStringCtx)) {
                      inls.add(entry.getValue());
                      testls.add(entry.getValue().name);
                      break;
                  }
              }
            }
            
            //finalized string exiting In; 
            fullStack.push(inls);
            System.err.println(fullStack.peek());
            System.err.println(testls);
        }
        
        @Override 
        public void exitIn (RestaurantDBParser.InContext ctx) {
            System.err.println("exiting IN expression\n");
        }
        
        @Override 
        public void enterPrice (RestaurantDBParser.PriceContext ctx) {
            System.err.println("\nentering PRICE expression");
            
            pricels = new ArrayList<Restaurant>(); 
            List<String> testls = new ArrayList<String>();
            
            
            long longMin = Long.parseLong(ctx.getChild(2).toString()); 
            long longMax = Long.parseLong(ctx.getChild(4).toString()); 
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
              //per restaurant 
              
              if (longMin <= entry.getValue().getPrice() &&  entry.getValue().getPrice() <= longMax) {
                  pricels.add(entry.getValue());
                  testls.add(entry.getValue().name);
              }
            }
          
            fullStack.push((ArrayList<Restaurant>) pricels);
            System.err.println(fullStack.peek());
            System.err.println(testls);
        }
        
        @Override 
        public void exitPrice (RestaurantDBParser.PriceContext ctx) {
            System.err.println("exiting PRICE expression\n");
        }
        
        @Override 
        public void enterRoot (RestaurantDBParser.RootContext ctx) {
            System.err.println("\nentering ROOT expression");
        }
        
        @Override 
        public void exitRoot (RestaurantDBParser.RootContext ctx) {
            System.err.println("exiting ROOT expression\n");
            //TODO: take the last element of the stack to return! final product
        }
        
        @Override 
        public void enterQuery (RestaurantDBParser.QueryContext ctx) {
            System.err.println("\n----------> entering QUERY expression");
            System.err.println("Child count : " + ctx.getChildCount());
            
        }
        
        @Override 
        public void exitQuery (RestaurantDBParser.QueryContext ctx) {
            System.err.println("<---------- exiting QUERY expression\n");
            System.err.println(ctx.children);

            int ORCount = 0; 
            
            //Check if this AndExp contains && operator
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("||")) {
                    System.err.println("OR operator exists");
                    ORCount++; 
                }
            }
            
            //Confirms this expression contains && operator
            for (int i = 0; i<ORCount; i++) {
                System.err.println("I am unioning");
                ORL = fullStack.pop();
                ORR = fullStack.pop();
                // ORF = ORL + ORR; 
                ORF = ORL; 
                ORF.addAll(ORR);
                fullStack.push((ArrayList<Restaurant>) ORF);
            }
            
            ORCount = 0; 
            
            //Test
            System.err.println("OR LIST finalized to be: \n" + fullStack.peek());
            for (Restaurant r : fullStack.peek()) {
                System.err.println(r.name);
            }
        }
        
        @Override 
        public void enterRating (RestaurantDBParser.RatingContext ctx) {
            System.err.println("\nentering RATING expression");
            
            List <String> testls = new ArrayList<String>();
            ratingls = new ArrayList<Restaurant>(); 

            double doubleMin = Double.parseDouble(ctx.getChild(2).toString()); 
            double doubleMax = Double.parseDouble(ctx.getChild(4).toString()); 
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
              System.err.println(entry.getValue().getStars());
              System.err.println("Beginning: " + doubleMin + "  -- Ending: " + doubleMax);
              
              if (doubleMin <= entry.getValue().getStars() &&  entry.getValue().getStars() <= doubleMax) {
                  ratingls.add(entry.getValue());
                  testls.add(entry.getValue().name);
                  System.err.println("FOUND IT~~~~~");
              }
            }
          
            fullStack.push((ArrayList<Restaurant>) ratingls);
            System.err.println(fullStack.peek());
            System.err.println(testls); 
        }
        
        @Override 
        public void exitRating (RestaurantDBParser.RatingContext ctx) {
            System.err.println("exiting RATING expression\n");
        }
        
        @Override
        public void enterName (RestaurantDBParser.NameContext ctx) {
            System.err.println("\nentering NAME expression");
            namels = new ArrayList<Restaurant>(); 
            String subStringCtx = ctx.getChild(2).toString().substring(1, ctx.getChild(2).toString().length()-1); 
            
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
              System.err.println(entry.getValue().name);
              System.err.println(subStringCtx); 

                  if (entry.getValue().name.equals(subStringCtx)) {
                      namels.add(entry.getValue());
                      System.err.println("FOUND IT~~~~~");
                  }
          }
           
              fullStack.push((ArrayList<Restaurant>) namels);
              System.err.println(fullStack.peek());
        }
        
        @Override
        public void exitName (RestaurantDBParser.NameContext ctx) {
            System.err.println("exiting NAME expression\n");
        }
        
        @Override
        public void enterAtom (RestaurantDBParser.AtomContext ctx) {
            System.err.println("entering ATOM expression");
        }
        
        @Override 
        public void exitAtom (RestaurantDBParser.AtomContext ctx) {
            System.err.println("exiting ATOM expression");
        }
        
        @Override 
        public void enterCategory (RestaurantDBParser.CategoryContext ctx) {
            String subStringCtx; 
            System.err.println("\nentering CATEGORY expression");
            System.err.println(ctx.depth()); //the whole token 
            System.err.println(ctx.getChild(2).toString().getClass()); //cuisine name
            categoryls = new ArrayList<Restaurant>(); 
            
            //TESTING: restaurant list in string, instead of restaurant objects
            List<String> stringls = new ArrayList<String>();
            
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
                for (String s : entry.getValue().getCategories()) {
                    subStringCtx = ctx.getChild(2).toString().substring(1, ctx.getChild(2).toString().length()-1);
                    if (s.equals(subStringCtx)) {
                        categoryls.add(entry.getValue());
                        stringls.add(entry.getValue().name);
                        break;
                    }
                }
            }
            
            fullStack.push((ArrayList<Restaurant>) categoryls);
            System.err.println(fullStack.peek());
            System.err.println(stringls);
        }
        
        @Override
        public void exitCategory (RestaurantDBParser.CategoryContext ctx) {
            System.err.println ("exiting CATEGORY expression\n");
            //Anything needed to be done? 
        }
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
            generateRestaurants(json); 
            
        } catch (ParseException pe) {
            System.out.println(pe);
        }
          
    }
    
    /**
     * Precondition: input must be a single element JSON string
     * 
     * @param reviewJSON
     */
    public void addReview (String reviewJSON) {
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
            json = (Map)parser.parse(reviewJSON, containerFactory);
            Iterator iter = (Iterator) json.entrySet().iterator(); 
            while(iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next(); 
            }
            //add one more non-JSON file element 
            json.put("JSONStr",reviewJSON);
            generateReviews(json); 
            
        } catch (ParseException pe) {
            System.out.println(pe);
        }
    }
       
    /**
     * Precondition: input must be a single element JSON string
     * 
     * @param userJSON
     */
    public void addUser (String userJSON) {
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
            json = (Map)parser.parse(userJSON, containerFactory);
            Iterator iter = (Iterator) json.entrySet().iterator(); 
            while(iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next(); 
            }
            //add one more non-JSON file element 
            json.put("JSONStr",userJSON);
            generateUsers(json); 
            
        } catch (ParseException pe) {
            System.out.println(pe);
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
     * Helper method to convert JSON maps to Restaurant Object maps. 
     * 
     * @modifies: private all_restaurant object
     * 
     */
    
    private void generateRestaurants (Map JSONRestaurantMap) {
            
            //Testing only...
//            Iterator entries = e.entrySet().iterator();
//            while (entries.hasNext()) {
//                Entry thisEntry = (Entry) entries.next(); 
//                System.out.println("KEY: " + thisEntry.getKey() + "    Value: " + thisEntry.getValue());
//            }
            Restaurant new_res = new Restaurant((String) JSONRestaurantMap.get("name"));
            new_res.setOpen((Boolean) JSONRestaurantMap.get("open")); 
            new_res.setURL((String) JSONRestaurantMap.get("url")); 
            new_res.setLong((double) JSONRestaurantMap.get("longitude")); 
            new_res.setNeighbours((List<String>) JSONRestaurantMap.get("neighborhoods"));
            new_res.setBusID((String) JSONRestaurantMap.get("business_id"));
            new_res.setCategories((List<String>) JSONRestaurantMap.get("categories"));
            new_res.setState((String) JSONRestaurantMap.get("state"));
            new_res.setStars((double) JSONRestaurantMap.get("stars"));
            new_res.setCity((String) JSONRestaurantMap.get("city"));
            new_res.setAddr((String) JSONRestaurantMap.get("full_address"));
            new_res.setReviewCnt((long) JSONRestaurantMap.get("review_count"));
            new_res.setPhotoURL((String) JSONRestaurantMap.get("photo_url"));
            new_res.setSchools((List<String>) JSONRestaurantMap.get("schools"));
            new_res.setLat((double) JSONRestaurantMap.get("latitude"));
            new_res.setPrice((long) JSONRestaurantMap.get("price"));
            new_res.setJSONStr((String) JSONRestaurantMap.get("JSONStr"));
            
            all_restaurants.putIfAbsent((String) JSONRestaurantMap.get("name"), new_res);
    }
    
    /**
     * Helper method to convert JSON maps to Review Object maps. 
     * 
     * @modifies: private all_review object
     * 
     */
    
    private void generateReviews (Map f) {
            
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
    
    /**
     * Helper method to convert JSON maps to User object maps.
     * 
     * @modifies: private all_users object
     * 
     */
    private void generateUsers (Map g) {
            
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
    
    
    
    //TO DELETE, testing only 
    public static void main (String [] args) {
        RestaurantDB res = new RestaurantDB ("restaurants.json", "reviews.json", "users.json");
        String queryIn = "in(\"Telegraph Ave\")"; 
        String queryRating = "rating(2.1..2.9)";
        String queryPrice = "price(1..2)";
        String queryORCategory = "category(\"Chinese\") || category(\"Italian\")";
        String queryAND = "in(\"Telegraph Ave\") && price(1..2)";
        String queryString1 = "in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)";
        String queryStringName = "name(\"Alborz\")"; 
        res.query(queryString1);
    }

}
