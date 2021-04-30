package group10.server.service;


import group10.server.entity.Player;
import group10.server.model.PlayerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class PlayerServiceTests {

    private final String testUsername = "testQUserX";
    private final String testPassword = "testQPassword";
    private final String testEmail = "tQestXq@gmail.com";
    private final String empty = "";
    private final String wrongCode = "adsgtaas";


    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("User Register")
    @Transactional
    @Order(1)
    void register() {
        PlayerDTO player = new PlayerDTO();
        player.setEmail(testEmail);
        player.setPassword(testPassword);
        player.setUsername(testUsername);
    }
    @Test
    @DisplayName("Register Same User")
    @Transactional
    void registerInvalidEmail() {
        PlayerDTO player = new PlayerDTO();
        player.setEmail(testEmail);
        player.setPassword(testPassword);
        player.setUsername(testUsername);
        playerService.register(player);
        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            playerService.register(player);
        });
    }

}
