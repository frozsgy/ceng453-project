package group10.client.utility;


public class SessionStorage {

    private String username;
    private String token;

    private static SessionStorage instance;


    public static SessionStorage getInstance() {
        if (instance == null) {
            instance = new SessionStorage();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void logout() {
        this.token = null;
        this.username = null;
    }
}
