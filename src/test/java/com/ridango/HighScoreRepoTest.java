package com.ridango;

import com.ridango.game.CocktailGameApplication;
import com.ridango.game.model.HighScore;
import com.ridango.game.repository.HighScoreRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CocktailGameApplication.class)
public class HighScoreRepoTest {

    @Autowired
    private HighScoreRepo highScoreRepo;

    @Test
    @DisplayName("Should save a high score and assign an ID")
    void testSaveHighScore() {
        HighScore highScore = new HighScore(100);

        HighScore savedHighScore = highScoreRepo.save(highScore);

        assertNotNull(savedHighScore.getId());
        assertEquals(100, savedHighScore.getHighest());
    }

    @Test
    @DisplayName("Should find the highest score from the repository")
    void testFindHighestScore() {
        highScoreRepo.save(new HighScore(200));
        highScoreRepo.save(new HighScore(100));

        Optional<HighScore> highScore = highScoreRepo.findFirstByOrderByHighestDesc();

        assertTrue(highScore.isPresent());
        assertEquals(200, highScore.get().getHighest());
    }
}
