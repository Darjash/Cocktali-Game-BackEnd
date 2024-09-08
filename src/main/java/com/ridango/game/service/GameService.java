package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.GameSession;
import com.ridango.game.model.HighScore;
import com.ridango.game.repository.HighScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private HighScoreRepo highScoreRepository;

    private GameSession thisSession;

    public GameSession startNewGame() {
        int score = 0;
        getHighScore();

        if (thisSession != null) {
            score = thisSession.getScore();
        }

        Cocktail randomCocktail = cocktailService.fetchRandomCocktail();
        thisSession = new GameSession(randomCocktail, 5, score);
        return thisSession;
    }

    public GameSession restartGame(){
        getHighScore();
        Cocktail randomCocktail = cocktailService.fetchRandomCocktail();
        thisSession = new GameSession(randomCocktail, 5, 0);
        cocktailService.resetAppearedCocktails();
        return thisSession;
    }

    public GameSession makeGuess(String guess) {
        if (guess.equalsIgnoreCase(thisSession.getCurrentCocktail().getStrDrink())) {
            thisSession.incrementScore(thisSession.getAttemptsLeft());
            return startNewGame();
        } else {
            thisSession.decrementAttempts();
            thisSession.revealRandomLetter();

            if (thisSession.getAttemptsLeft() <= 0) {
                saveHighScore();
            }
            return thisSession;
        }
    }

    private void saveHighScore() {
        HighScore highScore = new HighScore(thisSession.getScore());
        highScoreRepository.save(highScore);
    }

    public int getHighScore() {
        Optional<HighScore> highScore = highScoreRepository.findFirstByOrderByHighestDesc();
        if(highScore.isPresent()){
            return highScore.get().getHighest();
        }else{
            return 0;
        }
    }
}
