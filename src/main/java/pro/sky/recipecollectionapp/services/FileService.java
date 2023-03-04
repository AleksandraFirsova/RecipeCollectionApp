package pro.sky.recipecollectionapp.services;

import java.io.File;

public interface FileService {
    boolean saveToFile(String json, String fileName);
    String readFromFile(String fileName);
    boolean cleanFile(String fileName);

    File getDataFile(String fileName);
}
