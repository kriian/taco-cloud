package ru.hehnev.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.hehnev.tacocloud.models.taco.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.hehnev.tacocloud.models.taco.Ingredient.Type;
import ru.hehnev.tacocloud.models.taco.Taco;
import ru.hehnev.tacocloud.models.taco.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Мучная лепёшка", Type.WRAP),
                new Ingredient("COTO", "Кукурузная лепёшка", Type.WRAP),
                new Ingredient("GRBF", "Говяжий фарш", Type.PROTEIN),
                new Ingredient("FALA", "Фалафель", Type.PROTEIN),
                new Ingredient("TMTO", "Нарезанные помидоры", Type.VEGGIES),
                new Ingredient("ONIO", "Лук", Type.VEGGIES),
                new Ingredient("CHED", "Сыр чеддер", Type.CHEESE),
                new Ingredient("JACK", "Монтерей Джек", Type.CHEESE),
                new Ingredient("SLSA", "Сальса", Type.SAUCE),
                new Ingredient("SRCR", "Сметана", Type.SAUCE)
        );

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
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
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
}
