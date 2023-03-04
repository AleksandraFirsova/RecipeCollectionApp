package pro.sky.recipecollectionapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;
import pro.sky.recipecollectionapp.services.FileService;
import pro.sky.recipecollectionapp.services.RecipeService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Service
public class RecipeImpl implements RecipeService {
    HashMap<Integer, Recipe> recipeMap = new HashMap<>();

    @Value("${name.of.recipe.file}")
    private String recipeFileName;

    private final FileService fileService;

    public RecipeImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        int id = Counter.getId();
        recipeMap.put(id, recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Recipe getRecipe(int id) {
        return recipeMap.get(id);
    }

    @Override
    public Collection<Recipe> getAllRecipes() {
        return recipeMap.values();
    }

    @Override
    public Recipe editRecipe(int id, Recipe newRecipe) {
        recipeMap.put(id, newRecipe);
        saveToFile();
        return newRecipe;
    }

    @Override
    public Recipe removeRecipe(int id, Recipe removeRecipe) {
        recipeMap.remove(id, removeRecipe);
        return removeRecipe;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json, recipeFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDataFile() {
        return recipeFileName;
    }

    private void readFromFile() {
        String json = fileService.readFromFile(recipeFileName);
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
