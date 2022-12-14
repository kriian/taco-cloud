package ru.hehnev.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.hehnev.tacocloud.models.taco.Ingredient;
import ru.hehnev.tacocloud.models.taco.Ingredient.Type;
import ru.hehnev.tacocloud.models.taco.Taco;
import ru.hehnev.tacocloud.models.taco.TacoOrder;
import ru.hehnev.tacocloud.repository.IngredientRepository;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type.toString()));
        }

    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, String type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return "/design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
}
