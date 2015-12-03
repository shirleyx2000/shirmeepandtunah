package ca.ece.ubc.cpen221.mp5;

import java.util.List;

// TODO: Use this class to represent a restaurant.
// State the rep invariant and abs

/**
 * Abstract datatype: an object with relatively unique public fields 
 * rep invariant: every entry is modifiable according to new restuarant details
 * 
 * @author Shirley
 *
 */
public class Restaurant {
    
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
    public double stars; //TODO: Should this change to an Integer?
    public long review_count; 
    public long price; //Number of dollar signs
    
    public boolean open; 
    public List<String> categories; //TODO: Account for rep exposure for Lists
    public List<String> neighbours; 
    public List<String> schools;
    
    public Restaurant (String name) {
        this.name = name; 
    }
    
    public void setType(String type) {
        this.type = type; 
    }
    
    public void setBusID(String business_id) {
        this.business_id = business_id; 
    }
    
    public void setAddr(String full_address) {
        this.full_address = full_address; 
    }
    
    public void setCity(String city) {
        this.city = city; 
    }
    
    public void setState(String state) {
        this.state = state; 
    }
    
    public void setPhotoURL(String photo_url) {
        this.photo_url = photo_url; 
    }
    
    public void setURL(String url) {
        this.url = url; 
    }
    
    public void setLong(double longitude) {
        this.longitude = longitude; 
    }
    
    public void setLat(double latitude) {
        this.latitude = latitude; 
    }
    
    public void setReviewCnt (long review_count) {
        this.review_count = review_count; 
    }

    public void setStars (double stars) {
        this.stars = stars; 
    }
    
    public void setPrice (long price) {
        this.price = price; 
    }
    
    public void setOpen(boolean open) {
        this.open = open; 
    }
    
    public void setCategories (List<String> categories) {
        this.categories = categories; 
    }
    
    public void setNeighbours (List<String> neighbours) {
        this.neighbours = neighbours; 
    }
    
    public void setSchools (List<String> neighbours) {
        this.schools = schools; 
    }
}
