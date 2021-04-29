package group10.server.controller;

import group10.server.model.LoginDTO;
import group10.server.model.PasswordDTO;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
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
        return ResponseEntity.ok("UserHome");
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@Valid @RequestBody LoginDTO loginData) {
        return playerService.login(loginData);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody PlayerDTO user) {
        // TODO
        LOGGER.info("User register: " + user.getEmail());
        return ResponseEntity.ok(playerService.register(user));
    }

    @GetMapping("/logout/{userId}")
    @ResponseBody
    public ResponseEntity<?> logout(@PathVariable long userId) {
        // TODO
        return ResponseEntity.ok("UserLogout");
    }

    @GetMapping("/requestPassword/{email}")
    @ResponseBody
    public ResponseEntity<?> requestPassword(@PathVariable String email) {
        return ResponseEntity.ok(playerService.requestPassword(email));
    }

    @PutMapping("/updatePassword/{userId}")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@PathVariable long userId, @Valid @RequestBody PasswordDTO password) {
        playerService.updatePassword(userId, password);
        return ResponseEntity.ok("UserUpdatePassword");
    }

}
