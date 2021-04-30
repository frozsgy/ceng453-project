package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.enums.GameTypes;
import group10.server.repository.GameRepository;
import group10.server.repository.MatchRepository;
import group10.server.repository.Scoreboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;
    private MatchRepository matchRepository;

    @Autowired
    public GameService(GameRepository gameRepository, MatchRepository matchRepository) {
        this.gameRepository = gameRepository;
        this.matchRepository = matchRepository;
    }

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

    protected void finishMatch(Match oldLevel, int score) {
        Game game = oldLevel.getGame();
        oldLevel.setScore(score);
        oldLevel.setActive(false);
        if (oldLevel.getLevel() == 3) {
            game.setGameType(GameTypes.HUMAN);
        }
        gameRepository.save(game);
    }

    public Game getById(long id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.orElse(null);
    }

    public Page<Scoreboard> getScoreboard(long days, Pageable pageable) {
        return matchRepository.getScoreboard(days, pageable);
    }

}
