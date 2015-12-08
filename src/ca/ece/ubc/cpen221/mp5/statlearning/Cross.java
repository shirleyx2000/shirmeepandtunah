package ca.ece.ubc.cpen221.mp5.statlearning;

/**
 * This class represents a single location with co-ordinates in the form : (longitude, latitude).
 * 
 * @author guess_000
 *
 */
public class Cross{
    
    private double longitude;
    private double latitude;
    
    public Cross( double latitude, double longitude ){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public double getLongitude(){
        return this.longitude;
    }
    
    public double getLatitude(){
        return this.latitude;
    }
    
    public void setLongitude(double longitude){
        this.longitude = longitude; 
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude; 
    }
    
}