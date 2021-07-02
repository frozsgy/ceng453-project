package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.enums.GameTypes;
import group10.server.model.MatchMakingDTO;
import group10.server.repository.GameRepository;
import group10.server.repository.MatchRepository;
import group10.server.repository.Scoreboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Service class that is responsible of Games.
 */
@Service
public class GameService {

    /**
     * Auto-wired game repository. Responsible for interacting with game table.
     */
    private GameRepository gameRepository;
    /**
     * Auto-wired game repository. Responsible for interacting with match table.
     */
    private MatchRepository matchRepository;

    private Queue<MatchMakingDTO> queue;

    /**
     * Constructor for GameService
     * @param gameRepository Autowired game repository
     * @param matchRepository Autowired match repository
     */
    @Autowired
    public GameService(GameRepository gameRepository, MatchRepository matchRepository) {
        this.gameRepository = gameRepository;
        this.matchRepository = matchRepository;
        this.queue = new LinkedList<>();
    }

    /**
     * Starts a new game against AI for the given player.
     * @param player Player who is going to play the game.
     * @return New match
     * @see Match
     */
    public Match newGame(Player player) {
        Game game = new Game();
        game.setGameType(GameTypes.AI);
        gameRepository.save(game);
        Match match = new Match();
        match.setLevel(1);
        match.setGame(game);
        match.setPlayer(player);
        return matchRepository.save(match);
    }

    /**
     * Gets the next level.
     * @param oldLevel last played level
     * @param score score of old level.
     * @return The next level
     * @see Match
     */
    public Match nextLevel(Match oldLevel, int score) {
        this.finishMatch(oldLevel, score);
        if (oldLevel.getLevel() == 4) {
            return oldLevel;
        }
        Match match = new Match();
        match.setActive(true);
        match.setPlayer(oldLevel.getPlayer());
        match.setLevel(oldLevel.getLevel() + 1);
        match.setGame(oldLevel.getGame());
        return matchRepository.save(match);
    }

    /**
     * Mark the given level as played. If old level is 3,
     * it also sets the next level as against Human rather than AI.
     * @param oldLevel last level that is played.
     * @param score score of last level.
     */
    protected void finishMatch(Match oldLevel, int score) {
        Game game = oldLevel.getGame();
        oldLevel.setScore(score);
        oldLevel.setActive(false);
        if (oldLevel.getLevel() == 3) {
            game.setGameType(GameTypes.HUMAN);
        }
        gameRepository.save(game);
    }

    /**
     * Gets the game by id.
     * @param id id of the game
     * @return Game if found else null
     * @see Game
     */
    public Game getById(long id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.orElse(null);
    }

    /**
     * Gets the scoreboard with paging.
     * @param days Last n days
     * @param pageable Pageable object that is used for paging.
     * @return A page of scoreboard
     * @see Scoreboard
     */
    public Page<Scoreboard> getScoreboard(int days, Pageable pageable) {
        return matchRepository.getScoreboard(-days, pageable);
    }

    /**
     * Thread safe method that finds opponent for multiplayer level.
     * If no opponent is found, requesting player is placed to queue.
     * @param playerNetworkInfo Network information of the requesting player where player and userName fields are populated by the caller.
     * @return Opponent data if found, else null.
     */
    public MatchMakingDTO getOpponent(MatchMakingDTO playerNetworkInfo) {
        synchronized(this.queue) {
            try{
                MatchMakingDTO opponentInfo = this.queue.remove();
                return opponentInfo;
            } catch (Exception ex) {
                this.queue.add(playerNetworkInfo);
            }
            return null;
        }
    }

}
