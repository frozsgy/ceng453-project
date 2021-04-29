package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Player;
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

    public Game newGame(Player player) {
        // TODO
        System.out.println("new game request");
        return null;
    }

    public Game finishLevel(Player player, Game game) {
        // TODO
        return null;
    }


}
