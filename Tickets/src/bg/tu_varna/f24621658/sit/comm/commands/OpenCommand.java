package bg.tu_varna.f24621658.sit.comm.commands;

import bg.tu_varna.f24621658.sit.comm.core.FileManager;

public class OpenCommand implements Command {
    private FileManager fileManager;

    public OpenCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {

        if(args.length < 2){
            System.out.println("Usage: open <file>");
            return;
        }

        fileManager.open(args[1]);
    }
}
