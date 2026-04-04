package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.services.FileService;

public class CommandContext {
    private final FileService fileService;
    private boolean running;

    public CommandContext(FileService fileService) {
        this.fileService = fileService;
        this.running = true;
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
