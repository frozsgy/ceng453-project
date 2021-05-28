package group10.client.service;

import com.google.gson.Gson;
import group10.client.constants.ErrorConstants;
import group10.client.constants.ServerFolders;
import group10.client.model.Player;
import group10.client.state.SessionStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HTTPService {

    @Value("${spring.application.api-address}")
    private String apiAddress;
    private RestTemplate restTemplate;
    private Gson gson;
    private final static String API = "http://localhost:8080/api";

    private static HTTPService instance;

    private HTTPService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public static HTTPService getInstance() {
        if (instance == null) {
            instance = new HTTPService();
        }
        return instance;
    }

    public boolean login(Player player) {
        String json = gson.toJson(player);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            /*
                TODO
                for some reasons, this.apiAddress not injected. for now, I placed url hardcoded to test the client.
             */
            String path = API + ServerFolders.LOGIN_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
            String token = response.getBody(); // store it somewhere
            SessionStorage.getInstance().setToken(token);
            SessionStorage.getInstance().setUsername(player.getUsername());
            System.out.println("Success!");
        } catch (HttpServerErrorException e) {
            // invalid credientials.
            // debug data
            System.out.println("Invalid credientials");
            return false;
        }
        return true;
    }

    public String register(Player player) {
        String json = gson.toJson(player);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            String path = API + ServerFolders.REGISTER_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(errorKey);
        }
        return ErrorConstants.EMPTY_STRING;
    }

    public String getScoreboard(long days) {
        return getScoreboard(days, 0);
    }

    public String getScoreboard(long days, long page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(SessionStorage.getInstance().getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            // TODO -- REMOVE pageSize=1&
            String path = API + ServerFolders.SCOREBOARD_PATH + "/" + days + "?pageSize=1&pageNumber=" + page;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(errorKey);
        }
    }

}
