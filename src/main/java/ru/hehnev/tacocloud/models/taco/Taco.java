package ru.hehnev.tacocloud.models.taco;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "taco")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "name")
    @NotNull
    @Size(min = 3, message = "имя должно быть не мене 3 букв")
    private String name;

    @Size(min=1, message="Вы должны выбрать хотя-бы один ингредиент")
    @ManyToMany
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient taco) {
        this.ingredients.add(new IngredientRef(taco.getId()));
    }

//    public void addIngredient(Ingredient ingredient) {
//        this.ingredients.add(ingredient);
//    }
}
