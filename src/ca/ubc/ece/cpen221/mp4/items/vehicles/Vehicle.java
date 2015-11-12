package ca.ubc.ece.cpen221.mp4.items.vehicles;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * A Vehicle is an object in a {@link World} that is defined by:
 *      - Acceleration upon startup
 *      - Run over and destroy items of lesser strength, otherwise dies
 *      - Decelerate/slow down upon turning
 */

public interface Vehicle extends Actor, MoveableItem {
    
    /**
     * Increases the vehicle's speed by decreasing the cooldown period by 1 step
     * 
     * modifies: cooldown period decreases by 1 step
     */
    void speedUp();
    
    
    /**
     * Decreases the vehicle's speed by increasing the cooldown period by 1 step
     * 
     * modifies: cooldown period increases by 1 step
     */
    void slowDown();
 
}
