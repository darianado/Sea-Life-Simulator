import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of interface pred here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Predator extends Animal
{
    /**
     * An example of a method header - replace this comment with your own
     *
     * @param  y a sample parameter for a method
     * @return   the result produced by sampleMethod
     * 
     */
    
    private static final Random rand = Randomizer.getRandom();
    
    private int foodLevel;

    private Class preyType ;

    

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

    abstract int getFoodValue();

    protected void setFoodLevel(int food)
    {
        foodLevel = food;
    }

    protected int getFoodLevel()
    {
        return foodLevel;
    }

    protected void decrementFoodLevel()
    {
        foodLevel--;
    }

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

    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
}
