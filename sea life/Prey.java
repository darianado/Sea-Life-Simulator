import java.util.Random;
/**
 * 
 *
 * @author Luke Ayres, Dariana Dorin
 * @version 1 March 2021
 */
abstract public class Prey extends Animal
{
    private static final Random rand = Randomizer.getRandom();
    
    
    public Prey(boolean randomAge, Field field, Location location, int age)
    {
        super(field, location,age);
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            setFoodLevel(rand.nextInt(getFoodValue()));
        }
        else {
             setFoodLevel(getFoodValue());
        }
    }
    
    /**
     * @return the specific maximum food level
     */
    abstract int getFoodValue();
    
    /**
     * The animal eats a plant at its current location if the plant has a value of 2 or more
     */
    public void eatPlant()
    {
        Field field = getField();
        if( !isAlive() ) return;
        if(field.getPlantGrowth(getLocation()) > 2)
        {
            addFoodLevel(field.getPlantGrowth(getLocation()));
            field.resetPlantGrowth(getLocation());
        }
    }
    
}