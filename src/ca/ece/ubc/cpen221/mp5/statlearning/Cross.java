package ca.ece.ubc.cpen221.mp5.statlearning;

/**
 * This class represents a single location with co-ordinates in the form : (longitude, latitude).
 * Once created, an instance of this class cannot be modified.
 * 
 * @author guess_000
 *
 */
public class Cross{
    
    private double longitude;
    private double latitude;
    
    public Cross( double longitude, double latitude ){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public double getLongitude(){
        return this.longitude;
    }
    
    public double getLatitude(){
        return this.latitude;
    }
    
}