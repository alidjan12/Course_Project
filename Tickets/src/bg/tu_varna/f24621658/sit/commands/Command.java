package bg.tu_varna.f24621658.sit.commands;

/**
 * Общ договор за всички команди, които могат да се изпълняват от конзолата.
 */
public interface Command {
    void execute(String[] args, CommandContext context);
}
