package pro.sky.recipecollectionapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Ingredient;

@Service
public interface IngredientService {
    Ingredient addNewIngredient(Ingredient recipe);
    Ingredient getNewIngredient(int id);
}
