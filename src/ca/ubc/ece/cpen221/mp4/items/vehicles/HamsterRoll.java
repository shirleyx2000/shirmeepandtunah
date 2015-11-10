package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;

public class HamsterRoll implements Vehicle {

    private static final ImageIcon ballImage = Util.loadImage("hamster.gif");
    
    //Constants
    private static final int STRENGTH = 20;
    private static final int INITIAL_COOLDOWN = 5; //Min speed; medium fast 
    private static final int MIN_COOLDOWN = 0; //Max speed (smaller number = faster)
    private static final int MOVING_RANGE = 4;
    
    //State variables
    private Location location;
    private int energy;
    private int cooldown = INITIAL_COOLDOWN; 
    private Direction direction = Util.getRandomDirection();
    private boolean isDead = false; 
    private boolean start = true;
    
    public HamsterRoll(Location initialLocation) {
        this.location = initialLocation;
        this.isDead = false; 
    }

    @Override
    public ImageIcon getImage() {
        return ballImage;
    }

    @Override
    public String getName() {
        return "hamster";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }
    
    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }
    
    @Override
    public int getCoolDownPeriod() {
        return cooldown;
    }
    
    @Override
    public int getMovingRange() {
        return MOVING_RANGE; 
    }


    @Override
    public Command getNextAction(World world) {
        
        //HamsterRolls act like cars. Every action involves a MoveCommand.
        //Accelerates upon start
        increaseSpeed();
        //Must turn when faced with wall i.e. invalid location. Decelerate to turn.
        Location targetLocation = new Location(this.getLocation(), direction);
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
            return new MoveCommand(this, targetLocation);
        }
            //If item in targetLocation
                //If item.strength < own strength
                    //Destroy
                //Else
                    //Die
        
        return new WaitCommand();
    }
    
    public void increaseSpeed() {
        if (cooldown > 0) {
            cooldown--; 
        } else {
            cooldown = MIN_COOLDOWN; //Max speed
        }
    }

    public void decreaseSpeed() {
        if (cooldown < this.getCoolDownPeriod()) {
            cooldown++; 
        } else {
            cooldown = INITIAL_COOLDOWN;
        }
    }
    
    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }
    
    @Override
    public void loseEnergy(int energy) {
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }


}
