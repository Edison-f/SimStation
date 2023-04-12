package flock;

import mvc.Utilities;
import simstation.Agent;
import simstation.Heading;

import java.awt.*;

public class Bird extends Agent {
    int speed;
    public Bird(){
        super(Color.white);
        heading = Heading.random();
        // Speed set from 1 to 5
        speed = Utilities.rng.nextInt(5) + 1;
    }

    public int getSpeed() {
        return speed;
    }

    public void update(){
        Bird a = (Bird) this.world.getNeighbor(this, 1);
        if(a != null){
            this.speed = a.getSpeed();
            this.heading = a.getHeading();
            move(this.speed);
        }
        else{
           move(this.speed);
        }

    }

}
