package group10.server.service;


import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.model.PlayerDTO;
import group10.server.repository.Scoreboard;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class GameServiceTests {

    private final String testUsername = "testUser2";
    private final String testPassword = "testPassword";
    private final String testEmail = "test2@gmail.com";
    private static String token;
    private static long gameId = -1;
    private static int scorePerMatch = 42;
    private static Player player = null;
    private static Match match = null;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Test
    @DisplayName("User Register")
    @Order(1)
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        player = playerService.register(playerDTO);
        assertThat(player, notNullValue());
    }

    @Test
    @DisplayName("Create New Game")
    @Order(2)
    void newGameTest() {
        registerTest();
        match = gameService.newGame(player);
        assertThat(match, notNullValue());
    }

    @Test
    @DisplayName("Play for 4 rounds")
    @Order(3)
    void nextGameTest() {
        newGameTest();
        for (int i = 0; i < 4; i++) {
            match = gameService.nextLevel(match,  (i + 1) * scorePerMatch);
            if (i != 3) {
                assertEquals(match.getLevel(), i + 2);
                assertEquals(match.getScore(), 0);
            } else {
                assertEquals(match.getLevel(), 4);
                assertEquals(match.getScore(), scorePerMatch * 4);
            }
        }
    }

    @Test
    @DisplayName("General Scoreboard")
    @Order(4)
    void testGameScoreboard() {
        nextGameTest();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Scoreboard> scoreboard = gameService.getScoreboard(30, pageable);
        assertFalse(scoreboard.getContent().isEmpty());
    }
}
