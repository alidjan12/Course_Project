package bg.tu_varna.f24621658.sit.repository;

import bg.tu_varna.f24621658.sit.entity.Hall;

import java.util.HashMap;
import java.util.Map;

public class HallRepository {
    private Map<Integer, Hall> halls;

    public HallRepository() {
        halls = new HashMap<>();

        halls.put(1, new Hall(1, 5, new int[]{10, 10, 8, 8, 6}));
        halls.put(2, new Hall(2, 4, new int[]{12, 12, 10, 10}));
        halls.put(3, new Hall(3, 3, new int[]{6, 6, 6}));
    }

    public Hall findByNumber(int hallNumber) {
        return halls.get(hallNumber);
    }
}
