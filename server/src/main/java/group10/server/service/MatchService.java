package group10.server.service;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {

    private MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match getById(Player player, Game game) {
        return matchRepository.findFirstByPlayerAndGameOrderByUpdateDateDesc(player, game);
    }

}
