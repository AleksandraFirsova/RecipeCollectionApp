package pro.sky.recipecollectionapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Ingredient;

import java.util.Collection;

@Service
public interface IngredientService {
    Ingredient addNewIngredient(Ingredient recipe);

    Ingredient getIngredient(int id);

    Collection<Ingredient> getAllIngredients();

    Ingredient editIngredient(int id, Ingredient newIngredient);

    Ingredient removeIngredient(int id, Ingredient removeIngredient);
}
