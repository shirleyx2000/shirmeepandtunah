package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
	private int closest = 2; // max number; greater than fox's view range

	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
	    
	    //Priority of actions
	        //1. Eat! If no food, go to food.
	        //2. If at max energy, breed! 
	    
       Location currLocation = animal.getLocation();
       int foxEnergy = animal.getEnergy();
        
        //Eat if Rabbit nearby!
        for( Item item : world.searchSurroundings(animal) ){
            if( item.getName().equals("Rabbit") ){
                if( (currLocation.getDistance(item.getLocation()) == 1) ){
                     return new EatCommand(animal, item);
                 }
            }
        }
        
        //Else, go towards Rabbit closest to it.
        for( Item item : world.searchSurroundings(animal)){
            if( item.getName().equals("Rabbit") ){
                Direction towardsRabbit = Util.getDirectionTowards(currLocation, item.getLocation());
                Location targetLocation = new Location(currLocation, towardsRabbit);
                boolean isLocationEmpty = true;
                if( Util.isValidLocation(world, targetLocation) ){
                  for( Item it : world.searchSurroundings(animal) ){
                      if( it.getLocation().equals(targetLocation) ){
                          isLocationEmpty = false;
                      }
                  }
                  if( isLocationEmpty ){
                      return new MoveCommand(animal, targetLocation);
                  }
                }
            }
        }
	    
        //Breed if at max energy.
        if( foxEnergy == animal.getMaxEnergy() ){
            
            Location breedLocation = new Location(currLocation, Util.getRandomDirection());
            boolean isLocationEmpty = true;
            
            if( Util.isValidLocation(world, breedLocation) ){
                for( Item it : world.searchSurroundings(animal) ){
                    if( it.getLocation().equals(breedLocation) ){
                        isLocationEmpty = false;
                    }
                }
                if( isLocationEmpty ){
                    return new BreedCommand(animal, breedLocation);
                }  
            }
        }  

        //Nothing is in sight and cannot breed. Move to empty adjacent location
        Direction moveDir = Util.getRandomDirection();
        Location targetLocation = new Location(currLocation, moveDir);
        boolean isLocationEmpty = true;
        if( Util.isValidLocation(world, targetLocation) ){
            for( Item it : world.searchSurroundings(animal) ){
                if( it.getLocation().equals(targetLocation) ){
                    isLocationEmpty = false;
                }
            }
            if( isLocationEmpty ){
                return new MoveCommand(animal, targetLocation);
            }
        }
        
        //If not empty, check all other adjacent locations.
        for( Direction dir : Direction.values() ){
            if( !dir.equals(moveDir) ){
                targetLocation = new Location(currLocation, dir);
                isLocationEmpty = true;
                if( Util.isValidLocation(world, targetLocation) ){
                    for( Item it : world.searchSurroundings(animal) ){
                        if( it.getLocation().equals(targetLocation) ){
                            isLocationEmpty = false;
                        }
                    }
                    if( isLocationEmpty ){
                        return new MoveCommand(animal, targetLocation);
                    }
                }
            }
        }
        
        //If no adjacent location is empty, Fox is surrounded by objects. Do nothing. 
        return new WaitCommand();
	}

}
