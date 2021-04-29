package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.enums.GameTypes;
import group10.server.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Match newGame(Player player) {
        // TODO -- check
        Game game = new Game();
        game.setGameType(GameTypes.AI);
        Match match = new Match();
        match.setLevel(1);
        match.setGame(game);
        match.setPlayer(player);
        return match;
    }

    public Game finishLevel(Player player, Game game) {
        // TODO
        return null;
    }


}
