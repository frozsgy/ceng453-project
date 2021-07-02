package group10.server.model;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Data transfer object that is received from client and sent to client as response to matchmaking requests.
 * Carries network information for player.
 * Player and userName fields are auto-populated from the token. They are not by sent player.
 * Even if the player tries to send them, server will overwrite them with the data extracted from the user's token.
 */
public class MatchMakingDTO {

    /**
     * IP address of the user.
     */
    private String ip;
    /**
     * Port that client program is running on.
     */
    private String port;
    /**
     * Id of the player that requested the opponent information.
     * Populated by using the token of the user.
     */
    private long player;

    /**
     * Username of the player that requested the opponent information.
     * Populated by using the token of the user.
     */
    private String userName;

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
     * @return Username of the player
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
