package com.ridango.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "high_score")
@Data
@NoArgsConstructor
public class HighScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int highest;

    public HighScore(int score) {
        this.highest = score;
    }
}
