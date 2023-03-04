package pro.sky.recipecollectionapp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipecollectionapp.services.FileService;
import pro.sky.recipecollectionapp.services.impl.IngredientImpl;
import pro.sky.recipecollectionapp.services.impl.RecipeImpl;

import java.io.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Operations with files")
public class FilesController {

    private final FileService fileService;
    private final IngredientImpl ingredient;
    private final RecipeImpl recipe;

    @GetMapping("/recipe/export")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = fileService.getDataFile(recipe.getDataFile());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
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
