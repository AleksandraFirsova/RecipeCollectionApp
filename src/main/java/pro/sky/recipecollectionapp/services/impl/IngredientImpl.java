package pro.sky.recipecollectionapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipecollectionapp.models.Ingredient;
import pro.sky.recipecollectionapp.services.IngredientService;

import java.util.Collection;
import java.util.HashMap;

@Service
public class IngredientImpl implements IngredientService {
    HashMap<Integer, Ingredient> ingredientMap = new HashMap<>();

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        int id = Counter.getId();
        ingredientMap.put(id, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredientMap.get(id);
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredientMap.values();
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient newIngredient) {
        ingredientMap.put(id, newIngredient);
        return newIngredient;
    }

    @Override
    public Ingredient removeIngredient(int id, Ingredient removeIngredient) {
        ingredientMap.remove(id, removeIngredient);
        return removeIngredient;
    }
}
