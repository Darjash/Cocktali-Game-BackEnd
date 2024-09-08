package com.ridango.game.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class GameSession {

    private Cocktail currentCocktail;
    private String hiddenCocktailName;
    private int attemptsLeft;
    private int score;

    public GameSession(Cocktail currentCocktail, int attemptsLeft, int score) {
        this.currentCocktail = currentCocktail;
        this.hiddenCocktailName = hideCocktailName(currentCocktail.getStrDrink());
        this.attemptsLeft = attemptsLeft;
        this.score = score;
    }

    private String hideCocktailName(String name) {
        return name.replaceAll("[A-Za-z0-9]", "_");
    }

    public void revealRandomLetter() {
        Random random = new Random();
        StringBuilder hidden = new StringBuilder(hiddenCocktailName);
        String cocktailName = currentCocktail.getStrDrink();

        List<Integer> hiddenLettersIndexes = new ArrayList<>();

        for (int i = 0; i < hidden.length(); i++) {
            if (hidden.charAt(i) == '_') {
                hiddenLettersIndexes.add(i);
            }
        }

        if (!hiddenLettersIndexes.isEmpty() && hiddenLettersIndexes.size()>2) {
            int randomIndex = hiddenLettersIndexes.get(random.nextInt(hiddenLettersIndexes.size()));
            hidden.setCharAt(randomIndex, cocktailName.charAt(randomIndex));
            hiddenCocktailName = hidden.toString();
            if (hiddenCocktailName.length()>13){
                int randomIndex2 = hiddenLettersIndexes.get(random.nextInt(hiddenLettersIndexes.size()));
                hidden.setCharAt(randomIndex2, cocktailName.charAt(randomIndex2));
                hiddenCocktailName = hidden.toString();
            }
        }
    }

    public void decrementAttempts() {
        attemptsLeft--;
    }

    public void incrementScore(int attemptsLeft) {
        score += attemptsLeft;
    }
}
