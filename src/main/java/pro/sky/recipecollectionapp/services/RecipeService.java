package pro.sky.recipecollectionapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;

import java.util.Collection;

@Service
public interface RecipeService {
    Recipe addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Collection<Recipe> getAllRecipes();

    Recipe editRecipe(int id, Recipe newRecipe);

    Recipe removeRecipe(int id, Recipe removeRecipe);
}
