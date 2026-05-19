package bg.tu_varna.f24621658.sit.entity.keys;

import java.time.LocalDate;
import java.util.Objects;
/**
 * Ключ за идентифициране на представление по дата и име.
 */
public class EventKey {
    private final String name;
    private final LocalDate date;
    /**
     * Създава нов обект от тип EventKey.
     * @param name името на представлението.
     * @param date датата на представлението или датата, използвана като филтър.
     */
    public EventKey(String name, LocalDate date) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Невалидно име!");
        }
        if (date == null) {
            throw new IllegalArgumentException("Невалидна дата!");
        }

        this.name = name;
        this.date = date;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public String getName() {
        return name;
    }
    /**
     * Връща текущата стойност на съответното поле от модела.
     * @return текущата съхранена стойност
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     * Стандартна реализация на метода equals.
     * @param o обектът, с който се сравнява текущият ключ.
     * @return true, ако двата ключа имат еднакви име и дата; за hashCode - hash стойност по тези полета
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventKey eventKey)) return false;
        return Objects.equals(name, eventKey.name) &&
                Objects.equals(date, eventKey.date);
    }
    /**
     * Стандартна реализация на метода hashCode.
     * @return true, ако двата ключа имат еднакви име и дата; за hashCode - hash стойност по тези полета
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
