package group10.server.service;


import group10.server.model.PlayerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class PlayerServiceTests {

    private final String testUsername = "testUserX";
    private final String testPassword = "testPassword";
    private final String testEmail = "testXq@gmail.com";
    private final String empty = "";
    private final String wrongCode = "adsgtaas";


    @Autowired
    private PlayerService playerService;

    @Test
    @DisplayName("Service - User Register")
    @Transactional
    @Order(1)
    void registerTest() {
        PlayerDTO player = new PlayerDTO();
        player.setEmail(testEmail);
        player.setPassword(testPassword);
        player.setUsername(testUsername);
        assertThat(playerService.register(player), notNullValue());
    }

}
