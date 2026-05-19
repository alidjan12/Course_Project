package bg.tu_varna.f24621658.sit.app;

import bg.tu_varna.f24621658.sit.core.Engine;

/**
 * Начална точка на приложението за управление на билети.
 */
public class Main {
    /**
     * Стартира приложението.
     * @param args аргументите на командата, въведени от потребителя.
     */
         public static void main(String[] args) {
            Engine engine = new Engine();
            engine.start();
        }
}