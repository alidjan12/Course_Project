package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;
import bg.tu_varna.f24621658.sit.entity.contracts.InformationSystemCommands;

public class AddEventCommand implements Command {
    private InformationSystemCommands command;

    public AddEventCommand() {
       // this.command = new EventRepository();
    }

    @Override
    public void execute(String[] args, CommandContext context) {
       //command.addEvent();
    }
}
