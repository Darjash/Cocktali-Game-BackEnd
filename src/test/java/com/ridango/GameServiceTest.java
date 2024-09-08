package com.ridango;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.GameSession;
import com.ridango.game.service.CocktailService;
import com.ridango.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameServiceTest {
    @Mock
    private CocktailService cocktailService;

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
    void testStartNewGame() {
        GameSession session = gameService.startNewGame();

        assertNotNull(session);
        assertEquals("______", session.getHiddenCocktailName());
        assertEquals(5, session.getAttemptsLeft());
    }

    @Test
    void testMakeCorrectGuess(){
        gameService.startNewGame();
        GameSession session = gameService.makeGuess("Mojito");

        assertNotNull(session);
        assertEquals(5, session.getScore());
        assertEquals(5, session.getAttemptsLeft());
    }

    @Test
    void testMakeIncorrectGuess() {
        gameService.startNewGame();
        GameSession session = gameService.makeGuess("WrongGuess");

        assertNotNull(session);
        assertEquals(4, session.getAttemptsLeft());
    }

    @Test
    void testRestartGame() {
        GameSession session = gameService.restartGame();

        assertNotNull(session);
        assertEquals(5, session.getAttemptsLeft());
        assertEquals(0, session.getScore());
    }
}