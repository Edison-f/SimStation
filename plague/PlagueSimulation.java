package plague;

import mvc.*;
import simstation.*;
import java.awt.*;

class Plague extends Agent {

    private boolean isInfected;
    private int RESISTANCE = Utilities.rng.nextInt(11); // % chance of resisting infection 0 - 10

    public Plague() {
        super();
        setColor(Color.green);
        heading = Heading.random();
    }

    public void infect() {
        isInfected = true;
        setColor(Color.red);
    }

    public int getResistance() {
        return RESISTANCE;
    }

    public int getIsInfected() {
        if (isInfected) {
            return 1;
        }
        return 0;
    }

    public void update() {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);
        Plague p = (Plague) this.world.getNeighbor(this, 20);
        if (p != null && this.isInfected == true) {
            int randomNum = Utilities.rng.nextInt(100);
            int resistance = p.getResistance();
            int virulence = PlagueSimulation.getVirulence();
            if (randomNum >= resistance && virulence > randomNum) {
                p.infect();
            }
        }
    }
}

class PlagueFactory extends SimulationFactory {
    public Model makeModel() { return new PlagueSimulation(); }
    public String getTitle() { return "Plague";}
}

public class PlagueSimulation extends Simulation {
    public static int VIRULENCE = Utilities.rng.nextInt(50); // % chance of infection 0 - 100
    private int INFECTED = 1;
    private int UNINFECTED = 10;
    public void populate() {
        // For hosts (infected)
        for (int i = 0; i < INFECTED; i ++) {
            Plague p = new Plague();
            p.infect();
            addAgent(p);
        }
        // For normals (uninfected)
        for (int i = 0; i < UNINFECTED; i ++) {
            Plague p = new Plague();
            addAgent(p);
        }
    }

    @Override
    public void stats() {
        int sum = 0;
        for(int i = 0; i < agents.size(); i++) {
            Plague p = (Plague) agents.get(i);
            int infected = p.getIsInfected();
            sum += infected;
        }
        Utilities.inform(new String[] {
                "#agents = " + agents.size(),
                "clock = " + String.valueOf(getClock()),
                "%infected = " + ((double) sum / agents.size() * 100),
        });
    }

    public static int getVirulence() {
        return VIRULENCE;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimulationPanel(new PlagueFactory());
        panel.display();
    }
}