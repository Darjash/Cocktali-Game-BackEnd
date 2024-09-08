package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class CocktailService {

    private static final String RANDOM_COCKTAIL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

    private HashSet<String> appearedCocktails = new HashSet<>();

    public Cocktail fetchRandomCocktail() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> response = restTemplate.getForObject(RANDOM_COCKTAIL, Map.class);
        List<Map<String, Object>> drinks = (List<Map<String, Object>>) response.get("drinks");
        Map<String, Object> cocktailData = drinks.get(0);

        Cocktail cocktail = new Cocktail();
        cocktail.setIdDrink((String) cocktailData.get("idDrink"));
        cocktail.setStrDrink((String) cocktailData.get("strDrink"));
        cocktail.setStrCategory((String) cocktailData.get("strCategory"));
        cocktail.setStrGlass((String) cocktailData.get("strGlass"));
        cocktail.setStrInstructions((String) cocktailData.get("strInstructions"));
        cocktail.setStrDrinkThumb((String) cocktailData.get("strDrinkThumb"));

        Map<String, String> ingredients = new HashMap<>();
        for (int i = 1; i <= 15; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;

            String ingredient = (String) cocktailData.get(ingredientKey);
            String measure = (String) cocktailData.get(measureKey);

            if (ingredient != null) {
                ingredients.put(ingredient, measure != null ? measure : "");
            }
        }
        cocktail.setIngredients(ingredients);

        if (appearedCocktails.contains(cocktail.getIdDrink())) {
            return fetchRandomCocktail();
        }

        appearedCocktails.add(cocktail.getIdDrink());

        return cocktail;
    }

    public void resetAppearedCocktails() {
        appearedCocktails.clear();

    }
}
