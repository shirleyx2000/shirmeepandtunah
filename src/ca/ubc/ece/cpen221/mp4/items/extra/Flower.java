package ca.ubc.ece.cpen221.mp4.items.extra;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Flower implements Item {

    private final static ImageIcon flowerImage = Util.loadImage("flower.gif");

    private Location location;
    private int energy; 
    
    /**
     * Places a Sun at <code> location </code>. The location must be valid and
     * empty. The sun is the strongest, it will destroy everything 
     * that tries to invade its location.
     *
     * @param location
     *            : the location where this sun will be created
     */
    public Flower(Location location) {
        this.location = location;
    }
    
    @Override
    public int getPlantCalories() {
        return 5;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public ImageIcon getImage() {
        return flowerImage;
    }

    @Override
    public String getName() {
        return "flower";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return 5;
    }

    @Override
    public void loseEnergy(int energy) {
//        this.energy = this.energy - energy;
        
    }

    @Override
    public boolean isDead() {
        return false; 
    }

}
