package pro.sky.recipecollectionapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ingredient {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive
    private int count;
    private String measureUnit;
}
