import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a krill.
 * 
 * @author Dorin Dariana, Luke Ayres
 * @version feb.2021
 */
public class Krill extends Prey
{
    // Characteristics shared by all krills (class variables).
    
    // number of steps a krill can go before it has to eat again.
    private static final int FOOD_VALUE = 30;
    // The age to which a krill can live.
    private static final int MAX_AGE = 80;
    // The likelihood of a krill breeding.
    private static final double BREEDING_PROBABILITY = 0.99;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 7;
    //steps until he can breed again
    private static final int GAP_BETWEEN_BREEDING = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Create a new krill. A krill may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the krill will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Krill(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, 0);
    }
    
    public Animal getYoung(Field field, Location loc)
    {
        return new Krill(false,field,loc);
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
     * This is what the krill does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newKrills A list to return newly born krills.
     * @param timeOfDay The current time of day he acts in
     */
    public void act(List<Animal> newKrills, String timeOfDay)
    {
        eatPlant();
        incrementHunger();
        incrementAge();
        incrementLastBred();
        updateInfection();
        
        if(isAlive()) {
            if(canBreed())
                giveBirth(newKrills, timeOfDay);            
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
