package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * A Vehicle is an object in a {@link World} that is defined by:
 *      - Acceleration upon startup and deceleration upon stopping
 *      - Run over and destroy items of lesser strength, otherwise dies
 *      - Can only turn at low speeds
 */

public interface Vehicle extends Actor, MoveableItem {
    
    //getCoolDownPeriod
    //getNextAction
    //getMovingRange
    //moveTo
    //getImage
    //getLocation
    //getName
    //getStrength
    //isDead
    //loseEnergy
    
            

    /**
     * Increases the vehicle's speed by 1 step
     * 
     * modifies: decreases the cool down period by 1 step       
     */
    void accelerate();
    
    
    /**
     * Decreases the vehicle's speed by 1 step
     * 
     * modifies: increases the cool down period by 1 step
     */
    void decelerate();
    
    //How to enforce turning only at low speeds???

    
    
        
    
    
}
