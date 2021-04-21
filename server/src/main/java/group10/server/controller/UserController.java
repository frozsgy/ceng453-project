package group10.server.controller;

import group10.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private ApplicationContext context;
    private UserService userService;

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

}
