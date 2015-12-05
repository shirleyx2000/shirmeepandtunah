package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Collections;
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
    
    public String type = "business";
    public String business_id; 
    public String name; 
    public String full_address; 
    public String city; 
    public String state; 
    public String photo_url; 
    public String url; 
    public String JSONStr; 
    
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
    
    public String getName() {
        return this.name; 
    }
    
    public void setJSONStr (String JSONStr) {
        this.JSONStr = JSONStr;
    }
    
    public String getJsonStr() {
        return this.JSONStr; 
    }
    
    public String getType() {
        return this.type; 
    }
    
    public void setBusID(String business_id) {
        this.business_id = business_id; 
    }
    
    public String getBusID() {
        return this.business_id; 
    }
    
    public void setAddr(String full_address) {
        this.full_address = full_address; 
    }
    
    public String getAddr() {
        return this.full_address; 
    }
    
    public void setCity(String city) {
        this.city = city; 
    }
    
    public String getCity() {
        return this.city; 
    }
    
    public void setState(String state) {
        this.state = state; 
    }
    
    public String getState() {
        return this.state; 
    }
    
    public void setPhotoURL(String photo_url) {
        this.photo_url = photo_url; 
    }
    
    public String getPhotoURL() {
        return this.photo_url; 
    }
    
    public void setURL(String url) {
        this.url = url; 
    }
    
    public String getURL() {
        return this.url; 
    }
    
    public void setLong(double longitude) {
        this.longitude = longitude; 
    }
    
    public double getLong() {
        return this.longitude; 
    }
    
    public void setLat(double latitude) {
        this.latitude = latitude; 
    }
    
    public double getLat() {
        return this.latitude; 
    }
    
    public void setReviewCnt (long review_count) {
        this.review_count = review_count; 
    }
    
    public long getReviewCnt() {
        return this.review_count; 
    }

    public void setStars (double stars) {
        this.stars = stars; 
    }
    
    public double getStars() {
        return this.stars; 
    }
    
    public void setPrice (long price) {
        this.price = price; 
    }
    
    public long getPrice() {
        return this.price; 
    }
    
    public void setOpen(boolean open) {
        this.open = open; 
    }
    
    public boolean getOpen() {
        return this.open; 
    }
    
    public void setCategories (List<String> categories) {
        this.categories = categories; 
    }
    
    public List<String> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }
    
    public void setNeighbours (List<String> neighbours) {
        this.neighbours = neighbours; 
    }
    
    public List<String> getNeighbours() {
        return Collections.unmodifiableList(this.neighbours);
    }
    
    public void setSchools (List<String> neighbours) {
        this.schools = schools; 
    }
     
    public List<String> getSchools() {
        return Collections.unmodifiableList(this.schools);
    }
}
