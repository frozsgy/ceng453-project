package group10.server.service;


import group10.server.model.LoginDTO;
import group10.server.model.PasswordResetDTO;
import group10.server.model.PlayerDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PlayerServiceTests {

    private final String testUsername = "testQASDUserX";
    private final String testPassword = "testQPASDassword";
    private final String testEmail = "tQestXASDq@gmail.com";
    private final String empty = "";


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
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> playerService.register(player));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> playerService.login(login));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> playerService.login(login));
    }

    @Test
    @DisplayName("Login Before Register")
    @Transactional
    void loginBeforeRegister() {
        LoginDTO login = new LoginDTO();
        login.setPassword(testPassword);
        login.setUsername(testUsername);
        Assertions.assertThrows(IllegalArgumentException.class, () -> playerService.login(login));
    }
    @Test
    @DisplayName("Correct Request Password Code")
    void requestPasswordTest() throws Exception {
        register();
        String correctEmailJson = "{email:" + testEmail + "}";
        Assertions.assertTrue(playerService.requestPwCode(new JSONObject(correctEmailJson)));
    }

    @Test
    @DisplayName("Incorrect Request Password Code")
    void requestPasswordIncorrect() throws Exception {
        register();
        String emailJson = "{email:" + "asddsa" + "}";
        Assertions.assertTrue(playerService.requestPwCode(new JSONObject(emailJson)));
    }

    @Test
    @DisplayName("Empty Request Password Code")
    void requestPasswordEmpty() throws Exception {
        register();
        Assertions.assertTrue(playerService.requestPwCode(new JSONObject(empty)));
    }

    @Test
    @DisplayName("Update Password Without Code")
    void updatePasswordEmptyInvalid()  {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        dto.setResetCode(empty);
        Assertions.assertTrue(playerService.updatePassword(dto));
    }
}
