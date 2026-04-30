package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.services.FileService;
import bg.tu_varna.f24621658.sit.services.TicketSystem;

public class CommandContext {
    private final FileService fileService;
    private final TicketSystem ticketSystem;
    private boolean running;

    public CommandContext(FileService fileService, TicketSystem ticketSystem) {
        this.fileService = fileService;
        this.ticketSystem = ticketSystem;
        this.running = true;
    }

    public TicketSystem getTicketSystem() {
        return ticketSystem;
    }

    public FileService getFileService() {
        return fileService;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}
