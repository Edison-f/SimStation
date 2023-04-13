package plague;

import mvc.*;
import simstation.*;
import java.awt.*;

class Plague extends Agent {

    public Plague() {
        super(Color.white);
        heading = Heading.random();
    }

    public void update() {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);
    }

}

class PlagueFactory extends SimulationFactory {
    public Model makeModel() { return new PlagueSimulation(); }
    public String getTitle() { return "Plague";}
}

public class PlagueSimulation extends Simulation {
    public static int VIRULENCE = 50; // % chance of infection 0 - 100
    public static int RESISTANCE = 2; // % chance of resisting infection 0 - 10
    // etc.
    public void populate() {
        for(int i = 0; i < 15; i++)
            addAgent(new Plague());
    }

    public static void main(String[] args) {
        AppPanel panel = new SimulationPanel(new PlagueFactory());
        panel.display();
    }
}