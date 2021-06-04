package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.annotation.Order;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class ScoreboardControllerTest_JUnit5AssertJ {

    Scene scoreboardScene;
    Stage stage;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.SCOREBOARD_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent scoreboardScene = fxmlLoader.load();
        Scene scene = new Scene(scoreboardScene);
        this.scoreboardScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @DisplayName("Scoreboard text")
    void scoreboardText(FxRobot robot) {
        Text t = robot.lookup("#titleText").queryAs(Text.class);
        assertEquals(t.isVisible(), true);
        assertEquals(t.getText(), "Scoreboard");
    }


}
