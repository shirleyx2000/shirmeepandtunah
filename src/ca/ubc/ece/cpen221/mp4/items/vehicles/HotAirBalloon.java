package ca.ubc.ece.cpen221.mp4.items.vehicles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class HotAirBalloon implements Vehicle {

    private static final ImageIcon balloonImage = Util.loadImage("balloon.gif");
    
    //Constants
    private static final int STRENGTH = 150; // in air, so almost no strength unless you're flying? 
    private static final int INITIAL_COOLDOWN = 5; //slow
    private static final int MIN_COOLDOWN = 0; //Max speed (smaller number = faster)
    private static final int MOVING_RANGE = 1;
    
    //State variables
    private Location location;
    private int energy;
    private int cooldown = INITIAL_COOLDOWN; 
    private Direction direction = Util.getRandomDirection();
    private boolean isDead = false; 
    
    public HotAirBalloon(Location initialLocation) {
        this.location = initialLocation;
    }    
    
    @Override
    public ImageIcon getImage() {
        return balloonImage;
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
        this.energy = this.energy - energy;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getPlantCalories() {
        //Hot air balloon is not a plant
        return 0;
    }

    @Override
    public int getMeatCalories() {
        //Hot air balloon is not meat either.
        return 0;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return MOVING_RANGE;
    }

    @Override
    public int getCoolDownPeriod() {
        return INITIAL_COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        //Build up speed
        speedUp();
        
      //Move! 
        Location targetLocation = new Location(this.getLocation(), direction);
        
        //If object within range of 2 tiles in current direction, slow down.
        for( Item nearbyItem : world.searchSurroundings(this.getLocation(), 2) ){
            if( Util.getDirectionTowards(this.getLocation(), nearbyItem.getLocation()).equals(direction) ){
                slowDown();
                slowDown();
            }
        }
        if( !Util.isValidLocation(world, getNewLocation( MOVING_RANGE ))){
            slowDown();
            slowDown();
        }

        //Faced with wall...
        if( !Util.isValidLocation(world, targetLocation) ){
            //Keep track of possible directions to go towards.
            Map<Direction, Integer> facedDir = new HashMap<Direction, Integer>();
            for( Direction d : Direction.values() ){
                facedDir.put(d, 0); //Not facing other directions
            }
            //Get valid location
            while( !Util.isValidLocation(world, targetLocation) ) { 
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
            //Target (adj) location not empty. Check if item has less strength. If so, destroy. If equal, bounce off. Else, die.
            for( Item item: world.searchSurroundings(location, this.getMovingRange())){
                if( item.getLocation().equals(targetLocation) ){
                  //If the item has strength < HR, then item dies
                    if( item.getStrength() < this.getStrength() ){
                        item.loseEnergy(Integer.MAX_VALUE); //*dying noises*
                        return new WaitCommand(); //wait until item dies before moving
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
                      //Bouncing off object here.
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
    
    private Location getNewLocation( int movingRange ){
        
        //Hot air balloons move in a single direction by movingRange 
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();
        if( direction.equals(Direction.NORTH) ){
            y-=movingRange;
        } else if( direction.equals(Direction.EAST) ){
            x+=movingRange;
        } else if( direction.equals(Direction.SOUTH) ){
            y+=movingRange;
        } else {
            x-=movingRange;
        } 
        return new Location(x, y);
    }

    @Override
    public void speedUp() {
        if (cooldown > MIN_COOLDOWN) {
            cooldown--; 
        } else {
            cooldown = MIN_COOLDOWN; //Max speed
        }
        
    }

    @Override
    public void slowDown() {
        if (cooldown < INITIAL_COOLDOWN) {
            cooldown++; 
        } else {
            cooldown = INITIAL_COOLDOWN; //Min speed
        }
        
    }

}
