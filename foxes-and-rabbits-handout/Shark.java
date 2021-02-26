import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Shark extends Predator
{
    // Characteristics shared by all foxes (class variables).
 
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a fox can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.99;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int FOOD_VALUE = 12;
    
    private static final int GAP_BETWEEN_BREEDING = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
   

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Shark(boolean randomAge, Field field, Location location)
    {
        super(randomAge,field, location,0,Fish.class);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(FOOD_VALUE));
        }
        else {
            setFoodLevel(FOOD_VALUE);
        }
    }
    public Animal getYoung(Field field, Location loc)
    {
        return new Shark(false,field,loc);
    }
    public double getBreedingProb()
    {
        return BREEDING_PROBABILITY;
    }
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    public int getGapBreeding()
    {
        return GAP_BETWEEN_BREEDING;
    }
     public int getMaxAge()
    {
        return MAX_AGE;
    }
    public int getFoodValue()
    {
        return FOOD_VALUE;
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
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newSharks, String timeOfDay)
    {
        incrementAge();
        incrementHunger();
        incrementLastBred();
        if(isAlive()) {
             if(!hasBred())
                giveBirth(newSharks, timeOfDay);           
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    

   
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        decrementFoodLevel();
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
  
    
    
    ///**
    // * Check whether or not this fox is to give birth at this step.
    // * New births will be made into free adjacent locations.
    // * @param newFoxes A list to return newly born foxes.
    // */
    //protected void giveBirth(List<Animal> newSharks)
    //{
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
    //    Field field = getField();
    //    List<Location> free = field.getFreeAdjacentLocations(getLocation());
    //    int births = breed();
    //    for(int b = 0; b < births && free.size() > 0; b++) {
    //        Location loc = free.remove(0);
    //        Shark young = new Shark(false, field, loc);
    //        newSharks.add(young);
    //    }
    //}
        
    
 }
