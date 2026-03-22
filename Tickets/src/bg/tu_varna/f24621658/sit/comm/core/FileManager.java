package bg.tu_varna.f24621658.sit.comm.core;

public class FileManager {
    private String currentFile;

    public void open(String path){
        currentFile = path;
        System.out.println("Successfully opened " + currentFile);
    }

    public void close(){
        System.out.println("Successfully closed " + currentFile);
        currentFile = null;
    }
}
