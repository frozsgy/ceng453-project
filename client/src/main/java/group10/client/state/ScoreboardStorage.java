package group10.client.state;

import group10.client.model.PagedEntity;
import group10.client.model.Scoreboard;

public class ScoreboardStorage {

    private static ScoreboardStorage instance;
    private PagedEntity<Scoreboard> data;
    private int interval;

    public static ScoreboardStorage getInstance() {
        if (instance == null) {
            instance = new ScoreboardStorage();
        }
        return instance;
    }

    private ScoreboardStorage() {
    }

    public PagedEntity<Scoreboard> getData() {
        return data;
    }

    public void setData(PagedEntity<Scoreboard> data) {
        this.data = data;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
