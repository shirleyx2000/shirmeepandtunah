package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class LogRaft implements MoveableItem {

    private static final int STRENGTH = 80; //in water 
    private static final int COOLDOWN = 50; //slow
    private static final ImageIcon raftImage = Util.loadImage("lograft.gif");
    private Location location;
    private int energy;
    
    public LogRaft(Location initialLocation) {
        this.location = initialLocation;
    }    
    
    @Override
    public ImageIcon getImage() {
        return raftImage;
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
        //log raft does not contain meat calories
        return 0;
    }

    @Override
    public int getMeatCalories() {
        //log raft does not contain meat calories
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



}
