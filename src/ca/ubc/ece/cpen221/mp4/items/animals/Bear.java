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
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Bear implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 200;
    private static final int MAX_ENERGY = 1600;
    private static final int STRENGTH = 150;
    private static final int VIEW_RANGE = 4;
    private static final int MIN_BREEDING_ENERGY = 300;
    private static final int COOLDOWN = 5;
    private static final ImageIcon bearImage = Util.loadImage("bear.gif");

    private Location location;
    private int energy;
    
    /**
     * Create a new Bear at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Bear will be created
     */
    public Bear(Location initialLocation) {
        this.location = initialLocation; 
        this.energy = INITIAL_ENERGY; 
    }

    @Override
    public LivingItem breed() {
        Bear child = new Bear(location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public ImageIcon getImage() {
        return bearImage;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getMeatCalories() {
        // The amount of meat calories it provides is equal to its current
        // energy.
        return energy;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Bear";
    }

    @Override
    public Command getNextAction(World world) {
        // The Bear selects a random direction and check if the next location at
        // the direction is valid and empty. If yes, then it moves to the
        // location, otherwise it waits.
        Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(this.getLocation(), dir);
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
            this.energy--; // Loses 1 energy regardless of action.
            return new MoveCommand(this, targetLocation);
        }

        return new WaitCommand();
    }

    @Override
    public int getPlantCalories() {
        // This Bear is not a plant.
        return 0;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }
}
