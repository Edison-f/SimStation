package simstation;

import mvc.Utilities;
import java.io.Serializable;
import java.awt.Color;

import static mvc.Utilities.rng;

public abstract class Agent implements Runnable, Serializable {
    private Simulation world;
    private String name;
    protected Heading heading;
    protected int xc, yc;
    boolean suspended, stopped;
    Color color;
    // transient represents don't save this, having errors within threads
    transient Thread myThread;

    public Agent(){
        name = " ";
        heading = heading.random();
        xc = rng.nextInt(world.size);
        yc = rng.nextInt(world.size);
        suspended = false;
        stopped = false;
        myThread = null;
        color = null;
    }
        public Agent(Color c){
        name = " ";
        heading = heading.random();
        xc = rng.nextInt(world.size);
        yc = rng.nextInt(world.size);
        suspended = false;
        stopped = false;
        myThread = null;
        color = c;
    }
    public void setSimulation(Simulation s){
        world = s;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
           Utilities.inform(e.getMessage());
        }
    }
    public synchronized void join() {
        try {
            if (myThread != null) myThread.join();
        } catch(InterruptedException e) {
            Utilities.inform(e.getMessage());
        }
    }

    public void start() {
        //heading = Heading.random();
        myThread = new Thread(this);
        myThread.start();
        //xc = rng.nextInt(250); // Replace with actual window sizes
        //yc = rng.nextInt(250);
        //run();
    }
    
    // Double check
    public void run(){
        myThread = Thread.currentThread();
        while(isStopped() == false){
            try{
                checkSuspended();
                update();
                Thread.sleep(30);
            } catch(InterruptedException e){
                Utilities.inform(e.getMessage());
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
        while(xc < 0)
            xc += world.size;
        while(yc < 0)
            yc += world.size;
        if(xc > world.size)
            xc = xc % world.size;
        if(yc > world.size)
            yc = yc % world.size;
        world.changed();
    }
}
