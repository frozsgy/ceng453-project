package group10.server.model;

import org.hibernate.validator.constraints.Range;


public class MatchDTO {

    @Range(min = 0, message= "Score may not be empty")
    private int score;

    @Range(min = 0, message= "Game may not be empty")
    private long game;


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

}
