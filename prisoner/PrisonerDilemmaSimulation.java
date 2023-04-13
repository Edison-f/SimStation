package prisoner;

import mvc.AppPanel;
import mvc.Model;
import simstation.*;
import mvc.Utilities;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PrisonerDilemmaSimulation extends Simulation {
    List<Prisoner> cooperators;
    List<Prisoner> randoms;
    List<Prisoner> cheaters;
    List<Prisoner> t4ters;

    public static void main(String[] args) {
        AppPanel panel = new SimulationPanel(new PrisonerDilemmaFactory());
        panel.display();
    }

    @Override
    public void populate() {
        cooperators = new LinkedList<>();
        randoms = new LinkedList<>();
        cheaters = new LinkedList<>();
        t4ters = new LinkedList<>();
        createPrisoners(10, cooperators, Prisoner.PrisonerTypes.COOPERATE);
        createPrisoners(10, randoms, Prisoner.PrisonerTypes.RANDOMLY_COOPERATE);
        createPrisoners(10, cheaters, Prisoner.PrisonerTypes.CHEAT);
        createPrisoners(10, t4ters, Prisoner.PrisonerTypes.TIT4TAT);
    }

    private void createPrisoners(int n, List<Prisoner> list, Prisoner.PrisonerTypes type) {
        Prisoner temp;
        for (int i = 0; i < n; i++) {
            temp = new Prisoner(type);
            list.add(temp);
            addAgent(temp);
        }
    }

    @Override
    public void stats() {
        Utilities.inform(new String[]{  "Cooperators: " + average(cooperators),
                                        "Random Cooperators: " + average(randoms),
                                        "Cheaters: " + average(cheaters),
                                        "'Tit for tat'ers: " + average(t4ters)
                                        });
    }

    private double average(List<Prisoner> list) {
        double result = 0;
        for (Prisoner p :
                list) {
            result += ((double) (p.getFitness())) / list.size();
        }
        return result;
    }
}

class PrisonerDilemmaFactory extends SimulationFactory{
    public Model makeModel() { return new PrisonerDilemmaSimulation(); }
    public String getTitle() { return "Prisoner's Dilemma Tournament";}
}

class Prisoner extends Agent {

    private int fitness;
    private boolean partnerCheated = false;
    private Strategy strategy;

    public Prisoner(PrisonerTypes types) {
        super();
        switch (types) {
            case COOPERATE -> {
                strategy = new Cooperate();
                setColor(Color.green);
            }
            case RANDOMLY_COOPERATE -> {
                strategy = new RandomlyCooperate();
                setColor(Color.yellow);
            }
            case CHEAT -> {
                strategy = new Cheat();
                setColor(Color.red);
            }
            case TIT4TAT -> {
                strategy = new Tit4Tat();
                setColor(Color.blue);
            }
        }
    }

    public boolean cooperate() {
        return strategy.cooperate();
    }

    @Override
    public void update() {
        heading = Heading.random();
        move(Utilities.rng.nextInt(0, 10));
        Prisoner competitor = (Prisoner) world.getNeighbor(this, 10);
        if(competitor == null) return; // No valid competitors found
        boolean thisCoop = cooperate();
        boolean compCoop = competitor.cooperate();
        if (thisCoop) {
            competitor.partnerCheated = false;
            if (compCoop) { // Both cooperate
                updateFitness(3);
                competitor.updateFitness(3);
                partnerCheated = false;
            } else { // This cooperated, competitor cheated
                competitor.updateFitness(5);
                partnerCheated = true;
            }
        } else {
            competitor.partnerCheated = true;
            if (compCoop) { // This cheated, competitor cooperated
                updateFitness(5);
                partnerCheated = false;
            } else { // Both cheated
                updateFitness(1);
                competitor.updateFitness(1);
                partnerCheated = true;
            }
        }
    }

    public void updateFitness(int amt) {
        fitness += amt;
    }

    public int getFitness() {
        return fitness;
    }

    public enum PrisonerTypes {
        COOPERATE,
        RANDOMLY_COOPERATE,
        CHEAT,
        TIT4TAT
    }

    private abstract static class Strategy {
        abstract public boolean cooperate();
    }

    private static class Cooperate extends Strategy {
        @Override
        public boolean cooperate() {
            return true;
        }
    }

    private static class RandomlyCooperate extends Strategy {
        @Override
        public boolean cooperate() {
            return Utilities.rng.nextInt(0, 100) > 50;
        }
    }

    private static class Cheat extends Strategy {
        @Override
        public boolean cooperate() {
            return false;
        }
    }

    private class Tit4Tat extends Strategy {
        @Override
        public boolean cooperate() {
            return !partnerCheated;
        }
    }
}