package bg.tu_varna.f24621658.sit.commands;
/**
 * Помощен клас с общи методи за обработка на параметри на командите.
 */
public class CommandUtils {
    /**
     * Съединява част от аргументите в един текст, като запазва интервалите между думите.
     * @param args аргументите на командата, въведени от потребителя.
     * @param startIndex индексът на първия аргумент, който участва в съединяването.
     * @return сглобеният текст от избраните аргументи
     */
    public static String joinArguments(String[] args, int startIndex) {
        return joinArguments(args, startIndex, args.length);
    }
    /**
     * Съединява част от аргументите в един текст, като запазва интервалите между думите.
     * @param args аргументите на командата, въведени от потребителя.
     * @param startIndex индексът на първия аргумент, който участва в съединяването.
     * @param endIndexExclusive индексът след последния аргумент, който участва в съединяването.
     * @return сглобеният текст от избраните аргументи
     */
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
    /**
     * Премахва съществуващ запис, намерен по подадените идентифициращи стойности.
     * @param text текстът, от който се премахват ограждащи кавички.
     * @return текстът без ограждащи кавички
     */
    public static String removeQuotes(String text) {
        if (text.startsWith("\"") && text.endsWith("\"") && text.length() >= 2) {
            return text.substring(1, text.length() - 1);
        }

        return text;
    }
}