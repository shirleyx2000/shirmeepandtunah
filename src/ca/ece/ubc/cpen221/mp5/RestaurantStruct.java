package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantStruct {
    
    public RestaurantStruct(String type, String business_id, String name,
            String full_address, String city, String state, String photo_url,
            String url, double longitude, double latitude, double stars, long review_count,
            long price, boolean open, List<String> categories, List<String> neighbours, List<String> schools) {
        
        type = ""; 
        business_id = ""; 
        name = ""; 
        full_address = ""; 
        city = ""; 
        state = ""; 
        photo_url = ""; 
        url = ""; 
        
        longitude = 0.0; 
        latitude = 0.0; 
        stars = 0.0; 
        review_count = 0; 
        price = 0; 
        
        open = false; 
        categories = new ArrayList<String>(); 
        neighbours = new ArrayList<String>(); 
        schools = new ArrayList<String>(); 
    }
}
