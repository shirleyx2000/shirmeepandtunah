package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * A Vehicle is an object in a {@link World} that is defined by:
 *      - Acceleration upon startup
 *      - Run over and destroy items of lesser strength, otherwise dies
 *      - Decelerate/slow down upon turning
 */

public interface Vehicle extends Actor, MoveableItem {
    
    /**
     * Increases the vehicle's speed by 1 step
     * 
     * modifies: decreases the cool down period by 1 step       
     */
    void increaseSpeed();
    
    
    /**
     * Decreases the vehicle's speed by 1 step
     * 
     * modifies: increases the cool down period by 1 step
     */
    void decreaseSpeed();
    
    //How to enforce turning only at low speeds???

    
    
        
    
    
}
