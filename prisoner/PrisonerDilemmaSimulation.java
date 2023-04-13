package prisoner;

import mvc.AppPanel;
import mvc.Model;
import simstation.*;
import mvc.Utilities;

import java.util.*;

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
        for (int i = 0; i < 10; i++) {
            Prisoner temp = new Prisoner(Prisoner.PrisonerTypes.COOPERATE);
            cooperators.add(temp);
            addAgent(temp);
        }

        randoms = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Prisoner temp = new Prisoner(Prisoner.PrisonerTypes.RANDOMLY_COOPERATE);
            randoms.add(temp);
            addAgent(temp);
        }

        cheaters = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Prisoner temp = new Prisoner(Prisoner.PrisonerTypes.CHEAT);
            cheaters.add(temp);
            addAgent(temp);
        }

        t4ters = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Prisoner temp = new Prisoner(Prisoner.PrisonerTypes.TIT4TAT);
            t4ters.add(temp);
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
            case COOPERATE -> strategy = new Cooperate();
            case RANDOMLY_COOPERATE -> strategy = new RandomlyCooperate();
            case CHEAT -> strategy = new Cheat();
            case TIT4TAT -> strategy = new Tit4Tat();
        }
    }

    public boolean cooperate() {
        return strategy.cooperate();
    }

    @Override
    public void update() {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);
        Prisoner competitor = (Prisoner) world.getNeighbor(this, 10);
        if(competitor == null) return; // No valid competitors found
        boolean thisCoop = cooperate();
        boolean compCoop = competitor.cooperate();
        if (thisCoop) {
            if (compCoop) { // Both cooperate
                updateFitness(3);
                competitor.updateFitness(3);
                partnerCheated = false;
            } else { // This cooperated, competitor cheated
                competitor.updateFitness(5);
                partnerCheated = true;
            }
            competitor.partnerCheated = false;
        } else {
            if (compCoop) { // This cheated, competitor cooperated
                updateFitness(5);
                partnerCheated = false;
            } else { // Both cheated
                updateFitness(1);
                competitor.updateFitness(1);
                partnerCheated = true;
            }
            competitor.partnerCheated = true;
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