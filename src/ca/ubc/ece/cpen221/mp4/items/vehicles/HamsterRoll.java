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

    private static final int STRENGTH = 20;
    private static final int COOLDOWN = 10; //medium fast 
    private static final ImageIcon ballImage = Util.loadImage("hamster.gif");
    private static final int VIEW_RANGE = 6;
    private Location location;
    private boolean isDead; 
    private int energy;
    private int currCoolDown = getCoolDownPeriod(); 
    
    public HamsterRoll(Location initialLocation) {
        this.location = initialLocation;
        this.isDead = false; 
    }
    
    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(this.getLocation(), dir);
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
            moveTo(targetLocation);
            return new MoveCommand(this, targetLocation);
        }

        return new WaitCommand();
    }

    @Override
    public void moveTo(Location targetLocation) {
        // TODO Auto-generated method stub
        

    }
    
    void accelerate() {
        if (currCoolDown > 0) {
            currCoolDown--; 
        }
    }

    void decelerate() {
        if (currCoolDown < 0) {
            currCoolDown++; 
        }
    }
    
    @Override
    public int getMovingRange() {
        return VIEW_RANGE; 
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
    public void loseEnergy(int energy) {
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        //comparison of strengths where?
        return false;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

}
