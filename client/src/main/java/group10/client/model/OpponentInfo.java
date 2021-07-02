package group10.client.model;


/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Carries network and player information of opponent player.
 */
public class OpponentInfo {

    /**
     * IP address of the opponent.
     */
    private String ip;
    /**
     * Port that opponent client program is running on.
     */
    private String port;
    /**
     * Id of the opponent player
     */
    private long player;

    /**
     * Username of the opponent player
     */
    private String userName;

    public OpponentInfo() {

    }
    public OpponentInfo(String ip, String port){
        this.ip = ip;
        this.port = port;
    }

    /**
     * Gets the IP address
     * @return IP address
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets the port
     * @return Port number
     */
    public String getPort() {
        return port;
    }

    /**
     * Gets the player id
     * @return Id of the player
     */
    public long getPlayer() {
        return player;
    }

    /**
     * Sets the IP address
     * @param ip IP to be set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Sets the port
     * @param port Port to be set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Sets the player id
     * @param player Player id to be set
     */
    public void setPlayer(long player) {
        this.player = player;
    }

    /**
     * Gets the username
     * @return Username of the opponent
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username
     * @param userName Username to be set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
