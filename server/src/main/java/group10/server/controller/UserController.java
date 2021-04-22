package group10.server.controller;

import group10.server.model.UserDTO;
import group10.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private ApplicationContext context;
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, ApplicationContext context) {
        this.context = context;
        this.userService = userService;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("UserHome");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login() {
        // TODO
        return ResponseEntity.ok("UserLogin");
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO user) {
        // TODO
        LOGGER.info("User register: " + user.getEmail());
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/logout/{userId}")
    @ResponseBody
    public ResponseEntity<?> logout(@PathVariable long userId) {
        // TODO
        return ResponseEntity.ok("UserLogout");
    }

    @GetMapping("/requestPassword/{userId}")
    @ResponseBody
    public ResponseEntity<?> requestPassword(@PathVariable long userId) {
        // TODO
        return ResponseEntity.ok("UserRequestPassword");
    }

    @PostMapping("/updatePassword/{userId}")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@PathVariable long userId) {
        // TODO
        return ResponseEntity.ok("UserUpdatePassword");
    }

}
