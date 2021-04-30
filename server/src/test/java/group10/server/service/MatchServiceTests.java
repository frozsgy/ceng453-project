package group10.server.service;


import group10.server.entity.Match;
import group10.server.entity.Player;
import group10.server.model.PlayerDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class MatchServiceTests {

    private final String testUsername = "testUser2";
    private final String testPassword = "testPassword";
    private final String testEmail = "test2@gmail.com";
    private static long gameId = -1;
    private static int scorePerMatch = 42;
    private static Player player = null;
    private static Match match = null;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MatchService matchService;

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
    @DisplayName("Get by Match Id Test")
    @Order(3)
    void getByIdTest() {
        newGameTest();
        assertEquals(matchService.getById(player, match.getGame()), match);
    }

    @Test
    @DisplayName("General Scoreboard")
    @Order(4)
    void findByGameTest() {
        newGameTest();
        assertThat(matchService.findByGame(match.getGame()), notNullValue());
    }
}
