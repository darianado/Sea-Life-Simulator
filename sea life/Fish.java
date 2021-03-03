import java.util.List;
import java.util.Random;

/**
 * A simple model of a fish
 * 
 * @author Dorin Dariana, Luke Ayres
 * @version feb.2021
 */
public class Fish extends Prey
{
    // Characteristics shared by all fish (class variables).
    
    // number of steps a fish can go before it has to eat again.
    private static final int FOOD_VALUE = 25;
    // The age to which a fish can live.
    private static final int MAX_AGE = 90;
    // The likelihood of a fish breeding.
    private static final double BREEDING_PROBABILITY = 1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    //steps until he can breed again
    private static final int GAP_BETWEEN_BREEDING = 1;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    

    /**
     * Create a new fish. A fish may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the fish will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location,0);
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
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }
     /**
     * A fish can breed if it has reached the breeding age.
     * @return true if the fish can breed, false otherwise.
     */
    public boolean canBreed()
    {
        return getAge() >= GAP_BETWEEN_BREEDING;
    }
    
    /**
     * This is what the fish does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newFish A list to return newly born fish.
     * @param timeOfDay The current time of day he acts in
     */
    public void act(List<Animal> newFish, String timeOfDay)
    {
        eatPlant();
        incrementHunger();
        incrementAge();
        incrementLastBred();
        updateInfection();
        
        if(isAlive()) {
             if(canBreed())
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
    
}
