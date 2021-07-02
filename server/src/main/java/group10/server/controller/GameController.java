package group10.server.controller;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.model.MatchDTO;
import group10.server.model.MatchMakingDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * REST Endpoint class for Games.
 * All requests that are sent to /api/game is handled by
 * this class
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    /**
     * Autowired application context to connect spring.
     */
    private ApplicationContext context;
    /**
     * Autowired service responsible of games.
     * @see GameService
     */
    private GameService gameService;
    /**
     * Autowired service responsible of matches (levels)
     * @see MatchService
     */
    private MatchService matchService;
    /**
     * Autowired service responsible of players
     * @see PlayerService
     */
    private PlayerService playerService;

    /**
     * Logger to log things to standard out.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    /**
     * Constructor for game controller.
     * @param context Autowired application context
     * @param gameService Autowired game service
     * @param matchService Autowired match service
     * @param playerService Autowired player service
     */
    @Autowired
    public GameController(ApplicationContext context, GameService gameService, MatchService matchService, PlayerService playerService) {
        this.context = context;
        this.gameService = gameService;
        this.matchService = matchService;
        this.playerService = playerService;
    }

    /**
     * HTTP GET requests to /new path are served by this method.
     * Gets a new game and logs the username of the player
     * that sent the request.
     * @return HTTP200 New game as response.
     */
    @GetMapping("/new")
    @ResponseBody
    public ResponseEntity<?> getNewGame() {
        Player loggedInPlayer = playerService.getLoggedInPlayer();
        LOGGER.info("New Game request from: " + loggedInPlayer.getUsername());
        return ResponseEntity.ok(gameService.newGame(loggedInPlayer));
    }

    /**
     * HTTP POST requests to /match path are served by this method.
     * Gets the opponent network information for the multiplayer level.
     * If there is no opponent, user is placed into queue.
     * @param playerNetworkInfo Server and IP of the player that requested opponent.
     *                          This parameter is modified inside the method in a way that
     *                          playerId and userName fields are populated using the token of the user.
     * @return HTTP200 Opponent network information as HTTP response.
     */
    @PostMapping("/match")
    @ResponseBody
    public ResponseEntity<?> getOpponent(@RequestBody MatchMakingDTO playerNetworkInfo) {
        try {
            Player player = playerService.getLoggedInPlayer();
            playerNetworkInfo.setPlayer(player.getId());
            playerNetworkInfo.setUserName(player.getUsername());
            MatchMakingDTO opponentNetworkInfo = gameService.getOpponent(playerNetworkInfo);
            if (opponentNetworkInfo != null) {
                LOGGER.info(player.getId() + " matched with " + opponentNetworkInfo.getPlayer());
            } else {
                LOGGER.info(player.getId() + " is queued");
            }
            return ResponseEntity.ok(opponentNetworkInfo);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * HTTP POST requests to /next path are served by this method.
     * Logs the username of the user that sent the request to standard out.
     * @param dto HTTP Request body that contains level information.
     * @return HTTP200 with next level if request is valid, else HTTP 400 Bad request
     */
    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<?> nextMatch(@Valid @RequestBody MatchDTO dto) {
        try {
            int score = dto.getScore();
            long gameId = dto.getGame();
            Game game = gameService.getById(gameId);
            Player loggedInPlayer = playerService.getLoggedInPlayer();
            LOGGER.info("Next Match request from: " + loggedInPlayer.getUsername());
            Match currentMatch = matchService.getById(loggedInPlayer, game);
            return ResponseEntity.ok(gameService.nextLevel(currentMatch, score));
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * HTTP GET requests to /scoreboard/{day} path are served by this method.
     * @param day Scoreboard of last 'day' days
     * @param pageSize Number of score entries to be retrieved
     * @param pageNumber Page number of wanted scores. Used for paging the data.
     * @return HTTP200 scoreboard
     */
    @GetMapping("/scoreboard/{day}")
    @ResponseBody
    public ResponseEntity<?> getScoreboard(@PathVariable int day,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Scoreboard> scoreboard = gameService.getScoreboard(day, pageable);
        return ResponseEntity.ok(scoreboard);
    }

    /**
     * HTTP GET requests to /scoreboard/game/{id} path are served by this method.
     * @param id ID of the game
     * @return HTTP200 Scoreboard related to this game
     */
    @GetMapping("/scoreboard/game/{id}")
    @ResponseBody
    public ResponseEntity<?> getScoreboard(@PathVariable long id) {
        Game game = gameService.getById(id);
        List<Match> rounds = matchService.findByGame(game);
        Map<Integer, Integer> collect = rounds.stream().collect(Collectors.toMap(Match::getLevel, Match::getScore));
        return ResponseEntity.ok(collect);
    }

}
