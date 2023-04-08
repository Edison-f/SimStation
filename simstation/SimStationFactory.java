package simstation;
import mvc.*;
public class SimStationFactory implements AppFactory{
    public Model makeModel(){ return new Simulation(); }
    public String[] getEditCommands() {
        return new String[] { "Resume", "Start", "Stop", "Stats", "Suspend"};
    }
    public Command makeEditCommand(Model model, String type, Object source) {
        if(type == "Resume")
            return new ResumeCommand(model);
        if(type == "Start")
            return new StartCommand(model);
        if(type == "Stats")
            return new StatsCommand(model);
        if(type == "Stop")
            return new StopCommand(model);
        if(type == "Suspend")
            return new SuspendCommand(model);
        return null;
    }
    public View makeView(Model model) {
        return new SimulationView(model);
    }
    public String getTitle() {
        return "SimStation";
    }
    public String[] getHelp() {
        return new String[] {"Start: Start the agents",
                            "Stop: Stop the agents",
                            "Suspend: Suspend the agents but doesn't stop them",
                            "Stats: See that current details",
                           "Resume: Resume the agents if suspended"};
    }
    public String about() {
        return "SimStation Team 5";
    }
}

