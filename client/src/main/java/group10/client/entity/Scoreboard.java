package group10.client.entity;

public class Scoreboard {

    private long score;
    private long userId;
    private String username;
    private long levelOne;
    private long levelTwo;
    private long levelThree;
    private long levelFour;


    public Scoreboard() {
    }

    public Scoreboard(long score, long userId, String username) {
        this.score = score;
        this.userId = userId;
        this.username = username;
    }

    public Scoreboard(long score, long userId, String username, long levelOne, long levelTwo, long levelThree, long levelFour) {
        this.score = score;
        this.userId = userId;
        this.username = username;
        this.levelOne = levelOne;
        this.levelTwo = levelTwo;
        this.levelThree = levelThree;
        this.levelFour = levelFour;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(long levelOne) {
        this.levelOne = levelOne;
    }

    public long getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(long levelTwo) {
        this.levelTwo = levelTwo;
    }

    public long getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(long levelThree) {
        this.levelThree = levelThree;
    }

    public long getLevelFour() {
        return levelFour;
    }

    public void setLevelFour(long levelFour) {
        this.levelFour = levelFour;
    }
}
