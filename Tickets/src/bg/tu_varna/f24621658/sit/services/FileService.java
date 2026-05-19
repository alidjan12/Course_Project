package bg.tu_varna.f24621658.sit.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Сервиз за работа с текстов файл.
 * Пази пътя до текущо отворения файл, неговото съдържание
 * и информация дали в момента има отворен файл.
 */
public class FileService {
    private String currentFilePath;
    private String content;
    private boolean fileOpened;

    /**
     * Създава FileService без отворен файл.
     * Началното съдържание е празен текст.
     */
    public FileService() {
        this.currentFilePath = null;
        this.content = "";
        this.fileOpened = false;
    }

    /**
     * Отваря файл по подаден път.
     * Ако файлът не съществува, го създава празен.
     * Ако съществува, зарежда съдържанието му в паметта.
     *
     * @param filePath пътят до файла, който трябва да бъде отворен.
     */
    public void open(String filePath) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                content = "";
            } else {
                content = Files.readString(file.toPath());
            }

            currentFilePath = filePath;
            fileOpened = true;

            System.out.println("Успешно отворен файл: " + file.getName());
        } catch (IOException e) {
            currentFilePath = null;
            content = "";
            fileOpened = false;
            throw new IllegalStateException("Файлът не може да бъде отворен.", e);
        }
    }

    /**
     * Затваря текущо отворения файл.
     * Изчиства запазения път, съдържанието и отбелязва,
     * че вече няма активен файл.
     */
    public void close() {
        ensureFileIsOpened();

        currentFilePath = null;
        content = "";
        fileOpened = false;

        System.out.println("Успешно затворен файл.");
    }

    /**
     * Записва текущото съдържание в отворения файл.
     * Методът работи само ако предварително има отворен файл.
     */
    public void save() {
        ensureFileIsOpened();

        try (FileWriter writer = new FileWriter(currentFilePath)) {
            writer.write(content);
            System.out.println("Успешно запазен файл: " + new File(currentFilePath).getName());
        } catch (IOException e) {
            System.out.println("Грешка: Файлът не може да бъде запазен.");        }
    }

    /**
     * Записва текущото съдържание в нов файл.
     * Не сменя текущо отворения файл, а само копира съдържанието
     * към подадения нов път.
     *
     * @param newFilePath пътят до файла, в който ще се запише съдържанието.
     */
    public void saveAs(String newFilePath) {
        ensureFileIsOpened();

        try (FileWriter writer = new FileWriter(newFilePath)) {
            writer.write(content);
            System.out.println("Успешно запазен файл: " + new File(newFilePath).getName());
        } catch (IOException e) {
            System.out.println("Грешка: Файлът не може да бъде запазен.");        }
    }

    /**
     * Проверява дали има отворен файл.
     *
     * @return true, ако има активен файл; иначе false.
     */
    public boolean hasOpenedFile() {
        return fileOpened;
    }

    /**
     * Връща съдържанието, заредено от текущо отворения файл.
     *
     * @return текстовото съдържание, което се пази в паметта.
     */
    public String getContent() {
        return content;
    }

    /**
     * Заменя съдържанието, което ще бъде записано във файла.
     * Методът изисква да има отворен файл.
     *
     * @param content новото съдържание за запис.
     */

    public void setContent(String content) {
        ensureFileIsOpened();
        this.content = content;
    }

    /**
     * Проверява дали има отворен файл преди операция с файл.
     * Ако няма отворен файл, прекъсва изпълнението с грешка.
     */
    private void ensureFileIsOpened() {
        if (!fileOpened) {
            throw new IllegalStateException("Няма отворен файл.");        }
    }
}