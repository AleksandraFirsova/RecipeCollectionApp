package pro.sky.recipecollectionapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Recipe {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive
    private int timeForPreparing;
    @NotEmpty
    List<Ingredient> ingredients;
    @NotEmpty
    List<String> cookingInstructions;
}
