package bg.tu_varna.f24621658.sit.entity;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GenerateCode {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");


    public String generateCode(Event event, int row,int seat){
        String eventName = event.getName()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();

        if (eventName.length() > 4) {
            eventName = eventName.substring(0, 4);
        }

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
