package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

	private int closest = 10; // max number; greater than rabbit's view range
	private int temp;
	private boolean foxFound;

	public RabbitAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
	    
	    //Priority of actions:
	        //1. Don't die
	            //a. Eat! No grass? Move to grass! No grass nearby? Breed!
	            //b. Is a fox nearby? Escape!
	    
	    Location currLocation = animal.getLocation();
	    int rabbitEnergy = animal.getEnergy();
	    
        //Escape if fox is found nearby!
        for( Item item: world.searchSurroundings(animal) ){
            if( item.getName().equals("Fox")){
                Location foxLocation = item.getLocation();
                //Fox is adjacent to Rabbit if distance == 1
                if( currLocation.getDistance(foxLocation) <= animal.getViewRange() ){
                    Location runLocation = new Location(currLocation, Util.getDirectionTowards(foxLocation, currLocation) );
                    boolean isLocationEmpty = true;
                    //Make sure runLocation is valid and empty. If not, Rabbit dies.
                    if( Util.isValidLocation(world, runLocation) ){
                        for( Item it : world.searchSurroundings(animal) ){
                            if( it.getLocation().equals(runLocation) ){
                                isLocationEmpty = false;
                            }
                        }
                        if( isLocationEmpty ){
                            return new MoveCommand(animal, runLocation);
                        }
                    }
                }
            }
            //else if
        }
        
        //Eat if grass nearby!
        for( Item item : world.searchSurroundings(animal) ){
            if( item.getName().equals("grass") ){
                if( (currLocation.getDistance(item.getLocation()) == 1) ){
                     return new EatCommand(animal, item);
                 }
            }
        }

        //Else, go towards grass closest to it.
        for( Item item : world.searchSurroundings(animal)){
            if( item.getName().equals("grass") ){
                Direction towardsGrass = Util.getDirectionTowards(currLocation, item.getLocation());
                Location targetLocation = new Location(currLocation, towardsGrass);
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
        
    
        //No foxes are found nearby. Breed if at max energy.
        if( rabbitEnergy == animal.getMaxEnergy() ){
            
            Location breedLocation = new Location(currLocation, Util.getRandomDirection());
            boolean isOffspringAdjToFox = false;
            boolean isLocationEmpty = true;
            
            if( Util.isValidLocation(world, breedLocation) ){
                for( Item it : world.searchSurroundings(animal) ){
                    if( it.getLocation().equals(breedLocation) ){
                        isLocationEmpty = false;
                    }
                    if( it.getName().equals("Fox") && (breedLocation.getDistance(it.getLocation()) == 1)){
                        isOffspringAdjToFox = true;
                    }
                }
                if( (isLocationEmpty == true) && (isOffspringAdjToFox == false) ){
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
        
        //If no adjacent location is empty, Rabbit is surrounded by objects. Do nothing. 
        return new WaitCommand();
	}

}
	
