package pro.sky.recipecollectionapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Recipe;

@Service
public interface RecipeService {
    Recipe addNewRecipe(Recipe recipe);
    Recipe getNewRecipe(int id);
}
