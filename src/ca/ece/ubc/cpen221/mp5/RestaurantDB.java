package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

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
    
    private class RestaurantDBListener_Advanced extends RestaurantDBBaseListener {
        
        private Stack<String> ORANDExpression = new Stack<String>(); 
        private List<Restaurant> categoryls;
        private List<Restaurant> inls; 
        private List<Restaurant> pricels; 
        private List<Restaurant> ratingls;
        private List<Restaurant> namels; 
        private List<ArrayList<Restaurant>> ORList; 
        private List<ArrayList<Restaurant>> ANDList = new ArrayList<ArrayList<Restaurant>>();; 

        
        @Override
        public void enterAndExp (RestaurantDBParser.AndExpContext ctx) {
            System.out.println("==============> entering && expression");
            System.err.println("Child count: " + ctx.getChildCount());
            
            //TODO: check AND operator 
            //CHECK if child contains && token through TOKEN 
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("&&")) {
                    System.err.println("AND operator exists");
//                    ANDList = new ArrayList<ArrayList<Restaurant>>();
                    ORANDExpression.push("AND");
                    
                    for (int i = 0; i < (ctx.getChildCount()/2+1); i++) {
                        //one list per element OR'ed 
                        ANDList.add(new ArrayList<Restaurant>());
                    }
                }
            }
        }
        
        @Override
        public void exitAndExp (RestaurantDBParser.AndExpContext ctx) {
            System.out.println("<============== exiting && expression");
            
          //CHECK if child contains && token through TOKEN 
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("&&")) {
                    System.err.println("AND operator exists");
                    
                    List<Restaurant> resSet = new ArrayList<Restaurant>(ANDList.get(0)); 
                    //iterate through each list and combine into SETs
                    for (ArrayList<Restaurant> ls : ANDList) {
                        System.err.println(ls);
                        //INTERSECTION OF LISTS
                        resSet.retainAll(ls);
                    }
                    
                    //Check combined list
                    System.err.println("FINAL PRODUCT: \n" + resSet);            
                    
                    //release the OPERATOR
                    System.err.println(ORANDExpression.peek());
                    ORANDExpression.pop(); 
                    //should reset ANDList, once returned 
                    int size = ANDList.size(); 
                    for (int i = 0; i < (ctx.getChildCount()/2+1); i++) {
                        //one list per element OR'ed 
                        ANDList.remove(size-1-i);
                    }
                    //if higher levels of add needs to AND this product
//                    ANDList.add((ArrayList<Restaurant>) resSet);
                }
            }
            System.err.println("AND LIST finalized to be: \n" + ANDList);
        }
        
        @Override 
        public void enterIn (RestaurantDBParser.InContext ctx) {
            System.out.println("entering IN expression");
            String subStringCtx; 
            
            List<String> testls = new ArrayList<String>(); 
            
            //find a list to populate: 
            if (ORANDExpression.peek().equals("OR")) {
                for (ArrayList<Restaurant> ls : ORList) {
                    if (ls.isEmpty()) {
                        inls = ls; 
                        break;
                    }
                }
            } else if (ORANDExpression.peek().equals("AND")){ 
                for (ArrayList<Restaurant> ls : ANDList) {
                    if (ls.isEmpty()) {
                        inls = ls; 
                        break;
                    }
                }
            } else {
                System.err.println("WHAT HAPPENED?");
            }
                        
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
          System.err.println(inls);
          System.err.println(testls);
        }
        
        @Override 
        public void exitIn (RestaurantDBParser.InContext ctx) {
            System.out.println("exiting IN expression");
        }
        
        @Override 
        public void enterPrice (RestaurantDBParser.PriceContext ctx) {
            System.out.println("entering PRICE expression");
            
            List<String> testls = new ArrayList<String>();
            
            //find a list to populate: 
            if (ORANDExpression.peek().equals("OR")) {
                System.err.println("OR LIST");
                for (ArrayList<Restaurant> ls : ORList) {
                    if (ls.isEmpty()) {
                        pricels = ls; 
                        break;
                    }
                }
            } else if (ORANDExpression.peek().equals("AND")){ 
                System.err.println("AND LIST");
                for (ArrayList<Restaurant> ls : ANDList) {
                    if (ls.isEmpty()) {
                        pricels = ls; 
                        break;
                    }
                }
            } else {
                System.err.println("*****WHAT HAPPENED?");
            }
            
            long longMin = Long.parseLong(ctx.getChild(2).toString()); 
            long longMax = Long.parseLong(ctx.getChild(4).toString()); 
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
              //per restaurant 
              
              if (longMin <= entry.getValue().getPrice() &&  entry.getValue().getPrice() <= longMax) {
                  pricels.add(entry.getValue());
                  testls.add(entry.getValue().name);
              }
          }
          
          System.err.println(pricels);
          System.err.println(testls);
        }
        
        @Override 
        public void exitPrice (RestaurantDBParser.PriceContext ctx) {
            System.out.println("exiting PRICE expression");
        }
        
        @Override 
        public void enterRoot (RestaurantDBParser.RootContext ctx) {
            System.out.println("entering ROOT expression");
        }
        
        @Override 
        public void exitRoot (RestaurantDBParser.RootContext ctx) {
            System.out.println("exiting ROOT expression");
        }
        
        @Override 
        public void enterQuery (RestaurantDBParser.QueryContext ctx) {
            System.out.println("----------> entering QUERY expression");
            System.err.println("Child count : " + ctx.getChildCount());
            
            //CHECK if child contains || token through TOKEN 
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("||")) {
                    System.err.println("OR operator exists");
                    ORList = new ArrayList<ArrayList<Restaurant>>();
                    ORANDExpression.push("OR");
                    
                    for (int i = 0; i < (ctx.getChildCount()/2+1); i++) {
                        //one list per element OR'ed 
                         ORList.add(new ArrayList<Restaurant>());
                    }
                }
            }
            
        }
        
        @Override 
        public void exitQuery (RestaurantDBParser.QueryContext ctx) {
            System.out.println("<---------- exiting QUERY expression");
            
            System.err.println(ctx.children);
          //CHECK if child contains || token through TOKEN 
            for (ParseTree pt : ctx.children) {
                if (pt.toString().equals("||")) {
                    System.err.println("OR operator exists");
                    
                    //should at least exist one list after returning from an OREXP
                    Set<Restaurant> resSet = new LinkedHashSet<Restaurant>();
                    
                    //find UNION by combining lists into ONE set
                    for (ArrayList<Restaurant> ls : ORList) {
                        System.err.println(ls);
                        resSet.addAll(ls);
                    }
                    
                    //FINAL PRODUCT: union of combined list --> set
                    System.err.println(resSet);            
                    
                    System.err.println(ORANDExpression.peek());
                    ORANDExpression.pop(); 
                    //should process ORList, once returned 
                    ORList = null; 
                }
            }
            
           
        }
        
        @Override 
        public void enterRating (RestaurantDBParser.RatingContext ctx) {
            System.out.println("entering RATING expression");
            
            List <String> testls = new ArrayList<String>();
            
            if (ORANDExpression.peek().equals("OR")) {
                for (ArrayList<Restaurant> ls : ORList) {
                    if (ls.isEmpty()) {
                        ratingls = ls; 
                        break;
                    }
                }
            } else if (ORANDExpression.peek().equals("AND")){ 
                for (ArrayList<Restaurant> ls : ANDList) {
                    if (ls.isEmpty()) {
                        ratingls = ls; 
                        break;
                    }
                }
            } else {
                System.err.println("WHAT HAPPENED?");
            }
            
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
              System.err.println("NEXT RESTAURANT...\n");
          }
          
          System.err.println(ratingls);
          System.err.println(testls); 
        }
        
        @Override 
        public void exitRating (RestaurantDBParser.RatingContext ctx) {
            System.out.println("exiting RATING expression");
        }
        
        @Override
        public void enterName (RestaurantDBParser.NameContext ctx) {
            System.out.println("entering NAME expression");
            
            String subStringCtx = ctx.getChild(2).toString().substring(1, ctx.getChild(2).toString().length()-1); 
            
            if (ORANDExpression.peek().equals("OR")) {
                for (ArrayList<Restaurant> ls : ORList) {
                    if (ls.isEmpty()) {
                        namels = ls; 
                        break;
                    }
                }
            } else if (ORANDExpression.peek().equals("AND")){ 
                for (ArrayList<Restaurant> ls : ANDList) {
                    if (ls.isEmpty()) {
                        namels = ls; 
                        break;
                    }
                }
            } else {
                System.err.println("WHAT HAPPENED?");
            }
            
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
//              System.err.println(entry.getKey() + "/" + entry.getValue().getCategories());
              System.err.println(entry.getValue().name);
              System.err.println(subStringCtx); 

                  if (entry.getValue().name.equals(subStringCtx)) {
                      namels.add(entry.getValue());
                      System.err.println("FOUND IT~~~~~");
                  }
              System.err.println("NEXT RESTAURANT...\n");
          }
          
          System.err.println(namels);
        }
        
        @Override
        public void exitName (RestaurantDBParser.NameContext ctx) {
            System.out.println("exiting NAME expression");
        }
        
        @Override
        public void enterAtom (RestaurantDBParser.AtomContext ctx) {
            System.out.println("entering ATOM expression");
        }
        
        @Override 
        public void exitAtom (RestaurantDBParser.AtomContext ctx) {
            System.out.println("exiting ATOM expression");
        }
        
        @Override 
        public void enterCategory (RestaurantDBParser.CategoryContext ctx) {
            String subStringCtx; 
            System.out.println("\n>>>> entering CATEGORY expression");
            System.err.println(ctx.depth()); //the whole token 
            System.err.println(ctx.getChild(2).toString().getClass()); //cuisine name
            
            //find a list to populate: 
            if (ORANDExpression.peek().equals("OR")) {
                for (ArrayList<Restaurant> ls : ORList) {
                    if (ls.isEmpty()) {
                        categoryls = ls; 
                        break;
                    }
                }
            } else if (ORANDExpression.peek().equals("AND")){ 
                for (ArrayList<Restaurant> ls : ANDList) {
                    if (ls.isEmpty()) {
                        categoryls = ls; 
                        break;
                    }
                }
            } else {
                System.err.println("WHAT HAPPENED?");
            }
            
            //TESTING: restaurant list in string, instead of restaurant objects
            List<String> stringls = new ArrayList<String>();
            
            for (Map.Entry<String, Restaurant> entry : all_restaurants.entrySet()) {
                //per restaurant 
                for (String s : entry.getValue().getCategories()) {
                    subStringCtx = ctx.getChild(2).toString().substring(1, ctx.getChild(2).toString().length()-1);
                    if (s.equals(subStringCtx)) {
                        categoryls.add(entry.getValue());
                        stringls.add(entry.getValue().name);
//                        System.err.println("FOUND IT~~~~~");
                        break;
                    }
                }
//                System.err.println("NEXT RESTAURANT...\n");
            }
            
            System.err.println(categoryls);
            System.err.println(stringls);
        }
        
        @Override
        public void exitCategory (RestaurantDBParser.CategoryContext ctx) {
            System.out.println ("<<<< exiting CATEGORY expression\n");
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
