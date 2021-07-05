package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.annotation.Order;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.net.URL;

import static group10.client.constants.UiConstants.CARD_BACK_IMAGE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GameControllerTest {

    Scene gameScene;
    Stage stage;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.GAME_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent gameScene = fxmlLoader.load();
        Scene scene = new Scene(gameScene);
        this.gameScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @DisplayName("Level text")
    @Order(1)
    void levelText(FxRobot robot) {
        Text t = robot.lookup("#levelText").queryAs(Text.class);
        assertEquals(t.isVisible(), true);
        assertEquals(t.getText(), "Level 1");
    }

    @Test
    @DisplayName("Opponent Score")
    @Order(2)
    void opponentScore(FxRobot robot) {
        Text t = robot.lookup("#enemyScore").queryAs(Text.class);
        assertEquals(t.isVisible(), true);
        assertEquals(t.getText(), "Opponent Score: 0");
    }

    @Test
    @DisplayName("Player Score")
    @Order(3)
    void playerScore(FxRobot robot) {
        Text t = robot.lookup("#yourScore").queryAs(Text.class);
        assertEquals(t.isVisible(), true);
        assertEquals(t.getText(), "Your Score: 0");
    }

    @Test
    @DisplayName("Leave Button")
    @Order(4)
    void leaveAction(FxRobot robot) {
        Button b = robot.lookup("#leaveButton").queryAs(Button.class);
        assertEquals(b.isVisible(), true);
        assertEquals(b.isDisabled(), false);
    }

    @Test
    @DisplayName("Mid Count Text")
    @Order(5)
    void midView(FxRobot robot) {
        Text t = robot.lookup("#midCartCount").queryAs(Text.class);
        assertEquals(t.isVisible(), true);
        assertEquals(t.getText(), "Mid Count: 4");
    }

    @Test
    @DisplayName("Opponent Card View")
    @Order(6)
    void opponenCardView(FxRobot robot) {
        AnchorPane ap = robot.lookup("#upperAnchorPane").queryAs(AnchorPane.class);
        assertEquals(ap.isVisible(), true);
    }

    @Test
    @DisplayName("Log View")
    void logView(FxRobot robot) {
        TextArea ap = robot.lookup("#logArea").queryAs(TextArea.class);
        assertEquals(ap.isVisible(), true);
        assertEquals(ap.isDisabled(), false);
    }

    @Test
    @DisplayName("Opponent Card Count")
    @Order(7)
    void opponenCardCount(FxRobot robot) {
        AnchorPane ap = robot.lookup("#upperAnchorPane").queryAs(AnchorPane.class);
        assertEquals(ap.getChildren().size(), 4);
    }

    @Test
    @DisplayName("Player Card View")
    @Order(8)
    void playerCardView(FxRobot robot) {
        AnchorPane ap = robot.lookup("#bottomAnchorPane").queryAs(AnchorPane.class);
        assertEquals(ap.isVisible(), true);
    }

    @Test
    @DisplayName("Player Card Count")
    @Order(9)
    void playerCardCount(FxRobot robot) {
        AnchorPane ap = robot.lookup("#bottomAnchorPane").queryAs(AnchorPane.class);
        assertEquals(ap.getChildren().size(), 4);
    }

    @Test
    @DisplayName("Mid Card View")
    @Order(10)
    void midCardView(FxRobot robot) {
        AnchorPane s = robot.lookup("#midStack").queryAs(AnchorPane.class);
        assertEquals(s.isVisible(), true);
    }

    @Test
    @DisplayName("Mid Card Count")
    @Order(11)
    void midCardCount(FxRobot robot) {
        AnchorPane s = robot.lookup("#midStack").queryAs(AnchorPane.class);
        assertEquals(s.getChildren().size(), 4);
    }

    @Test
    @DisplayName("Challenge Button")
    @Order(12)
    void challengeButton(FxRobot robot) {
        Button b = robot.lookup("#challengeButton").queryAs(Button.class);
        assertEquals(b.isVisible(), false);
    }

    @Test
    @DisplayName("Opponent Cards Hidden")
    @Order(13)
    void cardBackImage(FxRobot robot) {
        AnchorPane ap = robot.lookup("#upperAnchorPane").queryAs(AnchorPane.class);
        ObservableList<?> cards = ap.getChildren();
        for (Node node : ap.getChildrenUnmodifiable()) {
            boolean isRect = node instanceof Rectangle;
            assertTrue(isRect);
            Rectangle r = (Rectangle) node;
            boolean hasImage = r.getFill() instanceof ImagePattern;
            assertTrue(hasImage);
        }
    }

    @Test
    @DisplayName("Player Cards Not Hidden")
    @Order(14)
    void playerCardNotHidden(FxRobot robot) {
        AnchorPane ap = robot.lookup("#bottomAnchorPane").queryAs(AnchorPane.class);
        for (Node node : ap.getChildrenUnmodifiable()) {
            StackPane sp = (StackPane) node;
            for (Node child : sp.getChildrenUnmodifiable()) {
                if (child instanceof Rectangle) {
                    Rectangle r = (Rectangle) child;
                    boolean hasImage = r.getFill() instanceof ImagePattern;
                    assertTrue(hasImage);
                    ImagePattern imagePattern = (ImagePattern) r.getFill();
                    assertFalse(imagePattern.getImage().getUrl().contains(CARD_BACK_IMAGE));
                }
            }
        }
    }

    @Test
    @DisplayName("Play Player Card")
    @Order(15)
    void playerCardPlay(FxRobot robot) {
        AnchorPane ap = robot.lookup("#bottomAnchorPane").queryAs(AnchorPane.class);
        for (Node node : ap.getChildrenUnmodifiable()) {
            StackPane sp = (StackPane) node;
            for (Node child : sp.getChildrenUnmodifiable()) {
                if (child instanceof Rectangle) {
                    Rectangle r = (Rectangle) child;
                    robot.clickOn(r);
                    assertEquals(sp.getChildren().size(), 0);
                    break;
                }
            }
            break;
        }
    }

    @Test
    @DisplayName("Game Over Screen")
    @Order(16)
    void gameOverScreen(FxRobot robot) {
        GameController._instance.gameOverScreen();
        AnchorPane s = robot.lookup("#midStack").queryAs(AnchorPane.class);
        assertEquals(s.getChildren().size(), 0);
        s = robot.lookup("#bottomAnchorPane").queryAs(AnchorPane.class);
        assertEquals(s.getChildren().size(), 0);
        s = robot.lookup("#upperAnchorPane").queryAs(AnchorPane.class);
        assertEquals(s.getChildren().size(), 0);
    }
}
