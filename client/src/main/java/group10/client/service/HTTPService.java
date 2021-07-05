package group10.client.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.constants.APIConstants;
import group10.client.constants.UiInfoConstants;
import group10.client.entity.Level;
import group10.client.entity.PlayerGame;
import group10.client.model.OpponentInfo;
import group10.client.model.PasswordReset;
import group10.client.model.Player;
import group10.client.state.SessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Singleton Service class that is responsible of sending
 * HTTP requests to server. Acts as a wrapper class.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Service
public class HTTPService {

    /**
     * Constant logger instance to print messages to console.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPService.class);

    /**
     * Rest template instance that sends requests to server.
     */
    private RestTemplate restTemplate;
    /**
     * Gson instance that is used to serialize and deserialize request and response bodies.
     */
    private Gson gson;
    /**
     * Constant JSON key to access server error messages.
     */
    private final static String SERVER_ERROR_MSG_KEY = "error";
    /**
     * Singleton instance.
     */
    private static HTTPService instance;

    /**
     * A type instance of Map of two Strings to be read from JSON files as error messages.
     */
    private Type errorMap;

    /**
     * Helper method to hash Strings with SHA-256.
     * Used for hashing passwords before sending them over network.
     * However, works on any String instance.
     *
     * @param pw Password to be hashed with SHA-256
     * @return SHA-256 Hash string of given password.
     */
    private String getSHA256(String pw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
            return new String(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("SHA-256 Failed.");
        }
        return pw;
    }

    /**
     * Private constructor for HTTPService.
     * Initializes restTemplate and gson
     */
    private HTTPService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
        this.errorMap = new TypeToken<Map<String, String>>() {
        }.getType();
    }

    /**
     * Creates a new HTTPService instance and assigns it to instance field.
     * Used for singleton access.
     *
     * @return instance
     */
    public static HTTPService getInstance() {
        if (instance == null) {
            instance = new HTTPService();
            LOGGER.info("Singleton HTTPService created");
        }
        return instance;
    }

    /**
     * Initializes a new HttpEntity and returns it.
     *
     * @param withToken Flag to add token header or not. Add if true, don't if false.
     * @param json      Json body to attach to created HttpEntity.
     * @return newly created HttpEntity.
     */
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

    /**
     * Given a Player instance, logs in the user by sending a HTTP request to server
     * and populates SessionStorage fields on successful login.
     * Also logs if the operation was successful or not to console.
     * Hashes the password with SHA-256 before sending it
     *
     * @param player Player to be logged in.
     * @return True if login is successful, false otherwise.
     * @see Player
     */
    public boolean login(Player player) {
        player.setPassword(this.getSHA256(player.getPassword()));
        String json = gson.toJson(player);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = APIConstants.LOGIN_PATH;
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

    /**
     * Registers the given player by sending HTTP request.
     * Logs if the operation was successful or not to console.
     * Hashes the password with SHA-256 before sending it
     *
     * @param player Player to be registered.
     * @return Empty string on success, Server error message on error.
     * @see Player
     */
    public String register(Player player) {
        player.setPassword(this.getSHA256(player.getPassword()));
        String json = gson.toJson(player);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = APIConstants.REGISTER_PATH;
            ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST, entity, String.class);
            LOGGER.info("Successful register: " + player.getUsername());
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), this.errorMap);
            LOGGER.info("Unsuccessful register: " + player.getUsername());
            return messagePair.get(SERVER_ERROR_MSG_KEY);
        }
        return UiInfoConstants.EMPTY_STRING;
    }

    /**
     * Sends a HTTP request to server to request a password reset code
     * to given email address. Logs the success of operation to console.
     *
     * @param emailContainer Object that wraps email.
     * @return Error message if operation failed, Empty string if operation was successful.
     * @see PasswordReset
     */
    public String sendCode(PasswordReset emailContainer) {
        String json = gson.toJson(emailContainer);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = APIConstants.REQUEST_CODE_PATH;
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

    /**
     * Updates the password by sending a HTTP request to server.
     * Logs the operation success status to console.
     * Hashes the password with SHA-256 before sending it
     *
     * @param resetForm Form that holds reset code, username and new password.
     * @return Empty string if operation was successful, error message otherwise.
     * @see PasswordReset
     */
    public String updatePassword(PasswordReset resetForm) {
        resetForm.setPassword(this.getSHA256(resetForm.getPassword()));
        String json = gson.toJson(resetForm);
        HttpEntity<String> entity = initEntity(false, json);
        try {
            String path = APIConstants.UPDATE_PW_PATH;
            ResponseEntity<Boolean> response = restTemplate.exchange(path, HttpMethod.PUT, entity, Boolean.class);
            boolean body = response.getBody();
            if (!body) {
                LOGGER.info("Error during password reset - code mismatch");
                return UiInfoConstants.CODE_DONT_MATCH_MESSAGE;
            }
        } catch (HttpServerErrorException e) {
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), this.errorMap);
            LOGGER.info("Error during password reset");
            return messagePair.get(SERVER_ERROR_MSG_KEY);
        }
        LOGGER.info("Password reset for " + resetForm.getUsername());
        return UiInfoConstants.EMPTY_STRING;
    }

    /**
     * Sends a get request to given URL with empty body and with token.
     *
     * @param url URL that the request is sent to.
     * @return Server response.
     */
    private String getRequest(String url) {
        HttpEntity<String> entity = initEntity(true, "");
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            String errorKey = "error";
            Map<String, String> messagePair = gson.fromJson(e.getResponseBodyAsString(), this.errorMap);
            return messagePair.get(errorKey);
        }
    }

    /**
     * Gets the scoreboard for last n days with paging
     *
     * @param days Last n days
     * @param page Requested page number.
     * @return Server message.
     */
    public String getScoreboard(long days, long page) {
        return this.getRequest(APIConstants.SCOREBOARD_PATH + "/" + days + "?pageNumber=" + page);
    }

    /**
     * Starts a new game by sending a GET request to ServerFolders.NEW_GAME_PATH.
     * Logs the username of the user who requested the new game to console.
     *
     * @return Server message
     */
    public String startNewGame() {
        LOGGER.info("New Game request from " + SessionStorage.getInstance().getUsername());
        return this.getRequest(APIConstants.NEW_GAME_PATH);
    }

    /**
     * POSTs score of the level to server.
     * Logs username, game and score to console.
     *
     * @param level Level that score was calculated for.
     * @return Server Response that contains player, game, level and score.
     * @see PlayerGame
     */
    public PlayerGame sendScores(Level level) {
        String json = gson.toJson(level);
        HttpEntity<String> entity = initEntity(true, json);
        String path = APIConstants.SEND_SCORE_PATH;
        ResponseEntity<PlayerGame> response = restTemplate.exchange(path, HttpMethod.POST, entity, PlayerGame.class);
        LOGGER.info("Score submission from " + SessionStorage.getInstance().getUsername() + " - Game: " + level.getGame() + " - Score: " + level.getScore());
        return response.getBody();
    }

    /**
     * Requests the opponent information.
     * If an opponent is found, it returns their network and player information.
     * If an opponent is not found, this player whose information provided as parameter is queued.
     * @param selfInformation This player's information to queue if an opponent is not found.
     * @return null if an opponent is not found. Opponent information otherwise.
     */
    public OpponentInfo getOpponent(OpponentInfo selfInformation) {
        String json = gson.toJson(selfInformation);
        HttpEntity<String> entity = initEntity(true, json);
        String path = APIConstants.FIND_OPPONENT_PATH;
        ResponseEntity<OpponentInfo> response = restTemplate.exchange(path, HttpMethod.POST, entity, OpponentInfo.class);
        System.out.println(response.getBody());
        return response.getBody();
    }


}
