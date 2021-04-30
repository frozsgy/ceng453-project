package group10.server.controller;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.model.MatchDTO;
import group10.server.repository.Scoreboard;
import group10.server.service.GameService;
import group10.server.service.MatchService;
import group10.server.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @GetMapping("/new")
    @ResponseBody
    public ResponseEntity<?> getNewGame() {
        Player loggedInPlayer = playerService.getLoggedInPlayer();
        LOGGER.info("New Game request from: " + loggedInPlayer.getUsername());
        return ResponseEntity.ok(gameService.newGame(loggedInPlayer));
    }

    @GetMapping("/match")
    @ResponseBody
    public ResponseEntity<?> isMatched() {
        // TODO - stub left for concurrency
        return ResponseEntity.ok(false);
    }

    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<?> nextMatch(@Valid @RequestBody MatchDTO dto) {
        int score = dto.getScore();
        long gameId = dto.getGame();
        Game game = gameService.getById(gameId);
        Player loggedInPlayer = playerService.getLoggedInPlayer();
        LOGGER.info("Next Match request from: " + loggedInPlayer.getUsername());
        Match currentMatch = matchService.getById(loggedInPlayer, game);
        return ResponseEntity.ok(gameService.nextLevel(currentMatch, score));
    }

    @GetMapping("/scoreboard/{day}")
    @ResponseBody
    public ResponseEntity<?> getScoreboard(@PathVariable int day,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Scoreboard> scoreboard = gameService.getScoreboard(day, pageable);
        return ResponseEntity.ok(scoreboard);
    }

    @GetMapping("/scoreboard/game/{id}")
    @ResponseBody
    public ResponseEntity<?> getScoreboard(@PathVariable long id) {
        Game game = gameService.getById(id);
        List<Match> rounds = matchService.findByGame(game);
        Map<Integer, Integer> collect = rounds.stream().collect(Collectors.toMap(Match::getLevel, Match::getScore));
        return ResponseEntity.ok(collect);
    }

}
