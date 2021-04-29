package group10.server.controller;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.model.MatchDTO;
import group10.server.service.GameService;
import group10.server.service.MatchService;
import group10.server.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private ApplicationContext context;
    private GameService gameService;
    private MatchService matchService;
    private PlayerService playerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    public GameController(ApplicationContext context, GameService gameService, MatchService matchService, PlayerService playerService) {
        this.context = context;
        this.gameService = gameService;
        this.matchService = matchService;
        this.playerService = playerService;
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("GameHome");
    }


    @GetMapping("/new")
    @ResponseBody
    public ResponseEntity<?> getNewGame() {
        Player loggedInPlayer = playerService.getLoggedInPlayer();
        LOGGER.info("New Game request from " + loggedInPlayer.getUsername());
        return ResponseEntity.ok(gameService.newGame(loggedInPlayer));
    }

    @GetMapping("/match")
    @ResponseBody
    public ResponseEntity<?> isMatched() {
        // TODO
        return ResponseEntity.ok("is matched or not");
    }

    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<?> nextMatch(@Valid @RequestBody MatchDTO dto) {
        int score = dto.getScore();
        long gameId = dto.getGame();
        Game game = gameService.getById(gameId);
        Player loggedInPlayer = playerService.getLoggedInPlayer();
        Match currentMatch = matchService.getById(loggedInPlayer, game);
        return ResponseEntity.ok(gameService.nextLevel(currentMatch, score));
    }

}
