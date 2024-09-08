package com.ridango.game.repository;

import com.ridango.game.model.HighScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HighScoreRepo extends JpaRepository <HighScore, Long> {
    Optional<HighScore> findFirstByOrderByHighestDesc();
}
