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
import pro.sky.recipecollectionapp.models.Recipe;
import pro.sky.recipecollectionapp.services.RecipeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Operations CRUD")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Adding new recipe")
    @ApiResponse(responseCode = "200", description = "Recipe added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))})
    public Recipe addNewRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting recipe by id", description = "Search with parameter")
    @ApiResponse(responseCode = "200", description = "Recipe received", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))})
    public Recipe getRecipe(@PathVariable int id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping
    @Operation(summary = "Getting all recipes", description = "Search without parameters")
    @ApiResponse(responseCode = "200", description = "All recipes received", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))})
    public HashMap<Integer, Recipe> getAllRecipes() {
        return recipeService.getRecipeMap();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Changing recipe by id", description = "Search with parameter")
    @ApiResponse(responseCode = "200", description = "Recipe changed", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))})
    public ResponseEntity<Recipe> putRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe) {
        recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.editRecipe(id, recipe));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove recipe by id", description = "Search with parameter")
    @ApiResponse(responseCode = "200", description = "Recipe removed", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))})
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe) {
        recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.removeRecipe(id, recipe));
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
