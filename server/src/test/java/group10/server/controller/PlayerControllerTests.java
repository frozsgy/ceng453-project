package group10.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordResetDTO;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:integration-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerControllerTests {

    private final String testUsername = "testUser";
    private final String testPassword = "testPassword";
    private final String testEmail = "test@gmail.com";
    private final static String AUTH_HEADER = "Authorization";
    private static String token;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test for User Register")
    @Order(1)
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        assertThat(playerService.register(playerDTO), notNullValue());
    }

    @Test
    @DisplayName("Test for User Register - Empty Mail")
    void registerEmptyMailTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail("");
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Test for User Register - Empty Password")
    void registerEmptyPasswordTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword("");
        playerDTO.setUsername(testUsername);
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Test for User Register - Empty Username")
    void registerEmptyUsernameTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername("");
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Test for User Login with Erroneous Input")
    @Order(2)
    void loginTestErroneous() throws Exception {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        dto.setPassword("wrongPassword");
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"error\":\"Wrong username/password\"}"));
    }

    @Test
    @DisplayName("Test for Successful User Login")
    @Order(3)
    void loginTest() throws Exception {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        this.token = this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Test for User Login with Wrong Uname")
    @Order(4)
    void loginTestWronUname() throws Exception {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("qwe!@#");
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"error\":\"Wrong username/password\"}"));
    }

    @Test
    @DisplayName("Test for Correct Request Password")
    @Order(5)
    void requestPasswordTest() throws Exception {
        this.mvc.perform(post("/api/user/requestPwCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{email:" + testEmail + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Test for Invalid Request Password")
    void requestPasswordInvalidTest() throws Exception {
        this.mvc.perform(post("/api/user/requestPwCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{email: asdasda@gmail.com}"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
    @Test
    @DisplayName("Test for Update Password With Wrong Code")
    void updatePasswordInvalidCode() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword("asdsa");
        dto.setResetCode("adsgtaas");
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Test for Update Password Without Code")
    void updatePasswordEmptyInvalid() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword("asdsa");
        dto.setResetCode("");
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Test for Update Password Without Code")
    void updatePasswordInvalidPassword() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword("");
        dto.setResetCode("adsasdsa");
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }



    public String getToken() {
        return token;
    }
    public String getTokenHeader() { return "Bearer " + token; }
}
