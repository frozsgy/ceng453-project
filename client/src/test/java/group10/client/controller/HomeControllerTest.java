package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(ApplicationExtension.class)
class HomeControllerTest {

    Scene homeScene;
    Stage stage;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.MENU_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent homeScene = fxmlLoader.load();
        Scene scene = new Scene(homeScene);
        this.homeScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @DisplayName("Play Button Active, Visible")
    void playButtonActive(FxRobot robot) {
        Button p = robot.lookup("#buttonNewGame").queryAs(Button.class);
        assertEquals(p.isVisible(), true);
        assertEquals(p.isDisabled(), false);
    }

    @Test
    @DisplayName("Scoreboard Button Active, Visible")
    void scoreboardButtonActive(FxRobot robot) {
        Button sc = robot.lookup("#buttonScoreboard").queryAs(Button.class);
        assertEquals(sc.isVisible(), true);
        assertEquals(sc.isDisabled(), false);
    }

    @Test
    @DisplayName("Log out Button Active, Visible")
    void logoutButtonActive(FxRobot robot) {
        Button b = robot.lookup("#buttonLogout").queryAs(Button.class);
        assertEquals(b.isVisible(), true);
        assertEquals(b.isDisabled(), false);
    }


    @Test
    @DisplayName("Home Stack Pane Visible, Disabled")
    void forgotStackPaneView(FxRobot robot) {
        StackPane sp = robot.lookup("#homeStackPane").queryAs(StackPane.class);;
        assertEquals(sp.isDisabled(), true);
        assertEquals(sp.isVisible(), true);
    }

    @Test
    @DisplayName("Log out Button Navigation")
    void backButtonEvent(FxRobot robot) {
        Button back = robot.lookup("#buttonLogout").queryAs(Button.class);
        robot.clickOn(back);
        Scene loginScene = stage.getScene();
        assertNotEquals(loginScene, homeScene);
    }
}
