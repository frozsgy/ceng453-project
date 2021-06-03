package group10.client.entity;

public class Level {

    private long game;
    private long score;


    public Level() {
    }

    public Level(long game, long score) {
        this.game = game;
        this.score = score;
    }

    public long getGame() {
        return game;
    }

    public void setGame(long game) {
        this.game = game;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
