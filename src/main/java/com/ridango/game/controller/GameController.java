package com.ridango.game.controller;

import com.ridango.game.model.GameSession;
import com.ridango.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST})
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public GameSession startGame() {
        return gameService.startNewGame();
    }

    @GetMapping("/restart")
    public GameSession restartGame(){
        return gameService.restartGame();
    }

    @PostMapping("/guess")
    public GameSession makeGuess(@RequestParam String guess) {
        return gameService.makeGuess(guess);
    }

    @GetMapping("/highscore")
    public int getHighScore() {
        return gameService.getHighScore();
    }
}