package com.ridango;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.GameSession;
import com.ridango.game.repository.HighScoreRepo;
import com.ridango.game.service.CocktailService;
import com.ridango.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameServiceTest {
    @Mock
    private CocktailService cocktailService;

    @Mock
    private HighScoreRepo highScoreRepo;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Cocktail cocktail = new Cocktail();
        cocktail.setStrDrink("Mojito");
        when(cocktailService.fetchRandomCocktail()).thenReturn(cocktail);
    }

    @Test
    @DisplayName("Should start a new game session with 5 attempts and a hidden cocktail name")
    void testStartNewGame() {
        GameSession session = gameService.startNewGame();

        assertNotNull(session);
        assertEquals("______", session.getHiddenCocktailName());
        assertEquals(5, session.getAttemptsLeft());
    }

    @Test
    @DisplayName("Should correctly guess the cocktail and start a new game with a full score")
    void testMakeCorrectGuess(){
        gameService.startNewGame();
        GameSession session = gameService.makeGuess("Mojito");

        assertNotNull(session);
        assertEquals(5, session.getScore());
        assertEquals(5, session.getAttemptsLeft());
    }

    @Test
    @DisplayName("Should reduce attempts left by 1 after an incorrect guess and reveal one letter")
    void testMakeIncorrectGuess() {
        gameService.startNewGame();
        GameSession session = gameService.makeGuess("WrongGuess");
        String hiddenName = session.getHiddenCocktailName();
        int countHiddenLetters = StringUtils.countOccurrencesOf(hiddenName, "_");

        assertNotNull(session);
        assertEquals(4, session.getAttemptsLeft());
        assertEquals(5, countHiddenLetters);
    }

    @Test
    @DisplayName("Should reduce attempts left by 1 after an incorrect guess and reveal two letters")
    void testMakeIncorrectGuessOnLongCocktail() {
        Cocktail longCocktail = new Cocktail();
        longCocktail.setStrDrink("Banana Cantaloupe Smoothie");
        when(cocktailService.fetchRandomCocktail()).thenReturn(longCocktail);

        gameService.startNewGame();

        GameSession session = gameService.makeGuess("WrongGuess");

        String hiddenName = session.getHiddenCocktailName();
        int countHiddenLetters = StringUtils.countOccurrencesOf(hiddenName, "_");

        assertNotNull(session);
        assertEquals(4, session.getAttemptsLeft());
        assertEquals(22, countHiddenLetters);
    }

    @Test
    @DisplayName("Should restart the game and reset attempts to 5 and score to 0")
    void testRestartGame() {
        GameSession session = gameService.restartGame();

        assertNotNull(session);
        assertEquals(5, session.getAttemptsLeft());
        assertEquals(0, session.getScore());
    }
}