package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantStruct {
    
    public String type;
    public String business_id; 
    public String name; 
    public String full_address; 
    public String city; 
    public String state; 
    public String photo_url; 
    public String url; 
    
    public double longitude; 
    public double latitude; 
    public double stars; 
    public long review_count; 
    public long price; 
    
    public boolean open; 
    public List<String> categories; 
    public List<String> neighbours; 
    public List<String> schools;
        
    public RestaurantStruct(String type, String business_id, String name,
            String full_address, String city, String state, String photo_url,
            String url, double longitude, double latitude, double stars, long review_count,
            long price, boolean open, List<String> categories, List<String> neighbours, List<String> schools) {
        
        this.type = type; 
        this.business_id = business_id; 
        this.name = name; 
        this.full_address = full_address; 
        this.city = city; 
        this.state = state; 
        this.photo_url = photo_url; 
        this.url = url; 
        
        this.longitude = longitude; 
        this.latitude = latitude; 
        this.stars = stars; 
        this.review_count = review_count; 
        this.price = price; 
        
        this.open = open; 
        this.categories = categories; 
        this.neighbours = neighbours; 
        this.schools = schools; 
    }
}
