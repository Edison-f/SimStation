package simstation;

import java.awt.geom.Ellipse2D;
import java.util.*;
import mvc.*;

public class Simulation extends Model {

    transient private Timer timer;
    private int clock;
    protected List<Agent> agents;

    public static int size = 240;

    public Simulation(){
        agents = new LinkedList<Agent>();
        populate();
        startTimer();
        clock = 0;
    }
    public void addAgent(Agent a){
        agents.add(a);
        a.setSimulation(this);
    }
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ClockUpdater(), 1000, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }
    public void start(){
        for(Agent a: agents)
            a.start();
    }
    public void resume(){
        for(Agent a: agents)
            a.resume();
    }
    public void suspend(){
        for(Agent a: agents)
            a.suspend();
    }
    public void stop(){
        for(Agent a: agents)
            a.stop();
    }

    public void stats(){
        int num_agents = agents.size();
        Utilities.inform(new String[]{"Agents: "+ String.valueOf(num_agents), "Clock: "+ String.valueOf(clock)});
    }
    public Iterator<Agent> iterator() { return agents.iterator();}

    public Agent getNeighbor(Agent a, double radius){
        // 0 to (agents.size() - 1)
        int start = Utilities.rng.nextInt(agents.size());
        int i = start + 1;
        // Get agent at random position in LinkedList
        Agent b = agents.get(start);
        // If Agent b is close to A, return agent b
       if(distance(a, b) <= radius && !a.equals(b))
           return b;
       // Else find another Agent that is close
       while(i != start && i >= 0 && i < agents.size()){
           b = agents.get(i);
           if(distance(a,b) <= radius && !a.equals(b))
               return b;
           i++;
           if(!(i < agents.size())) {
               i = 0;
           }
       }
       // If no such neighbors are close to current agent, return null;
        return null;
    }
    private double distance(Agent a, Agent b){ return Math.sqrt( (b.xc - a.xc)^2 + (b.yc - a.xc)^2); }
    // Empty as dictated from Simstation Assignment Details
    public void populate(){}
    private class ClockUpdater extends TimerTask {
        public void run() {
            clock++;
            //changed();
        }
    }

    public int getClock() {
        return clock;
    }

    // etc.

}