package bg.tu_varna.f24621658.sit.app;

import bg.tu_varna.f24621658.sit.core.Engine;
import bg.tu_varna.f24621658.sit.entity.Hall;

public class Main {
         static void main(String[] args) {
             Hall hall1 = new Hall(1,4,new int[]{4,6,5,7});
             Hall hall2 = new Hall(2,6,new int[]{4,9,7,8,5,6});
             Hall hall3 = new Hall(3,7,new int[]{9,6,3,6,2,4,8});

             hall1.printHall();

            Engine engine = new Engine();
            engine.start();
        }
}