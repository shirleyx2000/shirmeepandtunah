package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class FlyingSquirrel implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 40;
    private static final int MAX_ENERGY = 70;
    private static final int STRENGTH = 75;
    private static final int MIN_BREEDING_ENERGY = 20;
    private static final int VIEW_RANGE = 5;
    private static final int COOLDOWN = 2;
    private static final ImageIcon squirrelImage = Util.loadImage("squirrel.gif");

    private Location location;
    private int energy;
    private boolean isDead;

    /**
     * Create a new FlyingSquirrel at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the FlyingSquirrel will be created
     */
    public FlyingSquirrel(Location initialLocation) {
        location = initialLocation;
        energy = INITIAL_ENERGY;
    }
    
    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return 4; // Can fly across the air from tree to tree
    }

    @Override
    public ImageIcon getImage() {
        return squirrelImage;
    }

    @Override
    public String getName() {
        return "Squirrel";
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
        this.energy -= energy;
        if( this.energy <= 0){
            isDead = true;
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getPlantCalories() {
        return 0; //Not a plant
    }

    @Override
    public int getMeatCalories() {
        return energy;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        // The FlyingSquirrel stays by the trees 
        // Since no new trees are planted, it just keeps moving from tree to tree
        for (Item item : world.searchSurroundings(this)) {
            if (item.getName().equals("tree")){
                this.energy--; //loses energy if it moves
                Direction dir = Util.getDirectionTowards(this.getLocation(), item.getLocation());
                Location targetLocation = new Location(this.getLocation(), dir);
                if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
                    return new MoveCommand(this, targetLocation);
                }
            }
            
        }
        
        return new WaitCommand(); 
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public LivingItem breed() {
        FlyingSquirrel child = new FlyingSquirrel(location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
    }

    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

}
