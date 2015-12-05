package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;

// TODO: Use this class to represent a Yelp user.

public class User {
    
    //Private fields
    private String url;
    private String type = "user";
    private String user_id;
    private String name;
    private int review_count;
    private float average_stars;
    private Map<String, Integer> votes; //useful, funny, cool
    
    /*
     * AVOIDING REP EXPOSURE:
     * If field is immutable, references to same object are okay
     * Else (mutable), reference to new object is returned
     */
    
    //Methods 
    
    //Creator
    public User( String name ){
        this.name = name;
    }
    
    //Producer
        //None
    
    //Observer
    public String getUrl(){
        return this.url;
    }
    
    public String getType(){
        return this.type;
    }
    
    public String getUserId(){
        return this.user_id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getReviewCount(){ //no rep exposure for primitive types
        return this.review_count;
    }
    
    public float getAverageStars(){ 
        return this.average_stars;
    }
    
    public Map<String, Integer> getVotes(){
        return new HashMap<String, Integer>(this.votes);
    }

    
    //Mutator
    public void setUrl( String url ){
        this.url = url;
    }
    
    public void setUserId( String user_id ){
        this.user_id  = user_id  ;
    }
    
    public void setReviewCount( int review_count ){
        this.review_count = review_count;
    }
    
    public void setAverageStars( float average_stars ){
        this.average_stars = average_stars;
    }
    
    public void setVotes( Map<String, Integer> votes ){
        Map<String, Integer> votesCopy = new HashMap<String, Integer>(votes);
        this.votes = votesCopy;
    }
}
