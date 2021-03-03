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
                    setFoodLevel(getFoodValue());
                    return where;
                }
            }
        }
        return null;
    }

}
