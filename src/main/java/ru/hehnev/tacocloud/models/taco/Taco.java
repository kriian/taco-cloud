package ru.hehnev.tacocloud.models.taco;

import lombok.Data;

import java.util.List;

@Data
public class Taco {

    private String name;
    private List<Ingredient> ingredients;

}
