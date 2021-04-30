package group10.server.service;


import group10.server.entity.Player;
import group10.server.model.PlayerDTO;
import group10.server.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
public class PlayerServiceTests {

    private final String testUsername = "testUser";
    private final String testPassword = "testPassword";
    private final String testEmail = "test@gmail.com";
    private final String empty = "";
    private final String wrongCode = "adsgtaas";


    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Service - User Register")
    @Order(1)
    void registerTest() {
        Player player = new Player();
        player.setEmail(testEmail);
        player.setPassword(testPassword);
        player.setUsername(testUsername);
        assertThat(playerRepository.save(player), notNullValue());
    }

}
