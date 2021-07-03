package group10.server.controller;

import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordResetDTO;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Endpoint class for Players.
 * All requests that are sent to /api/user is handled by
 * this class
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@RestController
@RequestMapping("/api/user")
public class PlayerController {
    /**
     * Autowired application context to connect spring.
     *
     * @see ApplicationContext
     */
    private ApplicationContext context;
    /**
     * Autowired service responsible of players
     *
     * @see PlayerService
     */
    private PlayerService playerService;
    /**
     * Logger to log things to standard out.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    /**
     * Constructor for Player Controller
     *
     * @param playerService Autowired playerService
     * @param context       Autowired context
     */
    @Autowired
    public PlayerController(PlayerService playerService, ApplicationContext context) {
        this.context = context;
        this.playerService = playerService;
    }

    /**
     * HTTP GET requests to "" path are served by this method.
     * Gets data of the player that sent the request
     * Logs the username of the user that sent the request to standard out.
     *
     * @return Player
     * @see Player
     */
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> get() {
        Player loggedIn = playerService.getLoggedInPlayer();
        LOGGER.info("Get user details: " + loggedIn.getUsername());
        return ResponseEntity.ok(loggedIn);
    }

    /**
     * HTTP POST requests to "/login" path are served by this method.
     * Logs the username that is present in the request body to standard out.
     *
     * @param loginData Login form that contains login credentials
     * @return HTTP200 Token on success. HTTP 5xx on error.
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginData) {
        LOGGER.info("User login: " + loginData.getUsername());
        return ResponseEntity.ok(playerService.login(loginData));
    }

    /**
     * HTTP POST requests to "/register" path are served by this method.
     * Logs the email that is present in the request body to standard out.
     *
     * @param user Player that is going to be registered.
     * @return HTTP200 Player data on success. HTTP 5xx on error.
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody PlayerDTO user) {
        LOGGER.info("User register: " + user.getEmail());
        return ResponseEntity.ok(playerService.register(user));
    }

    /**
     * HTTP POST requests to "/requestPwCode" path are served by this method.
     * Logs the email address that the code is going to be sent to standard out.
     *
     * @param email Email address that the password reset code is sent to.
     * @return HTTP200true if mail is sent successfully, HTTP200 false otherwise
     */
    @PostMapping("/requestPwCode")
    @ResponseBody
    public ResponseEntity<?> requestPwCode(@RequestBody String email) {
        LOGGER.info("Password code request for: " + email);
        try {
            JSONObject emailJSON = new JSONObject(email);
            return ResponseEntity.ok(playerService.requestPwCode(emailJSON));
        } catch (JSONException e) {
            return ResponseEntity.ok(false);
        }
    }

    /**
     * HTTP POST requests to "/updatePassword" path are served by this method.
     * Logs the username sent by the client.
     *
     * @param passwordResetDTO Data that is sent by the client to reset their password.
     * @return HTTP200 true if successfully updated, HTTP200 false otherwise.
     * @see PasswordResetDTO
     */
    @PutMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        LOGGER.info("Password updated for: " + passwordResetDTO.getUsername());
        return ResponseEntity.ok(playerService.updatePassword(passwordResetDTO));
    }

}
