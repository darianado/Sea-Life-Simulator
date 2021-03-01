import java.util.Random;
/**
 * 
 *
 * @author Luke Ayres, Ariana Dorin
 * @version 1 March 2021
 */
abstract public class Prey extends Animal
{
    private static final Random rand = Randomizer.getRandom();
    
    private int foodLevel;
    
    public Prey(boolean randomAge, Field field, Location location, int age)
    {
        super(field, location,age);
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            foodLevel = rand.nextInt(getFoodValue());
        }
        else {
            foodLevel = getFoodValue();
        }
    }
    /**
     * @return the specific maximum food level
     */
    abstract int getFoodValue();

    /**
     * Set the current food level of the predator
     * @param food The food level to be assigned
     */
    protected void setFoodLevel(int food)
    {
        foodLevel = food;
    }

    /**
     * @return the current food level
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }
    public void eatPlant()
    {
        Field field = getField();
        System.out.println(getClass());
        System.out.println(field);
        if(field.getPlantGrowth(getLocation()) > 2)
        {
            foodLevel+=field.getPlantGrowth(getLocation());
            field.resetPlantGrowth(getLocation());
        }
    }
    /**
     * Increment the hunger and set dead if too much
     * time has passed since he last ate
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
}