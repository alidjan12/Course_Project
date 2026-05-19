package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Hall;

import java.util.*;

/**
 * Хранилище за предварително дефинираните зали в системата.
 * Пази залите по техния номер и позволява търсене и извеждане
 * на всички налични зали.
 */
public class HallRepository {
    private Map<Integer, Hall> halls;

    /**
     * Създава хранилище с предварително зададени зали.
     * В конструктора се добавят трите налични зали с техните редове
     * и брой места на всеки ред.
     */
    public HallRepository() {
        halls = new HashMap<>();

        halls.put(1, new Hall(1, 5, new int[]{10, 10, 8, 8, 6}));
        halls.put(2, new Hall(2, 4, new int[]{12, 12, 10, 10}));
        halls.put(3, new Hall(3, 3, new int[]{6, 6, 6}));
    }

    /**
     * Намира зала по нейния номер.
     *
     * @param hallNumber номерът на търсената зала.
     * @return намерената зала или null, ако няма зала с такъв номер.
     */
    public Hall findByNumber(int hallNumber) {
        return halls.get(hallNumber);
    }

    /**
     * Връща всички налични зали, сортирани по номер.
     *
     * @return списък със залите, подредени по техния номер.
     */
    public List<Hall> getHalls() {
        List<Hall> result = new ArrayList<>(halls.values());
        result.sort(Comparator.comparingInt(Hall::getHallNumber));
        return result;
    }
}
