package group10.server.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.MatchDTO;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:integration-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameControllerTests {

    private final String testUsername = "testUser2";
    private final String testPassword = "testPassword";
    private final String testEmail = "test2@gmail.com";
    private static String token;
    private static long gameId = -1;
    private static int scorePerMatch = 42;
    private static long userId = -1;

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
        Player registered = playerService.register(playerDTO);
        assertThat(registered, notNullValue());
        userId = registered.getId();
        LoginDTO dto = new LoginDTO();
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        String json = objectMapper.writeValueAsString(dto);
        token = this.mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Create New Game")
    @Order(2)
    void newGameTest() throws Exception {
        ResultActions newGame = this.mvc.perform(get("/api/game/new").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String contentAsString = newGame.andReturn().getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        gameId = json.getJSONObject("game").getLong("id");
    }

    @Test
    @DisplayName("Play for 3 rounds")
    @Order(2)
    void nextGameTest() throws Exception {
        MatchDTO dto = new MatchDTO();
        dto.setGame(gameId);
        for (int i = 0; i < 3; i++) {
            dto.setScore((i + 1) * scorePerMatch);
            String json = objectMapper.writeValueAsString(dto);
            String authorization = this.mvc.perform(post("/api/game/next").contentType(MediaType.APPLICATION_JSON).content(json)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            JSONObject jsonResponse = new JSONObject(authorization);
            JSONObject returnedGame = jsonResponse.getJSONObject("game");
            long returnedGameId = returnedGame.getLong("id");
            int returnedLevel = jsonResponse.getInt("level");
            int returnedScore = jsonResponse.getInt("score");
            assertEquals(returnedGameId, gameId);
            assertEquals(returnedLevel, i + 2);
            assertEquals(returnedScore, 0);
        }
    }

    @Test
    @DisplayName("Play final round")
    @Order(3)
    void finalRoundTest() throws Exception {
        MatchDTO dto = new MatchDTO();
        dto.setGame(gameId);
        dto.setScore(4 * scorePerMatch);
        String json = objectMapper.writeValueAsString(dto);
        String authorization = this.mvc.perform(post("/api/game/next").contentType(MediaType.APPLICATION_JSON).content(json)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(authorization);
        JSONObject returnedGame = jsonResponse.getJSONObject("game");
        long returnedGameId = returnedGame.getLong("id");
        int returnedLevel = jsonResponse.getInt("level");
        int returnedScore = jsonResponse.getInt("score");
        assertEquals(returnedLevel, 4);
        assertEquals(returnedScore, scorePerMatch * 4);
    }

    @Test
    @DisplayName("Scoreboard per Game")
    @Order(4)
    void testGameScoreboard() throws Exception {
        String authorization = this.mvc.perform(get("/api/game/scoreboard/game/" + gameId).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(authorization);
        for (int i = 1; i < 5; i++) {
            assertEquals(jsonResponse.getLong(String.valueOf(i)), scorePerMatch * i);
        }

    }

    @Test
    @DisplayName("General Scoreboard")
    @Order(5)
    void testGeneralScoreboard() throws Exception {
        String authorization = this.mvc.perform(get("/api/game/scoreboard/30").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(authorization);
        JSONArray scoreboard = jsonResponse.getJSONArray("content");
        int numberOfElements = jsonResponse.getInt("numberOfElements");
        assertEquals(numberOfElements, 1);
        JSONObject scoreboardOne = scoreboard.getJSONObject(0);
        assertEquals(scoreboardOne.getLong("userId"), userId);
        assertEquals(scoreboardOne.getLong("score"), 54 * scorePerMatch);
    }

}
