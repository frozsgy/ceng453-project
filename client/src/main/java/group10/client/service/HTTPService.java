package group10.client.service;

import com.google.gson.Gson;
import group10.client.constants.UiInfoConstants;
import group10.client.constants.ServerFolders;
import group10.client.model.PasswordReset;
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
    private final static String SERVER_ERROR_MSG_KEY = "error";

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

    private HttpEntity<String> initEntity(boolean withToken, String json) {
        HttpHeaders headers = new HttpHeaders();
        if (withToken) {
            headers.setBearerAuth(SessionStorage.getInstance().getToken());
        }
        if (json.isEmpty()) {
            return new HttpEntity<>(headers);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new HttpEntity<>(json, headers);
        }
    }

    public boolean login(Player player) {
        String json = gson.toJson(player);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            /**
                TODO
                for some reasons, this.apiAddress not injected. for now, I placed url hardcoded to test the client.
                also, hash passwords.
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
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.REGISTER_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(SERVER_ERROR_MSG_KEY);
        }
        return UiInfoConstants.EMPTY_STRING;
    }

    public String sendCode(PasswordReset emailContainer) {
        String json = gson.toJson(emailContainer);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.REQUEST_CODE_PATH;
            ResponseEntity<Boolean> response = restTemplate.exchange(path, HttpMethod.POST, entity, Boolean.class);
            boolean body = response.getBody();
            if (!body) {
                return UiInfoConstants.USER_NOT_FOUND;
            }
        } catch (HttpServerErrorException e) {
            System.out.println("Error");
        }
        return UiInfoConstants.EMPTY_STRING;
    }

    public String updatePassword(PasswordReset resetForm) {
        String json = gson.toJson(resetForm);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.UPDATE_PW_PATH;
            ResponseEntity<Boolean> response = restTemplate.exchange(path, HttpMethod.PUT, entity, Boolean.class);
            boolean body = response.getBody();
            if (!body) {
                return UiInfoConstants.CODE_DONT_MATCH_MESSAGE;
            }
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(SERVER_ERROR_MSG_KEY);
        }
        return UiInfoConstants.EMPTY_STRING;
    }

    public String getScoreboard(long days) {
        return getScoreboard(days, 0);
    }

    public String getScoreboard(long days, long page) {
        HttpEntity<String> entity = initEntity(true, "");
        try {
            String path = API + ServerFolders.SCOREBOARD_PATH + "/" + days + "?pageNumber=" + page;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(errorKey);
        }
    }

    public String startNewGame() {
        HttpEntity<String> entity = initEntity(true, "");
        try {
            String path = API + ServerFolders.NEW_GAME_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(errorKey);
        }
    }


    public void sendScores() {

    }

}
