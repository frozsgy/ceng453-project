package group10.server.model;

import javax.validation.constraints.NotBlank;

public class MatchDTO {

    @NotBlank
    private int score;

    @NotBlank
    private long game;

    @NotBlank
    private int level;


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getGame() {
        return game;
    }

    public void setGame(long game) {
        this.game = game;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
