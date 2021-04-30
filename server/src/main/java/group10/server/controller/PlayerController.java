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

@RestController
@RequestMapping("/api/user")
public class PlayerController {
    private ApplicationContext context;
    private PlayerService playerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    public PlayerController(PlayerService playerService, ApplicationContext context) {
        this.context = context;
        this.playerService = playerService;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> get() {
        Player loggedIn = playerService.getLoggedInPlayer();
        LOGGER.info("Get user details: " + loggedIn.getUsername());
        return ResponseEntity.ok(loggedIn);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginData) {
        LOGGER.info("User login: " + loginData.getUsername());
        return ResponseEntity.ok(playerService.login(loginData));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody PlayerDTO user) {
        LOGGER.info("User register: " + user.getEmail());
        return ResponseEntity.ok(playerService.register(user));
    }

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

    @PutMapping("/updatePassword")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {
        LOGGER.info("Password updated for: " + passwordResetDTO.getUsername());
        return ResponseEntity.ok(playerService.updatePassword(passwordResetDTO));
    }

}
