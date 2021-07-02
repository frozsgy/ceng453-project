package group10.server.model;

public class MatchMakingDTO {

    private long game;
    private String ip;
    private String port;
    private long player;
    private String userName;

    public long getGame() {
        return game;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public long getPlayer() {
        return player;
    }

    public void setGame(long game) {
        this.game = game;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setPlayer(long player) {
        this.player = player;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
