package group10.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private ApplicationContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    public GameController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("GameHome");
    }


    @GetMapping("/new")
    @ResponseBody
    public ResponseEntity<?> getNewGame() {
        // TODO
        return ResponseEntity.ok("NewGame");
    }

    @GetMapping("/natch")
    @ResponseBody
    public ResponseEntity<?> isMatched() {
        // TODO
        return ResponseEntity.ok("is matched or not");
    }

    @PostMapping("/finish")
    @ResponseBody
    public ResponseEntity<?> postEndGame() {
        // TODO
        return ResponseEntity.ok("accept game scores and such");
    }

}
