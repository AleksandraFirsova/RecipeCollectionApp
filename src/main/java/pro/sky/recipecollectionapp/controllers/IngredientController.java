package pro.sky.recipecollectionapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipecollectionapp.models.Ingredient;
import pro.sky.recipecollectionapp.services.IngredientService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public Ingredient addNewIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientService.addNewIngredient(ingredient);
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> putIngredient(@PathVariable int id, @Valid @RequestBody Ingredient ingredient) {
        ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientService.editIngredient(id, ingredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id, @Valid @RequestBody Ingredient ingredient) {
        ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientService.removeIngredient(id, ingredient));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
