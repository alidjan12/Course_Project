package bg.tu_varna.f24621658.sit.entity.keys;

import java.time.LocalDate;
import java.util.Objects;

public class EventKey {
    private final String name;
    private final LocalDate date;

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

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventKey eventKey)) return false;
        return Objects.equals(name, eventKey.name) &&
                Objects.equals(date, eventKey.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
