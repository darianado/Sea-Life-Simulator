import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
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
    //
    //private boolean hasBred;

    private int lastBred;

    private static final Random rand = Randomizer.getRandom();

    private int age;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
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

    public boolean hasBred()
    {
        // if(lastBred>=getGapBreeding())
        // {lastBred = 0; return true;
            // return false;
        // }
        return lastBred < getGapBreeding();
    }
    // public void setHasBred(boolean hasBred)
    // {
        // this.hasBred = hasBred;
    // }

    public boolean isMale()
    {
        return isMale;
    }
   
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals, String timeOfDay);

    abstract public double getBreedingProb();

    abstract public int getMaxLitterSize();

    abstract public boolean canBreed();

    abstract public int getMaxAge();

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

    public void updateBreeding() 
    {
        // if(lastBred==true) lastBred++;
        // if(lastBred>= getGapBreeding())
        // {
            // lastBred=0;
            // setHasBred(false);
        // }
       
        if(lastBred>= getGapBreeding()) lastBred=0;
        
        
    }
    public void incrementLastBred()
    {
         lastBred++;
    }

    public Class getThisClass()
    {
        return getClass();
    }

    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected void giveBirth(List<Animal> newAnimals, String timeOfDay)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        if(isMale()) return;

        Field field = getField();

        Animal mate = findMale(field);
        if(mate == null || mate.hasBred()) return;

        mate.updateBreeding();
        updateBreeding();

        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed(timeOfDay);

        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = getYoung(field,loc);
            newAnimals.add(young);
        }
    }

    abstract public Animal getYoung(Field field, Location loc);

    private Animal findMale(Field field)
    {
        List<Location> notFree = field.adjacentLocations(getLocation());

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

    protected int getAge()
    {
        return age;
    }

    protected void setAge(int age)
    {
        this.age=age;
    }

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
