package ca.ece.ubc.cpen221.mp5.statlearning;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.Review;

public class MeanRating implements MP5Function {

    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        // 0 - 5
        double averageRating = 0; 
        int count = 0; 
        
        averageRating = yelpRestaurant.getStars();
        
//        for (Review rev : db.getAllReviews().values()) {
//            if (rev.getBusinessId().equals(yelpRestaurant.getBusID())) {
//                averageRating += rev.getStars(); 
//                count++; 
//            }
//        }
//        
//        if (count > 0) {
//            averageRating = averageRating/(double)count; 
//        }
        return averageRating; 
    }
}
