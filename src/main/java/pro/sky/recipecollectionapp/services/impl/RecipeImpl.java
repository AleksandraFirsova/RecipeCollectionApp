package pro.sky.recipecollectionapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;
import pro.sky.recipecollectionapp.services.RecipeService;

import java.util.Collection;
import java.util.HashMap;

@Service
public class RecipeImpl implements RecipeService {
    HashMap<Integer, Recipe> recipeMap = new HashMap<>();

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        int id = Counter.getId();
        recipeMap.put(id, recipe);
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
        return newRecipe;
    }

    @Override
    public Recipe removeRecipe(int id, Recipe removeRecipe) {
        recipeMap.remove(id, removeRecipe);
        return removeRecipe;
    }
}
