package ca.ubc.ece.cpen221.mp4.items.extra;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Sun implements Item {

    private final static ImageIcon sunImage = Util.loadImage("sun.gif");
    
    private Location location;
    /**
     * Places a Sun at <code> location </code>. The location must be valid and
     * empty. The sun is the strongest, it will destroy everything 
     * that tries to invade its location.
     *
     * @param location
     *            : the location where this sun will be created
     */
    public Sun(Location location) {
        this.location = location;
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
    public ImageIcon getImage() {
        return sunImage;
    }

    @Override
    public String getName() {
        return "sun";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return 1000000;
    }

    @Override
    public void loseEnergy(int energy) {
        //it can never lose energy 
    }

    @Override
    public boolean isDead() {
        // will never die!
        return false;
    }

}
