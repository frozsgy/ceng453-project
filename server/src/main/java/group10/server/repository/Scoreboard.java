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
}
