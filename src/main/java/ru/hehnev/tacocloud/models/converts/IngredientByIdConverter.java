package ru.hehnev.tacocloud.models.converts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.hehnev.tacocloud.models.taco.Ingredient;
import ru.hehnev.tacocloud.repository.IngredientRepository;

import java.util.HashMap;
import java.util.Map;

import static ru.hehnev.tacocloud.models.taco.Ingredient.*;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
//    private Map<String, Ingredient> ingredientMap = new HashMap<>();
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

//    public IngredientByIdConverter() {
//        ingredientMap.put("FLTO", new Ingredient("FLTO", "Мучная лепёшка", Type.WRAP));
//        ingredientMap.put("COTO", new Ingredient("COTO", "Кукурузная лепёшка", Type.WRAP));
//        ingredientMap.put("GRBF", new Ingredient("GRBF", "Говяжий фарш", Type.PROTEIN));
//        ingredientMap.put("FALA", new Ingredient("FALA", "Фалафель", Type.PROTEIN));
//        ingredientMap.put("TMTO", new Ingredient("TMTO", "Нарезанные помидоры", Type.VEGGIES));
//        ingredientMap.put("ONIO", new Ingredient("ONIO", "Лук", Type.VEGGIES));
//        ingredientMap.put("CHED", new Ingredient("CHED", "Сыр чеддер", Type.CHEESE));
//        ingredientMap.put("JACK", new Ingredient("JACK", "Монтерей Джек", Type.CHEESE));
//        ingredientMap.put("SLSA", new Ingredient("SLSA", "Сальса", Type.SAUCE));
//        ingredientMap.put("SRCR", new Ingredient("SRCR", "Сметана", Type.SAUCE));
//    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
