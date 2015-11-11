package ca.ubc.ece.cpen221.mp4.items.vehicles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class HamsterRoll implements Vehicle {

    private static final ImageIcon ballImage = Util.loadImage("hamster.gif");
    
    //Constants
    private static final int STRENGTH = 40;
    private static final int INITIAL_COOLDOWN = 5; //Min speed; medium fast 
    private static final int MIN_COOLDOWN = 0; //Max speed (smaller number = faster)
    private static final int MOVING_RANGE = 1; //Can only move to adj tile per step
    
    //State variables
    private Location location;
    private int energy;
    private int cooldown = INITIAL_COOLDOWN; 
    private Direction direction = Util.getRandomDirection();
    private boolean isDead = false; 
    
    public HamsterRoll(Location initialLocation) {
        this.location = initialLocation;
        this.isDead = false; 
    }

    @Override
    public ImageIcon getImage() {
        return ballImage;
    }

    @Override
    public String getName() {
        return "hamster";
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
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }
    
    @Override
    public int getCoolDownPeriod() {
        return cooldown;
    }
    
    @Override
    public int getMovingRange() {
        return MOVING_RANGE; 
    }

    @Override
    public Command getNextAction(World world) {
        
        //HamsterRolls act like cars. If possible, will move to adj. location.
        
        //Increment speed unless at max.
        increaseSpeed();
        
        //Move! 
        Location targetLocation = new Location(this.getLocation(), direction);
        
        if( !Util.isValidLocation(world, targetLocation) ){
            //Keep track of possible directions to go towards.
            Map<Direction, Integer> facedDir = new HashMap<Direction, Integer>();
            for( Direction d : Direction.values() ){
                facedDir.put(d, 0); //Not facing other directions
            }
            //Get valid location
            while( !Util.isValidLocation(world, targetLocation) ) { 
                System.out.println(facedDir);
                facedDir.put(direction, 1);
                //Try random direction. Make sure it hasn't been faced yet.
                Direction d = Util.getRandomDirection();
                if( facedDir.get(d).equals(0) ){ 
                    direction = d;
                    targetLocation = new Location(this.getLocation(), direction);
                }
            }
            //Valid location must have been found.
        }
        
        if( Util.isLocationEmpty(world, targetLocation) ) {
            return new MoveCommand(this, targetLocation);
        } else {
            //Target (adj) location not empty. Check if item has less strength. If so, destroy. Else, die.
            for( Item item: world.searchSurroundings(location, this.getMovingRange())){
                //if( direction.equals(Util.getDirectionTowards(location, item.getLocation())) ){
                if( item.getLocation().equals(targetLocation) ){
                  //If the item has strength < HR, then item dies
                    if( item.getStrength() < this.getStrength() ){
                        item.loseEnergy(Integer.MAX_VALUE); //*dying noises*
                        return new MoveCommand(this, targetLocation);
                    } else if ( item.getStrength() == this.getStrength() ){ //Bounce off object
                        
                      //Keep track of possible directions to go towards.
                      Map<Direction, Integer> facedDir = new HashMap<Direction, Integer>();
                      for( Direction d : Direction.values() ){
                          if( d.equals(direction) ){
                              facedDir.put(d, 1);
                          } else{
                              facedDir.put(d, 0); //Not facing other directions
                          }
                      }
                      //while loop will only run until possible moves in all 4 directions have been tried
                      while( !Util.isValidLocation(world, targetLocation) || !Util.isLocationEmpty(world, targetLocation) ) { 
                          System.out.println(facedDir);
                          facedDir.put(direction, 1);
                          if( !facedDir.values().contains(0) ){ 
                              //At this point, no possible moves left i.e. trapped by other objects with equal strength.
                              return new WaitCommand();
                          }
                          //Try random direction. Make sure it hasn't been faced yet.
                          Direction d = Util.getRandomDirection();
                          if( facedDir.get(d).equals(0) ){ 
                              direction = d;
                              targetLocation = new Location(this.getLocation(), direction);
                          }
                      }
                      //Managed to exit while loop! A possible move has been found.
                      return new MoveCommand(this, targetLocation);
                        
                    } else { //Else, HR dies
                        isDead = true; 
                        return new WaitCommand();
                    }
                }
            }
            
            //And this point here, an adj location is not empty AND there's no item in its surroundings equal to an adj location... Method should not reach here.
            System.out.println("Unreachable code reached.");
            return new WaitCommand();
        }
    }


    public void increaseSpeed() {
        if (cooldown > 0) {
            cooldown--; 
        } else {
            cooldown = MIN_COOLDOWN; //Max speed
        }
    }

    public void decreaseSpeed() {
        if (cooldown < this.getCoolDownPeriod()) {
            cooldown++; 
        } else {
            cooldown = INITIAL_COOLDOWN;
        }
    }
    
    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }
    
    @Override
    public void loseEnergy(int energy) {
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }


}
