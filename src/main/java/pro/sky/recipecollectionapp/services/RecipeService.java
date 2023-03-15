package pro.sky.recipecollectionapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;

import java.nio.file.Path;
import java.util.HashMap;

@Service
public interface RecipeService {
    Recipe addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    HashMap<Integer, Recipe> getRecipeMap();

    Recipe editRecipe(int id, Recipe newRecipe);

    Recipe removeRecipe(int id, Recipe removeRecipe);

    HashMap<Integer, Recipe> getAllRecipes();

    Path getTxtFile(String fileName);
}
