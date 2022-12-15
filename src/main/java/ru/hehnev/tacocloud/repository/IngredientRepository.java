package ru.hehnev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hehnev.tacocloud.models.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
