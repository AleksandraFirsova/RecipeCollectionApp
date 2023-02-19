package pro.sky.recipecollectionapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipecollectionapp.models.Ingredient;
import pro.sky.recipecollectionapp.models.Recipe;
import pro.sky.recipecollectionapp.services.IngredientService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Tag(name = "Ingredient", description = "Operations CRUD")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    @Operation(summary = "Adding new ingredient")
    @ApiResponse(responseCode = "200", description = "Ingredient added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))})
    public Ingredient addNewIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientService.addNewIngredient(ingredient);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting ingredient by id")
    @ApiResponse(responseCode = "200", description = "Ingredient received", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))})
    public Ingredient getIngredient(@PathVariable int id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping
    @Operation(summary = "Getting all ingredients")
    @ApiResponse(responseCode = "200", description = "All ingredients received", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))})
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Changing ingredient by id")
    @ApiResponse(responseCode = "200", description = "Ingredient changed", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))})
    public ResponseEntity<Ingredient> putIngredient(@PathVariable int id, @Valid @RequestBody Ingredient ingredient) {
        ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientService.editIngredient(id, ingredient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Ingredient removed")
    @ApiResponse(responseCode = "200", description = "Ingredient removed", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredient.class))})
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
