package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.services.FileService;
import bg.tu_varna.f24621658.sit.services.TicketSystem;

public class CommandContext {
    private final FileService fileService;
    private final TicketSystem ticketSystem;
    private final InformationPrinter printer;
    private boolean running;

    public CommandContext(FileService fileService, TicketSystem ticketSystem, InformationPrinter printer) {
        this.fileService = fileService;
        this.ticketSystem = ticketSystem;
        this.printer = printer;
        this.running = true;
    }

    public TicketSystem getTicketSystem() {
        return ticketSystem;
    }

    public FileService getFileService() {
        return fileService;
    }

    public InformationPrinter getPrinter() {
        return printer;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}
