package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.Review;

public class PriceScale implements MP5Function {

    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        //Restaurant Price Scale
       
        return yelpRestaurant.getPrice();
    }

}
