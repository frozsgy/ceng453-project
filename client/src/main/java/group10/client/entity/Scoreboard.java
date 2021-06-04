package group10.client.entity;

/**
 * Scoreboard entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Scoreboard {

    /**
     * Field for storing cumulative score
     */
    private long score;
    /**
     * Field for storing user id
     */
    private long userId;
    /**
     * Field for storing username
     */
    private String username;
    /**
     * Field for storing level one score
     */
    private long levelOne;
    /**
     * Field for storing level twp score
     */
    private long levelTwo;
    /**
     * Field for storing level three score
     */
    private long levelThree;
    /**
     * Field for storing level four score
     */
    private long levelFour;

    /**
     * Default no parameter constructor
     */
    public Scoreboard() {
    }

    /**
     * Three parameters constructor
     */
    public Scoreboard(long score, long userId, String username) {
        this.score = score;
        this.userId = userId;
        this.username = username;
    }

    /**
     * Full parameters constructor
     */
    public Scoreboard(long score, long userId, String username, long levelOne, long levelTwo, long levelThree, long levelFour) {
        this.score = score;
        this.userId = userId;
        this.username = username;
        this.levelOne = levelOne;
        this.levelTwo = levelTwo;
        this.levelThree = levelThree;
        this.levelFour = levelFour;
    }

    /**
     * Gets score
     *
     * @return score
     */
    public long getScore() {
        return score;
    }

    /**
     * Sets score
     *
     * @param score new value of score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     * Gets user id
     *
     * @return user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id
     *
     * @param userId new value of user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets score of level one
     *
     * @return level one score
     */
    public long getLevelOne() {
        return levelOne;
    }

    /**
     * Sets score of level one
     *
     * @param levelOne new value of level one score
     */
    public void setLevelOne(long levelOne) {
        this.levelOne = levelOne;
    }

    /**
     * Gets score of level two
     *
     * @return level two score
     */
    public long getLevelTwo() {
        return levelTwo;
    }

    /**
     * Sets score of level two
     *
     * @param levelTwo new value of level two score
     */
    public void setLevelTwo(long levelTwo) {
        this.levelTwo = levelTwo;
    }

    /**
     * Gets score of level three
     *
     * @return level three score
     */
    public long getLevelThree() {
        return levelThree;
    }

    /**
     * Sets score of level three
     *
     * @param levelThree new value of level three score
     */
    public void setLevelThree(long levelThree) {
        this.levelThree = levelThree;
    }

    /**
     * Gets score of level four
     *
     * @return level four score
     */
    public long getLevelFour() {
        return levelFour;
    }

    /**
     * Sets score of level four
     *
     * @param levelFour new value of level four score
     */
    public void setLevelFour(long levelFour) {
        this.levelFour = levelFour;
    }
}
