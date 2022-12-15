package ru.hehnev.tacocloud.models.taco;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Entity(name = "ingredient_ref")
public class IngredientRef {

    @Id
    private final String ingredient;
}
