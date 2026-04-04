package bg.tu_varna.f24621658.sit.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileService {
    private String currentFilePath;
    private String content;
    private boolean fileOpened;

    public FileService() {
        this.currentFilePath = null;
        this.content = "";
        this.fileOpened = false;
    }

    //otvarq fajla ili go suzdava
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

            System.out.println("Successfully opened " + file.getName());
        } catch (IOException e) {
            System.out.println("Error: Could not open file.");
        }
    }

    //zatvarq fajla ako ima otvoren
    public void close() {
        ensureFileIsOpened();

        currentFilePath = null;
        content = "";
        fileOpened = false;

        System.out.println("Successfully closed file.");
    }

    public void save() {
        ensureFileIsOpened();

        try (FileWriter writer = new FileWriter(currentFilePath)) {
            writer.write(content);
            System.out.println("Successfully saved " + new File(currentFilePath).getName());
        } catch (IOException e) {
            System.out.println("Error: Could not save file.");
        }
    }

    public void saveAs(String newFilePath) {
        ensureFileIsOpened();

        try (FileWriter writer = new FileWriter(newFilePath)) {
            writer.write(content);
            System.out.println("Successfully saved " + new File(newFilePath).getName());
        } catch (IOException e) {
            System.out.println("Error: Could not save file.");
        }
    }

    public boolean hasOpenedFile() {
        return fileOpened;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        ensureFileIsOpened();
        this.content = content;
    }

    //proveravq dail ima otvoren fajl
    private void ensureFileIsOpened() {
        if (!fileOpened) {
            throw new IllegalStateException("No file is currently opened.");
        }
    }
}