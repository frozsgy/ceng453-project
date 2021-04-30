package group10.server.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.model.LoginDTO;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
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
class GameControllerTests {

    private final String testUsername = "testUser";
    private final String testPassword = "testPassword";
    private final String testEmail = "test@gmail.com";
    private static String token;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Register and Login before Game Tests")
    @Order(1)
    void registerTest() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail(testEmail);
        playerDTO.setPassword(testPassword);
        playerDTO.setUsername(testUsername);
        assertThat(playerService.register(playerDTO), notNullValue());
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        token = this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(token);
    }

    @Test
    @DisplayName("Create New Game")
    @Order(2)
    void newGameTest() throws Exception {
        this.mvc.perform(get("/api/game/new").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
