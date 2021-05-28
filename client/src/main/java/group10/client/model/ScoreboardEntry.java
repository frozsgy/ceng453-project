package group10.client.model;

public class ScoreboardEntry {

    private long score;
    private long userId;
    private String username;

    public ScoreboardEntry() {
    }

    public ScoreboardEntry(long score, long userId, String username) {
        this.score = score;
        this.userId = userId;
        this.username = username;
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
}
