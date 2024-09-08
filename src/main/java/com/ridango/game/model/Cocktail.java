package com.ridango.game.model;

import lombok.Data;

import java.util.Map;

@Data
public class Cocktail {
    private String idDrink;
    private String strDrink;
    private String strInstructions;
    private String strCategory;
    private String strGlass;
    private String strDrinkThumb;
    private Map<String, String> ingredients;
}
