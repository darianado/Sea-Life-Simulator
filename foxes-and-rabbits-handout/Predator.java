import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A class representing shared characteristics of the predetor animals.
 *
 * @author Dorin Dariana, Luke Ayres
 * @version feb.2021
 */
abstract public class Predator extends Animal
{
    
    private static final Random rand = Randomizer.getRandom();

    //the food level counter until the animal starves
    private int foodLevel;
    //the type of animal he eats
    private Class preyType ;

    /**
     * Create a new predator at location in field, with an age and a prey type.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param age The age of the animal
     * @param prey The type of pray he eats
     */
    public Predator(boolean randomAge, Field field, Location location, int age, Class prey)
    {
        super(field, location,age);
        this.preyType= prey;
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

    /**
     * Look for a pray to eat in the adjacent locations 
     */
    public Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal!=null && animal.getClass() == preyType) {
                Animal prey = (Animal) animal;
                if(prey.isAlive()) {
                    prey.setDead();
                    foodLevel = getFoodValue();
                    return where;
                }

            }
        }
        return null;
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
