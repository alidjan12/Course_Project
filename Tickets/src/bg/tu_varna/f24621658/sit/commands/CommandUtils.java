package bg.tu_varna.f24621658.sit.commands;

public class CommandUtils {
    public static String joinArguments(String[] args, int startIndex) {
        return joinArguments(args, startIndex, args.length);
    }

    public static String joinArguments(String[] args, int startIndex, int endIndexExclusive) {
        StringBuilder sb = new StringBuilder();

        for (int i = startIndex; i < endIndexExclusive; i++) {
            sb.append(args[i]);

            if (i < endIndexExclusive - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static String removeQuotes(String text) {
        if (text.startsWith("\"") && text.endsWith("\"") && text.length() >= 2) {
            return text.substring(1, text.length() - 1);
        }

        return text;
    }
}