package group10.server.service;


import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PlayerDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PlayerServiceTests {

    private final String testUsername = "testQASDUserX";
    private final String testPassword = "testQPASDassword";
    private final String testEmail = "tQestXASDq@gmail.com";
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
        assertThat(playerService.register(player), notNullValue());
    }

    @Test
    @DisplayName("Register Same User")
    @Transactional
    @Order(2)
    void registerSameUser() {
        register();
        PlayerDTO player = new PlayerDTO();
        player.setEmail(testEmail);
        player.setPassword(testPassword);
        player.setUsername(testUsername);
        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            playerService.register(player);
        });
    }

    @Test
    @DisplayName("Login Registered User")
    @Transactional
    @Order(3)
    void login() {
        register();
        LoginDTO login = new LoginDTO();
        login.setPassword(testPassword);
        login.setUsername(testUsername);
        assertThat(playerService.login(login), notNullValue());
    }

    @Test
    @DisplayName("Invalid Password")
    @Transactional
    @Order(4)
    void loginInvalid() {
        register();
        LoginDTO login = new LoginDTO();
        login.setPassword(empty);
        login.setUsername(testUsername);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            playerService.login(login);
        });
    }

    @Test
    @DisplayName("Invalid Username")
    @Transactional
    @Order(4)
    void loginInvalidUname() {
        register();
        LoginDTO login = new LoginDTO();
        login.setPassword(testPassword);
        login.setUsername("asddsa");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            playerService.login(login);
        });
    }

    @Test
    @DisplayName("Login Before Register")
    @Transactional
    void loginBeforeRegister() {
        LoginDTO login = new LoginDTO();
        login.setPassword(testPassword);
        login.setUsername(testUsername);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            playerService.login(login);
        });
    }
    @Test
    @DisplayName("Correct Request Password")
    void requestPasswordTest() throws Exception {
        register();
        String correctEmailJson = "{email:" + testEmail + "}";
        Assertions.assertTrue(playerService.requestPwCode(new JSONObject(correctEmailJson)));
    }
}
