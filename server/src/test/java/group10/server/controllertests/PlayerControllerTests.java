package group10.server.controllertests;

import group10.server.controller.PlayerController;
import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlayerControllerTests {

    @Autowired
    private PlayerController playerController;
    @MockBean
    private PlayerService playerService;
    @Test
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail("skjdsajk@gmail.com");
        playerDTO.setPassword("qwerty");
        playerDTO.setUsername("testUser");
        long response = -1;
        assertThat(playerService.register(playerDTO), greaterThan(response));
    }

}
