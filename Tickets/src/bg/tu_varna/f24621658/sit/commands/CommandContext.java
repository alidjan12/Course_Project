package bg.tu_varna.f24621658.sit.commands;

import bg.tu_varna.f24621658.sit.print.InformationPrinter;
import bg.tu_varna.f24621658.sit.services.FileService;
import bg.tu_varna.f24621658.sit.services.TicketSystem;

/**
 * Съхранява общите зависимости, необходими за изпълнение на командите.
 */
public class CommandContext {
    private final FileService fileService;
    private final TicketSystem ticketSystem;
    private final InformationPrinter printer;
    private boolean running;

    /**
     * Създава нов обект от тип CommandContext.
     * @param fileService услугата за отваряне, четене и запис на файлове.
     * @param ticketSystem услугата с бизнес логиката за представления и билети.
     * @param printer принтерът, чрез който се извеждат съобщения към потребителя.
     */
    public CommandContext(FileService fileService, TicketSystem ticketSystem, InformationPrinter printer) {
        this.fileService = fileService;
        this.ticketSystem = ticketSystem;
        this.printer = printer;
        this.running = true;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public TicketSystem getTicketSystem() {
        return ticketSystem;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public FileService getFileService() {
        return fileService;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public InformationPrinter getPrinter() {
        return printer;
    }
    /**
     * Проверява дали основният цикъл на приложението трябва да продължи да работи.
     * @return true, ако приложението още трябва да чете команди
     */
    public boolean isRunning() {
        return running;
    }
    /**
     * Маркира приложението като спряно, за да приключи четенето на команди.
     */
    public void stop() {
        this.running = false;
    }
}
