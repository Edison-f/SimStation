package simstation;

import java.io.Serializable;

abstract class Agent implements Runnable, Serializable {
    Simulation world;
    private String name;
    Heading heading;
    int xc, yc;
    boolean suspended, stopped;
    Thread myThread;
    public Agent(String n){
        name = n;
       // heading
        xc = 0;
        yc = 0;
        suspended = false;
        stopped = false;
        // myThread
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

    public void start(){}
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

    public void move(int steps){}
}
