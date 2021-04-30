package group10.server.controllertests;

import group10.server.model.PlayerDTO;
import group10.server.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:integration-test.properties")
class PlayerControllerTests {

    @Autowired
    private PlayerService playerService;

    @Test
    void registerTest() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setEmail("skjdsajkgg@gmail.com");
        playerDTO.setPassword("qwerty");
        playerDTO.setUsername("testUser");
        assertThat(playerService.register(playerDTO), notNullValue());
    }

}
