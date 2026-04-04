package bg.tu_varna.f24621658.sit.commands.ipml;

import bg.tu_varna.f24621658.sit.commands.Command;
import bg.tu_varna.f24621658.sit.commands.CommandContext;

public class OpenCommand implements Command {
    @Override
    public void execute(String[] args, CommandContext context) {
        if (args.length < 2) {
            System.out.println("Error: File path is required.");
            return;
        }

        String filePath = joinArguments(args, 1);
        filePath = removeQuotes(filePath);

        context.getFileService().open(filePath);
    }

    private String joinArguments(String[] args, int startIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String removeQuotes(String text) {
        if (text.startsWith("\"") && text.endsWith("\"") && text.length() >= 2) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }
}