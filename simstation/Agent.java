package simstation;

<<<<<<< HEAD
import jdk.jshell.execution.Util;
import mvc.Utilities;

import java.io.Serializable;

abstract public class Agent implements Serializable, Runnable {

    private String name;
    public Heading heading;
    private int xc;
    private int yc;
    private boolean suspended;
    private boolean stopped;
    private Thread myThread;

    public Agent() {
        suspended = false;
        stopped = false;
    }

    @Override
    public void run() {

    }

    public void start() {
        heading = Heading.random();
        xc = Utilities.rng.nextInt(999); // Replace with actual window sizes
        yc = Utilities.rng.nextInt(999);
        run();
    }

    public void suspend() {

    }

    public void resume() {

    }

    public void stop() {

    }

    abstract public void update();

    public void move(int steps) {

=======
import java.io.Serializable;

public abstract class Agent implements Runnable, Serializable {
    private Simulation world;
    private String name;
    protected Heading heading;
    int xc, yc;
    boolean suspended, stopped;
    Thread myThread;
    public Agent(){
        name = " ";
        heading = heading.NORTH;
        xc = 0;
        yc = 0;
        suspended = false;
        stopped = false;
        myThread = null;
    }

    public void setSimulation(Simulation s){
        world = s;
    }
    public synchronized void stop() { stopped = true; }
    public synchronized boolean isStopped() { return stopped; }
    public synchronized void suspend() { suspended = true; }
    public synchronized boolean isSuspended() { return suspended;  }
    public synchronized void resume() { notify(); }

    private synchronized void checkSuspended(){
        try{
            while(stopped == false && suspended == true){
                wait();
                suspended = false;
            }
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    // Needs to be completed
    public void start(){}
    // Double check
    public void run(){
        myThread = Thread.currentThread();
        while(isStopped() == false){
            try{
                update();
                Thread.sleep(20);
                checkSuspended();
            } catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public abstract void update();

    public Heading getHeading() {
        return heading;
    }

    public int getXc() {
        return xc;
    }

    public int getYc() {
        return yc;
    }

    public void move(int steps){
        switch (heading){
            // if north subtract y position
            case NORTH:{
                yc -= steps;
                break;
            }
            // if south add y position
            case SOUTH:{
                yc += steps;
                break;
            }
            // if west subtract x position
            case WEST:{
                xc -= steps;
                break;
            }
            // if east add x position
            case EAST:{
                xc += steps;
                break;
            }
            // if northeast, add x position (east) & subtract y (north)
            case NORTHEAST:{
                xc += steps;
                yc -= steps;
                break;
            }
            // if northwest, sub x position (west) & subtract y position (north)
            case NORTHWEST:{
                xc -= steps;
                yc -= steps;
                break;
            }
            // if southeast, add x position (east) & add y position (south)
            case SOUTHEAST:{
                xc += steps;
                yc += steps;
                break;
            }
            // if southwest, subtract x position (west) & add y position (south)
            case SOUTHWEST:{
                xc -= steps;
                yc += steps;
                break;
            }
        }
>>>>>>> master
    }
}
