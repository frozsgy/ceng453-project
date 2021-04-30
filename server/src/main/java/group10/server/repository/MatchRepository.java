package group10.server.repository;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Repository that is used by MatchService. Allows communication with the database.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    /**
     * Returns the most recent level of a Game that was played by the given Player.
     *
     * @param player The player that played the level
     * @param game   The game that was played
     * @return Match The level
     */
    Match findFirstByPlayerAndGameOrderByUpdateDateDesc(Player player, Game game);

    /**
     * Returns the scoreboard for the recent given days.
     *
     * @param days     Number of days
     * @param pageable Pageable object for pagination
     * @return Page of Scoreboard
     */
    @Query(value = "SELECT SUM(r.score) AS Score, r.player_id AS UserId FROM rounds r " +
            "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW()) GROUP BY r.player_id ORDER BY Score DESC ",
            countQuery = "SELECT COUNT(*) FROM (SELECT SUM(r.score) AS Score, r.player_id AS UserId FROM rounds r " +
                    "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW()) GROUP BY r.player_id) k",
            nativeQuery = true)
    Page<Scoreboard> getScoreboard(@Param(value = "days") int days, Pageable pageable);

    /**
     * Finds all matches of a game by id.
     *
     * @param game Game object to find matches.
     * @return List of Matches
     */
    List<Match> findByGame(Game game);

}
