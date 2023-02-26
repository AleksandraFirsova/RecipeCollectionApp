package pro.sky.recipecollectionapp.services;

public interface FileService {
    boolean saveToFile(String json, String fileName);
    String readFromFile(String fileName);
    boolean cleanFile(String fileName);
}
