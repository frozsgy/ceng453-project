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
 * Repository that is used by MatchService. Allows communication with the database.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
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
    // TODO -- union, views, stored procedure or function
    @Query(value = "SELECT (l1.Score + 2 * l2.Score + 3 * l3.Score + 10 * l4.Score) AS Score, r.player_id AS UserId, p.username AS Username, " +
            "l1.Score AS LevelOne, l2.Score AS LevelTwo, l3.Score AS LevelThree, l4.Score AS LevelFour " +
            "FROM rounds r " +
            "JOIN player p ON r.player_id = p.id " +
            "JOIN  " +
            "(SELECT SUM(r.score) AS Score, r.player_id AS UserId " +
            "FROM rounds r  " +
            "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW())  " +
            "AND r.level = 1 " +
            "GROUP BY r.player_id) l1  " +
            "ON l1.UserId = r.player_id " +
            "JOIN  " +
            "(SELECT SUM(r.score) AS Score, r.player_id AS UserId " +
            "FROM rounds r  " +
            "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW())  " +
            "AND r.level = 2 " +
            "GROUP BY r.player_id) l2 " +
            "ON l2.UserId = r.player_id " +
            "JOIN " +
            "(SELECT SUM(r.score) AS Score, r.player_id AS UserId " +
            "FROM rounds r  " +
            "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW())  " +
            "AND r.level = 3 " +
            "GROUP BY r.player_id) l3 " +
            "ON l3.UserId = r.player_id " +
            "JOIN  " +
            "(SELECT SUM(r.score) AS Score, r.player_id AS UserId " +
            "FROM rounds r  " +
            "WHERE r.create_date >= TIMESTAMPADD(DAY, :days, NOW())  " +
            "AND r.level = 4 " +
            "GROUP BY r.player_id) l4 " +
            "ON l4.UserId = r.player_id " +
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
