package ru.hehnev.tacocloud.repository;

import ru.hehnev.tacocloud.models.taco.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
