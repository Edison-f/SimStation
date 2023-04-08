package simstation;
import mvc.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SimulationView extends View{
    public SimulationView(Model model){
        super(model);
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        gc.setColor(Color.WHITE);
        Simulation simulation = (Simulation) model;
        Ellipse2D.Double[] shapes = simulation.generateShapes();
        Graphics2D graphics2D = (Graphics2D) gc;
        for (Ellipse2D.Double shape :
                shapes) {
            graphics2D.fill(shape);
        }
        gc.setColor(oldColor);
    }
}
