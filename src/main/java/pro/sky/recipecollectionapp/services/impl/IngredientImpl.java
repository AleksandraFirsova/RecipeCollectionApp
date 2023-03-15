package pro.sky.recipecollectionapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.recipecollectionapp.models.Ingredient;
import pro.sky.recipecollectionapp.services.FileService;
import pro.sky.recipecollectionapp.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;

@Service
public class IngredientImpl implements IngredientService {
    final private FileService fileService;
    HashMap<Integer, Ingredient> ingredientMap = new HashMap<>();

    @Value("${name.of.ingredient.file}")
    private String ingredientsFileName;

    public IngredientImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        int id = Counter.getId();
        ingredientMap.put(id, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(int id) {
        if (ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id не найден");
        }
        return ingredientMap.get(id);
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredientMap.values();
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient newIngredient) {
        ingredientMap.put(id, newIngredient);
        saveToFile();
        return newIngredient;
    }

    @Override
    public Ingredient removeIngredient(int id, Ingredient removeIngredient) {
        ingredientMap.remove(id, removeIngredient);
        return removeIngredient;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileService.saveToFile(json, ingredientsFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDataFile() {
        return ingredientsFileName;
    }

    private void readFromFile() {
        String json = fileService.readFromFile(ingredientsFileName);
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
