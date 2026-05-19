package bg.tu_varna.f24621658.sit.entity;

import java.time.format.DateTimeFormatter;
import java.util.UUID;
/**
 * Генерира уникални кодове за закупени билети.
 */
public class GenerateCode {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Генерира код чрез нормализирано име на представление, дата, номер на зала, ред, място и случаен UUID фрагмент.
     * @param event представлението, което се записва, проверява или използва за извеждане.
     * @param row номерът на реда в залата.
     * @param seat номерът на мястото в реда.
     * @return уникалният код на закупения билет
     */
    public String generateCode(Event event, int row,int seat){
        // Премахва символите, които не са букви или цифри
        String eventName = event.getName()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();

        if (eventName.length() > 4) {
            eventName = eventName.substring(0, 4);
        }
        // Генерира случаен шестсимволен фрагмент
        String randomPart = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();

        StringBuilder sb = new StringBuilder();

        sb.append(eventName);
        sb.append("-");
        sb.append(event.getDate().format(FORMATTER));
        sb.append("-");
        sb.append("H").append(event.getHall().getHallNumber());
        sb.append("-");
        sb.append("R").append(row);
        sb.append("-");
        sb.append("S").append(seat);
        sb.append("-");
        sb.append(randomPart);

        return sb.toString();
    }
}
