package simstation;

import mvc.*;

public class ResumeCommand extends Command {
    public ResumeCommand(Model model){ super(model); }
    @Override
    public void execute() throws Exception {
        Simulation sim = (Simulation)model;
        sim.resume();
    }
}
