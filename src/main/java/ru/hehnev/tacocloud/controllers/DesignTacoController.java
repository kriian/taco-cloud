package ru.hehnev.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.hehnev.tacocloud.models.taco.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ru.hehnev.tacocloud.models.taco.Ingredient.Type;
import ru.hehnev.tacocloud.models.taco.Taco;
import ru.hehnev.tacocloud.models.taco.TacoOrder;
import ru.hehnev.tacocloud.repository.IngredientRepository;

import javax.validation.Valid;

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
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Мучная лепёшка", Type.WRAP),
//                new Ingredient("COTO", "Кукурузная лепёшка", Type.WRAP),
//                new Ingredient("GRBF", "Говяжий фарш", Type.PROTEIN),
//                new Ingredient("FALA", "Фалафель", Type.PROTEIN),
//                new Ingredient("TMTO", "Нарезанные помидоры", Type.VEGGIES),
//                new Ingredient("ONIO", "Лук", Type.VEGGIES),
//                new Ingredient("CHED", "Сыр чеддер", Type.CHEESE),
//                new Ingredient("JACK", "Монтерей Джек", Type.CHEESE),
//                new Ingredient("SLSA", "Сальса", Type.SAUCE),
//                new Ingredient("SRCR", "Сметана", Type.SAUCE)
//        );
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
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
