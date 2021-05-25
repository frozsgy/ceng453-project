package group10.client.service;

import com.google.gson.Gson;
import group10.client.model.Player;
import org.springframework.stereotype.Service;

@Service
public class HTTPService {

    private static HTTPService instance;

    public static HTTPService getInstance() {
        if (instance == null) {
            return new HTTPService();
        }
        return instance;
    }

    public void login(Player player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
    }
}
