package simstation;
import mvc.*;

import java.awt.*;
import java.util.Iterator;

public class SimulationView extends View{
    public SimulationView(Model model){
        super(model);
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        gc.setColor(Color.WHITE);
        Simulation simulation = (Simulation) model;

        Iterator<Agent> a = simulation.iterator();
        Agent index;
        if(a.hasNext()){
            index = a.next();
            gc.fillOval(index.xc, index.yc, 10, 10);
        }
        while(a.hasNext()){
            index = a.next();
            gc.fillOval(index.xc, index.yc, 10, 10);
        }
        gc.setColor(oldColor);
    }
}
