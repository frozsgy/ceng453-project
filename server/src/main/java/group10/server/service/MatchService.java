package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Service class that is responsible of Match service. Each level of game is a match
 */
@Service
public class MatchService {

    /**
     * Auto-wired match repostiory. Responsible for interacting with match table.
     */
    private MatchRepository matchRepository;

    /**
     * Constructor for match service.
     * @param matchRepository Autowired match repository.
     */
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Gets the next match (i.e, next level).
     * @param player Player that plays the game.
     * @param game Game being played.
     * @return Next level
     * @see Match
     */
    public Match getById(Player player, Game game) {
        return matchRepository.findFirstByPlayerAndGameOrderByUpdateDateDesc(player, game);
    }

    /**
     * Gets all the played levels of the given game.
     * @param game Game whose levels are searched
     * @return List of matches (levels) of a game.
     */
    public List<Match> findByGame(Game game) {
        return matchRepository.findByGame(game);
    }

}
