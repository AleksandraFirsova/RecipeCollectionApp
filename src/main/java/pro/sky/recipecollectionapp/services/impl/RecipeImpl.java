package pro.sky.recipecollectionapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;
import pro.sky.recipecollectionapp.services.RecipeService;

import java.util.HashMap;

@Service
public class RecipeImpl implements RecipeService {
    HashMap<Integer, Recipe> recipeMap = new HashMap<>();

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        int id = Counter.GetId();
        recipeMap.put(id, recipe);
        return recipe;
    }

    @Override
    public Recipe getNewRecipe(int id) {
        return recipeMap.get(id);
    }
}
