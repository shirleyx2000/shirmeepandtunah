package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class HamsterBall implements MoveableItem {

    private static final int STRENGTH = 20;
    private static final int COOLDOWN = 10; //medium fast 
    private static final ImageIcon ballImage = Util.loadImage("hamster.gif");
    private Location location;
    private int energy;
    
    public HamsterBall(Location initialLocation) {
        this.location = initialLocation;
    }    
    
    @Override
    public ImageIcon getImage() {
        return ballImage;
    }

    @Override
    public String getName() {
        return "Hamster Ball"; 
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
        // TODO Auto-generated method stub
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        return energy<=0;
    }

    @Override
    public int getPlantCalories() {
        //hamster ball does not contain meat calories
        return 0;
    }

    @Override
    public int getMeatCalories() {
        //hamster ball does not contain meat calories
        return 0;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return 2;
    }

}
