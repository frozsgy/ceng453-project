package group10.server.repository;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Interface that is used by MatchRepository while returning Scoreboard objects.
 */
public interface Scoreboard {

    /**
     * Getter method for Score.
     *
     * @return Score.
     */
    long getScore();

    /**
     * Getter method for User id.
     *
     * @return User id.
     */
    long getUserId();

    /**
     * Getter method for Username.
     *
     * @return Username.
     */
    String getUsername();

    /**
     * Getter method for Score for Level One.
     *
     * @return Score.
     */
    long getLevelOne();

    /**
     * Getter method for Score for Level Two.
     *
     * @return Score.
     */
    long getLevelTwo();

    /**
     * Getter method for Score for Level Three.
     *
     * @return Score.
     */
    long getLevelThree();

    /**
     * Getter method for Score for Level Four.
     *
     * @return Score.
     */
    long getLevelFour();
}
