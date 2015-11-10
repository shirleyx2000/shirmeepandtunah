package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class HotAirBalloon implements Vehicle {

    private static final int STRENGTH = 5; // in air, so almost no strength unless you're flying? 
    private static final int COOLDOWN = 5; //slow
    private static final ImageIcon balloonImage = Util.loadImage("balloon.gif");
    private Location location;
    private int energy;
    
    public HotAirBalloon(Location initialLocation) {
        this.location = initialLocation;
    }    
    
    @Override
    public ImageIcon getImage() {
        return balloonImage;
    }

    @Override
    public String getName() {
        return "Hot Air Balloon"; 
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
        // TODO Auto-generated method stub ????
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        return energy<=0;
    }

    @Override
    public int getPlantCalories() {
        //Hot air balloon does not contain meat calories
        return 0;
    }

    @Override
    public int getMeatCalories() {
        //Hot air balloon does not contain meat calories
        return 0;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return 5;
    }

    @Override
    public int getCoolDownPeriod() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Command getNextAction(World world) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void accelerate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decelerate() {
        // TODO Auto-generated method stub
        
    }


}
