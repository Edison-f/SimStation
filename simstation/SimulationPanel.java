package simstation;
import mvc.*;

import javax.swing.*;

public class SimulationPanel extends AppPanel {

    public SimulationPanel(SimulationFactory factory) {
        super(factory);
        String[] buttonNames = new String[] {"Start", "Suspend", "Resume", "Stop", "Stats"};
        for (String s :
                buttonNames) {
            JButton temp = new JButton(s);
            temp.addActionListener(this);
            controlPanel.add(temp);
        }
    }

}
