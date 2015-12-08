package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 * 
	 * @param db
	 * @return
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		return Collections.unmodifiableList(kmc( k, db.getAllRestaurants() ));
	}

	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
	    StringBuilder ClusterToJSONStr = new StringBuilder(""); 
	    Map ClusterJSON = new LinkedHashMap(); 
	    
	    //{"x": 37.8702006, "y": -122.2659014, "name": "Cinnaholic", "cluster": 3, "weight": 5.0} <-- weight = stars or just 4
	    
	    for (Set<Restaurant> rset : clusters) {
	        for (Restaurant r : rset) {
	            ClusterJSON.put("x", r.getLat());
	            ClusterJSON.put("y", r.getLong());
	            ClusterJSON.put("name", r.getName());
	            ClusterJSON.put("cluster", rset.size());
	            ClusterJSON.put("weight", r.getStars());
	        }
	        
	        ClusterToJSONStr.append(JSONObject.toJSONString(ClusterJSON) + ", ");
	    }
	    
	    ClusterToJSONStr.delete(ClusterToJSONStr.length()-2, ClusterToJSONStr.length()-1);
		return ClusterToJSONStr.toString();
	}

	public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
		// TODO: Implement this method
		return null;
	}

	public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
		// TODO: Implement this method
		return null;
	}
	
	private static List<Set<Restaurant>> kmc( int k, Map<String, Restaurant> allRestaurants ){
	    
	    List<Set<Restaurant>> kmsLs = new ArrayList<Set<Restaurant>>();
	    List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	    restaurantList.addAll(allRestaurants.values());
	    
	    //STEP 1: 
	    //Get k random restaurant locations & make as empty clusters
	    Map<Cross, Set<Restaurant>> clusters = new HashMap<Cross, Set<Restaurant>>();
	    Random randomGen = new Random();
	    while( clusters.size() < k ){
	        int randomIndex = randomGen.nextInt(restaurantList.size());
	        clusters.putIfAbsent( new Cross ( restaurantList.get(randomIndex).latitude, restaurantList.get(randomIndex).longitude ), new HashSet<Restaurant>() );
	        System.out.println(randomIndex);
	    }
	    
        //Count how many crosses are already centroids for their respective restaurant sets
        int matchCount  = 0;
	    
	    while (true) {
	        
	        //If all crosses are centroids for their respective restaurant sets, break!
	        System.out.println ("Start of new iteration - Match Cnt: " + matchCount);
	        if( matchCount == clusters.size()){
	            break;
	        }
	        
	        //Reset match count for new clusters
	        matchCount  = 0;

	        //STEP 2: 
	    //Assign restaurants to cluster
	    for( Restaurant r : restaurantList ){
	        System.out.println("Restaurant LatLong: " + r.getLat() + "," + r.getLong());
	        
	        Map<Cross, Double> distanceFromCrosses = new HashMap<Cross, Double>();
	        for( Cross c : clusters.keySet() ){
	            System.out.println("C LatLong: " + c.getLatitude() + "," + c.getLongitude());
	            double distance = Math.sqrt( Math.pow(r.latitude-c.getLatitude(), 2) + Math.pow(r.longitude-c.getLongitude(), 2)); //should be positive
	            distanceFromCrosses.put(c, distance);
	        }
	        //Find cross with min distance
	        Double min = Double.MAX_VALUE;
	        for( Double d : distanceFromCrosses.values() ){
	            if( d < min ){ //Assume that min MUST be assigned to one of the distances
	                min = d;
	            }
	        }
	        System.out.println ("Minimum distance: " + min);
	        
	        for( Cross c : distanceFromCrosses.keySet() ){
	            if( distanceFromCrosses.get(c).equals(min) ){ //at least one cross is mapped to value of "min"
	                clusters.get(c).add(r);
	            }
	        }
	    }
	     
	        //STEP 3: 
	    
	        //Calculate centroid for each cluster i.e. average location for set of restaurants
	        for( Cross c : clusters.keySet() ){
	            
	            System.out.println("@Cross: " + c.getLatitude() + "," + c.getLongitude());
	            
	            double avgLong = 0 ;
	            double avgLat = 0;
	            //Get the average location for each cluster (set of restaurants) 
	            System.out.println("Restaurants: " );
	            for( Restaurant r : clusters.get(c) ) {
	                System.out.println(r.getName());
	                avgLat += r.getLat(); 
	                avgLong += r.getLong(); 
	            }
	            
	               System.out.println("*****cluster size: "  + clusters.get(c).size());
	            avgLat /= clusters.get(c).size(); 
	            avgLong /= clusters.get(c).size(); 
	            
	            // replace cross c's (x,y) with average (x,y) = centroid
	            if( (c.getLatitude() == avgLat) && (c.getLongitude() == avgLong) ){
	                matchCount++;
	            }
	            c.setLatitude(avgLat); 
	            c.setLongitude(avgLong);
	        }
	    }
	    
	    kmsLs.addAll(clusters.values());
	    
	    return kmsLs;
	}
	
	public static void main( String[] args) {
	    
	    Map<String, Restaurant> testMap = new HashMap<String, Restaurant>();
	    List<Set<Restaurant>> returnedLs; 
	    
	    Restaurant r1 = new Restaurant("r1");
	    r1.setLat(3.0);
	    r1.setLong(3.0);
	    testMap.put(r1.getName(), r1);
	    
	    Restaurant r2 = new Restaurant("r2");
        r2.setLat(-3.0);
        r2.setLong(3.0);
        testMap.put(r2.getName(), r2);

        
        Restaurant r3 = new Restaurant("r3");
        r3.setLat(3.0);
        r3.setLong(-3.0);
        testMap.put(r3.getName(), r3);

        
        Restaurant r4 = new Restaurant("r4");
        r4.setLat(-3.0);
        r4.setLong(-3.0);
        testMap.put(r4.getName(), r4);

        
        Restaurant r5 = new Restaurant("r5");
        r5.setLat(-4.0);
        r5.setLong(3.0);
        testMap.put(r5.getName(), r5);

        
        Restaurant r6 = new Restaurant("r6");
        r6.setLat(0.0);
        r6.setLong(-2.0);
        testMap.put(r6.getName(), r6);

//        returnedLs = kmc( 2, testMap );
        
        RestaurantDB rdb = new RestaurantDB("restaurants.json", "reviews.json", "users.json");
        returnedLs = kMeansClustering(20, rdb);
        System.out.println(returnedLs);
        System.out.println("FINAL JSON str:  " + convertClustersToJSON(returnedLs));
	}
}