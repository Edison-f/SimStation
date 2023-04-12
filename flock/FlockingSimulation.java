package flock;

import simstation.*;
import mvc.*;
import java.util.Iterator;

public class FlockingSimulation extends Simulation {
    public void populate(){
        for(int i = 0; i < 15; i++)
            addAgent(new Bird());
    }

    @Override
    public void stats(){

        int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0;

        for(int i = 0; i < agents.size(); i++){
            Bird a = (Bird) agents.get(i);
            int s = a.getSpeed();
            if(s == 1)
                s1++;
            if(s == 2)
                s2++;
            if(s == 3)
                s3++;
            if(s == 4)
                s4++;
            if(s == 5)
                s5++;
        }

        Utilities.inform(new String[] {
                "# of birds at speed 1: " + s1,
                "# of birds at speed 2: " + s2,
                "# of birds at speed 3: " + s3,
                "# of birds at speed 4: " + s4,
                "# of birds at speed 5: " + s5
        }        );
    }

    public static void main(String[] args){
        AppPanel panel = new SimulationPanel(new FlockingFactory());
        panel.display();
    }
}
