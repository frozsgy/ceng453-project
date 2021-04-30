package group10.server.service;


import group10.server.entity.Player;
import group10.server.model.PlayerDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

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
    private static long userId = -1;

    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("Service - User Register")
    @Order(1)
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        Player player = playerService.register(playerDTO);
        assertThat(player, notNullValue());
        userId = player.getId();
    }

}
