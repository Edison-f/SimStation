package flock;

import mvc.*;
import simstation.SimulationFactory;

public class FlockingFactory extends SimulationFactory {
    public Model makeModel() { return new FlockingSimulation(); }
    public String getTitle() { return "Flocking";}
}
