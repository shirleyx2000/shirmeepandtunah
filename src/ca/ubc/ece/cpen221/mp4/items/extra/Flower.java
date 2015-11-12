package ca.ubc.ece.cpen221.mp4.items.extra;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Flower implements Item {

    private final static ImageIcon flowerImage = Util.loadImage("flower.gif");

    private Location location;
    private boolean isDead; 
    
    /**
     * Places a Flower at <code> location </code>. The location must be valid and
     * empty. A Flower is the weakest and can get trampled over easily by anything
     *
     * @param location
     *            : the location where this flower will be created
     */
    public Flower(Location location) {
        this.location = location;
        isDead = false; 
    }
    
    @Override
    public int getPlantCalories() {
        //about half of what a grass can provide IF eaten
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
        //almost no strength, therefore easily dies
        return 1;
    }

    @Override
    public void loseEnergy(int energy) {
        //live or die mechanism, it doesn't lose energy overtime
        //current AI does not implement eating flower, 
        //but flower behaves similarly to grass if used. 
        isDead = true; 
    }

    @Override
    public boolean isDead() {
        return isDead; 
    }

}
