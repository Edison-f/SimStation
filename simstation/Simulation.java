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

    public Ellipse2D.Double[] generateShapes() {
        Ellipse2D.Double[] result = new Ellipse2D.Double[agents.size()];
        for (int i = 0; i < result.length; i++) {
            Agent curr = agents.get(i);
            result[i] = new Ellipse2D.Double(curr.xc, curr.yc, 10, 10);
        }
        return result;
    }

    public Agent getNeighbor(Agent a, double radius){
        // placeholder
        return a;
    }
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