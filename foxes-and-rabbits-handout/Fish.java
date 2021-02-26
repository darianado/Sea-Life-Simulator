import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Fish extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 20;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.99;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    
    private static final int GAP_BETWEEN_BREEDING = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(boolean randomAge, Field field, Location location)
    {
        super(field, location,0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }
    public Animal getYoung(Field field, Location loc)
    {
        return new Fish(false,field,loc);
    }
    public int getGapBreeding()
    {
        return GAP_BETWEEN_BREEDING;
    }
    public double getBreedingProb()
    {
        return BREEDING_PROBABILITY;
    }
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
     public int getMaxAge()
    {
        return MAX_AGE;
    }
    
     /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    public boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newFish, String timeOfDay)
    {
        incrementAge();
        incrementLastBred();
        if(isAlive()) {
             if(!hasBred())
                giveBirth(newFish, timeOfDay);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    ///**
    // * Check whether or not this rabbit is to give birth at this step.
    // * New births will be made into free adjacent locations.
    // * @param newRabbits A list to return newly born rabbits.
    // */
    //protected void giveBirth(List<Animal> newFish)
    //{
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
    //    Field field = getField();
    //    List<Location> free = field.getFreeAdjacentLocations(getLocation());
    //    int births = breed();
    //    for(int b = 0; b < births && free.size() > 0; b++) {
    //        Location loc = free.remove(0);
    //        Fish young = new Fish(false, field, loc);
    //        newFish.add(young);
    //    }
    //}
        
    
}
