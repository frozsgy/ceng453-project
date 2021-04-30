package group10.server.controller;

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
    private final String empty = "";
    private final String wrongCode = "adsgtaas";

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("User Register")
    @Order(1)
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        assertThat(playerService.register(playerDTO), notNullValue());
    }

    @Test
    @DisplayName("User Register - Empty Mail")
    void registerEmptyMailTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(empty);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("User Register - Empty Password")
    void registerEmptyPasswordTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(empty);
        playerDTO.setUsername(testUsername);
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("User Register - Empty Username")
    void registerEmptyUsernameTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(empty);
        String json = objectMapper.writeValueAsString(playerDTO);
        this.mvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("User Login with Erroneous Input")
    @Order(2)
    void loginTestErroneous() throws Exception {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        String wrongPassword = "wrongPassword";
        dto.setPassword(wrongPassword);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"error\":\"Wrong username/password\"}"));
    }

    @Test
    @DisplayName("Successful User Login")
    @Order(3)
    void loginTest() throws Exception {
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("User Login with Wrong Uname")
    @Order(4)
    void loginTestWronUname() throws Exception {
        LoginDTO dto = new LoginDTO();
        String wrongUsername = "qwe!@#";
        dto.setUsername(wrongUsername);
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"error\":\"Wrong username/password\"}"));
    }

    @Test
    @DisplayName("Correct Request Password")
    @Order(5)
    void requestPasswordTest() throws Exception {
        String correctEmailJson = "{email:" + testEmail + "}";
        this.mvc.perform(post("/api/user/requestPwCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(correctEmailJson))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Invalid Request Password")
    void requestPasswordInvalidTest() throws Exception {
        String wrongEmailJson = "{email: asdasda@gmail.com}";
        this.mvc.perform(post("/api/user/requestPwCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wrongEmailJson))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Invalid Request Password Body")
    void requestPasswordInvalidBodyTest() throws Exception {
        this.mvc.perform(post("/api/user/requestPwCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUsername))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Update Password With Wrong Code")
    void updatePasswordInvalidCode() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        dto.setResetCode(wrongCode);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Update Password Without Code")
    void updatePasswordEmptyInvalid() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        dto.setResetCode(empty);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Update Password With Empty Password")
    void updatePasswordInvalidPassword() throws Exception {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setUsername(testUsername);
        dto.setPassword(empty);
        dto.setResetCode(wrongCode);
        String json = objectMapper.writeValueAsString(dto);
        this.mvc.perform(put("/api/user/updatePassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is5xxServerError());
    }
}
