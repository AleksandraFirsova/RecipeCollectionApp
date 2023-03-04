package pro.sky.recipecollectionapp.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Value("${path.to.date.file}")
    private String dataFilePath;

    @Override
    public boolean saveToFile(String json, String fileName) {
        Path path = Path.of(dataFilePath, fileName);
        try {
            cleanFile(fileName);
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(String fileName) {
        Path path = Path.of(dataFilePath, fileName);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanFile(String fileName) {
        try {
            Path path = Path.of(dataFilePath, fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile(String fileName) {
        return new File(dataFilePath + "/" + fileName);
    }
}
