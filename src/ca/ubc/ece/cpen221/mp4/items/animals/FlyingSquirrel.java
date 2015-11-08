package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class FlyingSquirrel implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 40;
    private static final int MAX_ENERGY = 70;
    private static final int STRENGTH = 75;
    private static final int MIN_BREEDING_ENERGY = 20;
    private static final int VIEW_RANGE = 4;
    private static final int COOLDOWN = 2;
    private static final ImageIcon squirrelImage = Util.loadImage("squirrel.gif");

    private final AI ai;

    private Location location;
    private int energy;

    /**
     * Create a new {@link FlyingSquirrel} with an {@link AI} at
     * <code> initialLocation </code>. The <code> initialLoation
     * </code> must be valid and empty.
     *
     * @param squirrelAI
     *            : The AI designed for FlyingSquirrel
     * @param initialLocation
     *            : the location where this squirrel will be created
     */
    public FlyingSquirrel(AI squirrelAI, Location initialLocation) {
        ai = squirrelAI;
        location = initialLocation;
        energy = INITIAL_ENERGY;
    }

    @Override
    public LivingItem breed() {
        FlyingSquirrel child = new FlyingSquirrel(ai, location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
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
        return squirrelImage;
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
        return "Squirrel";
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--; // Loses 1 energy regardless of action.
        return nextAction;
    }

    @Override
    public int getPlantCalories() {
        // This FlyingSquirrel is not a plant.
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
        this.energy -= energyLoss;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;

    }
}
