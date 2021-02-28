import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling, Dorin Dariana, Luke Ayres
 * @version feb.2021
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animals gender
    private boolean isMale;
    //counter of steps since last bred
    private int lastBred;
    //the age of that animal
    private int age;

    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param age The age of the animal
     */
    public Animal(Field field, Location location,int age)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.age=age;
        isMale = rand.nextInt(2) == 0;
        lastBred=getGapBreeding();
    }

    /**
     * Get if the animal has bred recently enough 
     * that he momentarily can't breed again
     * @return true if he has bred recently and can't 
     *         breed right now or false otherwise
     */
    public boolean hasBred()
    {
        return lastBred < getGapBreeding();
    }

    /**
     * Get if the animal gender
     * @return true if he is a male or false if it is female
     */
    public boolean isMale()
    {
        return isMale;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     * @param timeOfDay the current time of day he needs to act
     */
    abstract public void act(List<Animal> newAnimals, String timeOfDay);

    /**
     * @return the animal's specific breeding probability
     */
    abstract public double getBreedingProb();

     /**
     * @return the animal's specific maximum litter size
     */
    abstract public int getMaxLitterSize();

     /**
     * @return true if the animal can breed
     */
    abstract public boolean canBreed();

    /**
     * @return the animal's specific maximum life span
     */
    abstract public int getMaxAge();

    /**
     * @return the animal's specific gap of steps needed to breed again
     */
    abstract public int getGapBreeding();

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @param timeOfDay the current time of day the breeding takes place
     * @return The number of births (may be zero).
     */
    protected int breed(String timeOfDay)
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProb() * (timeOfDay == "Night" ? 0.7 : 1.0)) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Check and update the animal last bred counter
     */
    public void updateBreeding() 
    {
        if(lastBred>= getGapBreeding()) lastBred=0;
    }

    /**
     * Increment the last bred counter
     */
    public void incrementLastBred()
    {
        lastBred++;
    }
  
    /**
     * Check whether or not this animal is to give birth at this step.
     * New animals will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     * @param timeOfDay the current time of day the birth takes place
     */
    protected void giveBirth(List<Animal> newAnimals, String timeOfDay)
    {
        //the male can't give birth
        if(isMale()) return;
        
        //the female cant give birth without a male mate
        Animal mate = findMale();
        if(mate == null || mate.hasBred()) return;

        mate.updateBreeding();
        updateBreeding();

        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed(timeOfDay);

        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = getYoung(field,loc);
            newAnimals.add(young);
        }
    }

    /**
     * Get a new baby of that specific animal
     * @return a new born animal
     * @param field The field of the young one
     * @param loc The location of the young one
     * 
     */
    abstract public Animal getYoung(Field field, Location loc);

    /**
     * Get a male mate if there is one in the adjacent location
     * @return the mate animal found or null
     */
    private Animal findMale()
    {
        List<Location> notFree = getField().adjacentLocations(getLocation());

        Iterator<Location> iterator = notFree.iterator();

        while(iterator.hasNext())
        {
            Location next = iterator.next();
            if(next == null || field.getObjectAt(next) == null) continue;
            if(field.getObjectAt(next).getClass().equals(this.getClass()))
            {
                Animal animal = (Animal)field.getObjectAt(next);
                if(animal.isMale()) return animal;
            }
        }
        return null;
    }

    /**
     * @return the age of the animal
     */
    protected int getAge()
    {
        return age;
    }

    /**
     * Set the age of the animal
     * @param the age of the animal
     */
    protected void setAge(int age)
    {
        this.age=age;
    }

    /**
     * Increment the age of the animal and set dead if too old
     */
    protected void incrementAge()
    {
        age++;
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
