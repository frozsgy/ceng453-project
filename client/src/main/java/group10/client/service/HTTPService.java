package group10.client.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.constants.UiInfoConstants;
import group10.client.constants.ServerFolders;
import group10.client.entity.Level;
import group10.client.entity.PagedEntity;
import group10.client.entity.PlayerGame;
import group10.client.entity.Scoreboard;
import group10.client.model.PasswordReset;
import group10.client.model.Player;
import group10.client.state.SessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class HTTPService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPService.class);

    @Value("${spring.application.api-address}")
    private String apiAddress;
    private RestTemplate restTemplate;
    private Gson gson;
    private final static String API = "http://localhost:8080/api";
    private final static String SERVER_ERROR_MSG_KEY = "error";
    private static HTTPService instance;

    private String getSHA256(String pw) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
            return new String(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("SHA-256 Failed.");
        }
        return pw;
    }

    private HTTPService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public static HTTPService getInstance() {
        if (instance == null) {
            instance = new HTTPService();
            LOGGER.info("Singleton HTTPService created");
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
        player.setPassword(this.getSHA256(player.getPassword()));
        String json = gson.toJson(player);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.LOGIN_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
            String token = response.getBody(); // store it somewhere
            SessionStorage.getInstance().setToken(token);
            SessionStorage.getInstance().setUsername(player.getUsername());
            LOGGER.info("Successful login: " + player.getUsername());
        } catch (HttpServerErrorException e) {
            LOGGER.info("Unsuccessful login with invalid credentials: " + player.getUsername());
            return false;
        }
        return true;
    }

    public String register(Player player) {
        player.setPassword(this.getSHA256(player.getPassword()));
        String json = gson.toJson(player);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.REGISTER_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
            LOGGER.info("Successful register: " + player.getUsername());
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            LOGGER.info("Unsuccessful register: " + player.getUsername());
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
                LOGGER.info("Error during password reset - user not found");
                return UiInfoConstants.USER_NOT_FOUND;
            }
        } catch (HttpServerErrorException e) {
            LOGGER.info("Error during password reset");
        }
        LOGGER.info("Password reset request for " + emailContainer.getUsername());
        return UiInfoConstants.EMPTY_STRING;
    }

    public String updatePassword(PasswordReset resetForm) {
        resetForm.setPassword(this.getSHA256(resetForm.getPassword()));
        String json = gson.toJson(resetForm);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = API + ServerFolders.UPDATE_PW_PATH;
            ResponseEntity<Boolean> response = restTemplate.exchange(path, HttpMethod.PUT, entity, Boolean.class);
            boolean body = response.getBody();
            if (!body) {
                LOGGER.info("Error during password reset - code mismatch");
                return UiInfoConstants.CODE_DONT_MATCH_MESSAGE;
            }
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            LOGGER.info("Error during password reset");
            return messagePair.get(SERVER_ERROR_MSG_KEY);
        }
        LOGGER.info("Password reset for " + resetForm.getUsername());
        return UiInfoConstants.EMPTY_STRING;
    }

    public String getScoreboard(long days) {
        return getScoreboard(days, 0);
    }

    private String getRequest(String url) {
        HttpEntity<String> entity = initEntity(true, "");
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            return messagePair.get(errorKey);
        }
    }

    public String getScoreboard(long days, long page) {
        return this.getRequest(API + ServerFolders.SCOREBOARD_PATH + "/" + days + "?pageNumber=" + page);
    }

    public String startNewGame() {
        LOGGER.info("New Game request from " + SessionStorage.getInstance().getUsername());
        return this.getRequest(API + ServerFolders.NEW_GAME_PATH);
    }

    public PlayerGame sendScores(Level level) {
        String json = gson.toJson(level);
        HttpEntity<String> entity = initEntity(true, json);
        String path = API + ServerFolders.SEND_SCORE_PATH;
        ResponseEntity<PlayerGame> response = restTemplate.exchange(path, HttpMethod.POST, entity, PlayerGame.class);
        LOGGER.info("Score submission from " + SessionStorage.getInstance().getUsername() + " - Game: " + level.getGame() + " - Score: " + level.getScore());
        return response.getBody();
    }

}
