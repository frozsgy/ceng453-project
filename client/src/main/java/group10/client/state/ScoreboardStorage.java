package group10.client.state;

import group10.client.entity.PagedEntity;
import group10.client.entity.Scoreboard;

/**
 * Singleton storage class for scoreboard data.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class ScoreboardStorage {

    /**
     * Singleton instance.
     */
    private static ScoreboardStorage instance;
    /**
     * Paged scoreboard data that is stored.
     */
    private PagedEntity<Scoreboard> data;
    /**
     * Holds how many days are being displayed.
     * For instance, if interval is 7, scores of last
     * 7 days are stored.
     */
    private int interval;

    /**
     * Creates an instance, assigns it to instance field and returns it.
     *
     * @return ScoreboardStorage instance.
     */
    public static ScoreboardStorage getInstance() {
        if (instance == null) {
            instance = new ScoreboardStorage();
        }
        return instance;
    }

    /**
     * Private default constructor.
     */
    private ScoreboardStorage() {
    }

    /**
     * Gets the data
     *
     * @return Paged scoreboard data.
     */
    public PagedEntity<Scoreboard> getData() {
        return data;
    }

    /**
     * Sets the data
     *
     * @param data data to be set.
     */
    public void setData(PagedEntity<Scoreboard> data) {
        this.data = data;
    }

    /**
     * Gets the interval
     *
     * @return interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Sets the interval
     *
     * @param interval interval to be set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
}
