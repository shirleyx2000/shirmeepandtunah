package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class RestaurantCategory implements MP5Function {

     
    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        Restaurant r = db.getAllRestaurants().get(yelpRestaurant.getName()); 
        
        //the more variety, the better the rating
        return r.getCategories().size(); 
    }

}
