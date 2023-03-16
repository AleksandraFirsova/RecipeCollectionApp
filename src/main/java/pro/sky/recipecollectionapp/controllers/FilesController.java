package pro.sky.recipecollectionapp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipecollectionapp.services.FileService;
import pro.sky.recipecollectionapp.services.impl.IngredientImpl;
import pro.sky.recipecollectionapp.services.impl.RecipeImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Operations with files")
public class FilesController {

    private final FileService fileService;
    private final IngredientImpl ingredient;
    private final RecipeImpl recipe;

    @GetMapping("/recipe/export/json")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = fileService.getDataFile(recipe.getDataFile());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @GetMapping("/recipe/export/txt")
    public ResponseEntity<Object> downloadRecipeTxtFile(@RequestParam String fileName) {
        if (recipe.getTxtFile(fileName) == null) {
            return ResponseEntity.notFound().build();
        }
        Path path = recipe.getTxtFile(fileName);
        try {
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.txt\"")
                    .contentLength(Files.size(path))
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @PostMapping(value = "/ingredient/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile file) {
        fileService.cleanFile(ingredient.getDataFile());
        File dataFile = fileService.getDataFile(ingredient.getDataFile());
        try {
            FileOutputStream fos = new FileOutputStream(dataFile);
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/recipe/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        fileService.cleanFile(recipe.getDataFile());
        File dataFile = fileService.getDataFile(recipe.getDataFile());
        try {
            FileOutputStream fos = new FileOutputStream(dataFile);
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
