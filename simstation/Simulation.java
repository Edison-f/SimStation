package simstation;

import java.awt.geom.Ellipse2D;
import java.util.*;
import mvc.*;

public class Simulation extends Model {

    private Timer timer;
    private int clock;
    List<Agent> agents;

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
       if( distance(a, b) <= radius)
           return b;
       // Else find another Agent that is close
       while(i != start){
           b = agents.get(i);
           if(distance(a,b) <= radius)
               return b;
           // if index reaches the end, go back to start
           if(i == (agents.size() - 1))
               i = 0;
           // else keep incrementing
           else
               i++;
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

    // etc.

}