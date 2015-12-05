package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;

// TODO: Use this class to represent a Yelp review.

public class Review {
    
    //Private fields
    private String type = "review";
    private String review_id;
    private String business_id;
    private String user_id;
    private int stars;
    private String text;
    private String date;
    private Map<String, Integer> votes; //useful, funny, cool
    
    /*
     * AVOIDING REP EXPOSURE:
     * If field is immutable, references to same object are okay
     * Else (mutable), reference to new object is returned
     * 
     * REP INVARIANT:
     * Once created, a Review cannot be edited. (subject to change)
     */
    
    //Methods 
    
    //Creator
    public Review(){
    }
    
    //Producer
        //None
    
    //Observer
    public String getType(){
        return this.type;
    }
    
    public String getReviewId(){
        return this.review_id;
    }
    
    public String getBusinessId(){
        return this.business_id; //String is immutable so sharing the reference does not pose a risk 
                                 //in changing the object which this.business_id points to
    }
    
    public String getUserId(){
        return this.user_id;
    }
    
    public int getStars(){ //no rep exposure for primitive types, value returned not reference
        return this.stars;
    }
    
    public String getText(){
        return this.text;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public Map<String, Integer> getVotes(){
        return new HashMap<String, Integer>(this.votes);
    }

    
    //Mutator
    public void setReviewId( String review_id ){
        this.review_id = review_id;
    }
    
    public void setBusinessId( String business_id ){
        this.business_id = business_id ;
    }
    
    public void setUserId( String user_id ){
        this.user_id  = user_id  ;
    }
    
    public void setStars( int stars ){
        this.stars = stars;
    }
    
    public void setText( String text ){
        this.text = text;
    }
    
    public void setDate( String date ){
        this.date = date;
    }
    
    public void setVotes( Map<String, Integer> votes ){
        Map<String, Integer> votesCopy = new HashMap<String, Integer>(votes);
        this.votes = votesCopy;
    }
    

}
