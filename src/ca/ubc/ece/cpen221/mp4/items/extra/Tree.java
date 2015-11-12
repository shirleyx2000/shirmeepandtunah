package ca.ubc.ece.cpen221.mp4.items.extra;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Tree implements Item {

    private final static ImageIcon treeImage = Util.loadImage("tree.gif");

    private Location location;
    /**
     * Places a Tree at <code> location </code>. The location must be valid and
     * empty. Only way to die is through collision with car? 
     *
     * @param location
     *            : the location where this tree will be created
     */
    public Tree(Location location) {
        this.location = location;
    }
    
    @Override
    public int getPlantCalories() {
        //same as plants 
        return 10;
    }

    @Override
    public int getMeatCalories() {
        // not a meat
        return 0;
    }

    @Override
    public ImageIcon getImage() {
        return treeImage;
    }

    @Override
    public String getName() {
        return "tree";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        //stronger than all animals
        return 1000;
    }

    @Override
    public void loseEnergy(int energy) { 
        //doesn't die, until further AI changes
    }

    @Override
    public boolean isDead() {
        // groot shall not die!
        return false;
    }

}
