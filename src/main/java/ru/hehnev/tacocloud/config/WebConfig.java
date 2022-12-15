package ru.hehnev.tacocloud.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.hehnev.tacocloud.models.taco.Ingredient;
import ru.hehnev.tacocloud.repository.IngredientRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

//    @Bean
//    public CommandLineRunner dataLoader(IngredientRepository repo) {
//        return args -> repo.save(new Ingredient("SVIN", "Мясо свинины", Ingredient.Type.PROTEIN));
//    }
}
