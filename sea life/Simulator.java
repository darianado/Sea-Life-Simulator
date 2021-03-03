import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing sea animals.
 * 
 * @author David J. Barnes and Michael Kölling, Dorin Dariana, Luke Ayres
 * @version feb.2021
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a predator will be created in any given grid position.
    private static final double PRED_CREATION_PROBABILITY = 0.05;
    // The probability that a prey will be created in any given grid position.
    private static final double PREY_CREATION_PROBABILITY = 0.05;
    

    // List of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Krill.class, Color.ORANGE);
        view.setColor(Whale.class, Color.BLUE);
        view.setColor(Fish.class, Color.RED);
        view.setColor(Seal.class, Color.GRAY);
        view.setColor(Shark.class, Color.BLACK);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(400);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * @return timeOfDay the current time of day
     * if step % 24 < 6 or step % 24 >= 18, returns "night"
     * if step % 24 >= 6 and step % < 12, returns "morning"
     * otherwise, returns "afternoon"
     */
    public String getTimeOfDay()
    {
        int hour = step % 24;
        if(hour < 6 || hour >= 18)
        {
            return "Night";
        } else if(hour < 12)
        {
            return "Morning";
        } else
        {
            return "Afternoon";
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each animal
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();
        
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals, getTimeOfDay());
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        
        field.incrementPlantGrowth();
        
        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);

        view.showStatus(step, getTimeOfDay(), field);
    }
    
    

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, getTimeOfDay(), field);
    }
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= PREY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Krill krill = new Krill(true, field, location);
                    animals.add(krill);
                }
                else if(rand.nextDouble() <= PREY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fish fish = new Fish(true, field, location);
                    animals.add(fish);
                }
                else if(rand.nextDouble() <= PRED_CREATION_PROBABILITY) {
                   Location location = new Location(row, col);
                    Whale whale = new Whale(true, field, location);
                    animals.add(whale);
                }
                
                else if(rand.nextDouble() <= PRED_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Seal seal = new Seal(true, field, location);
                    animals.add(seal);
                }
                else if(rand.nextDouble() <= PRED_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Shark shark = new Shark(true, field, location);
                    animals.add(shark);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
