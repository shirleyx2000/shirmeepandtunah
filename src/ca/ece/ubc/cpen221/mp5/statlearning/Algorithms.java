package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
		// TODO: Implement this method
		return null;
	}

	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		// TODO: Implement this method
		return null;
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
	    
	    List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	    restaurantList.addAll(allRestaurants.values());
	    
	    //Get k random restaurant locations & make as empty clusters
	    Map<Cross, Set<Restaurant>> clusters = new HashMap<Cross, Set<Restaurant>>();
	    Random randomGen = new Random();
	    while( clusters.size() < k ){
	        int randomIndex = randomGen.nextInt(restaurantList.size());
	        clusters.putIfAbsent( new Cross ( restaurantList.get(randomIndex).latitude, restaurantList.get(randomIndex).longitude ), new HashSet<Restaurant>() );
	    }
	    
	    //Assign restaurants to cluster
	    for( Restaurant r : restaurantList ){
	        Map<Cross, Double> distanceFromCrosses = new HashMap<Cross, Double>();
	        for( Cross c : clusters.keySet() ){
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
	        for( Cross c : distanceFromCrosses.keySet() ){
	            if( distanceFromCrosses.get(c).equals(min) ){ //at least one cross is mapped to value of "min"
	                clusters.get(c).add(r);
	            }
	        }
	    }
	    
	    //Calculate centroid for each cluster i.e. set of restaurants' locations
	    
	    //Assign crosses with coordinates of centroid
	    
	    //Repeat until crosses location = centroid location
	    
	    
	    
	    
	    return null;
	}
	
	private void testKmc() {
	    
	}
}