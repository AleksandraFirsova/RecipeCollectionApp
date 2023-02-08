package pro.sky.recipecollectionapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Ingredient;
import pro.sky.recipecollectionapp.services.IngredientService;

import java.util.HashMap;

@Service
public class IngredientImpl implements IngredientService {
    HashMap<Integer, Ingredient> ingredientMap = new HashMap<>();
    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        int id = Counter.GetId();
        ingredientMap.put(id, ingredient);
        return ingredient;
    }
    @Override
    public Ingredient getNewIngredient(int id) {
        return ingredientMap.get(id);
    }
}
