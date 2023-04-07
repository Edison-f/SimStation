package simstation;

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

    }
}
