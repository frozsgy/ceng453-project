package group10.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.model.LoginDTO;
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
    private String token;

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

    public String getToken() {
        return token;
    }
}
