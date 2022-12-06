package ru.hehnev.tacocloud.models.taco;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;
    private Date createdAt = new Date();

    @NotNull
    @Size(min = 3, message = "имя должно быть не мене 3 букв")
    private String name;

    @NotNull
    @Size(min=1, message="Вы должны выбрать хотя-бы один ингредиент")
    private List<Ingredient> ingredients;

}
