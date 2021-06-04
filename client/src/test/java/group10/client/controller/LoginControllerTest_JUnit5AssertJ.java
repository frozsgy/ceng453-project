package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest_JUnit5AssertJ {

    private Button button;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent loginScreen = fxmlLoader.load();
        Scene scene = new Scene(loginScreen);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void should_contain_button_with_text(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);
        Assertions.assertThat(login).hasText("Log In");
        // or (lookup by css id):
//        Assertions.assertThat(robot.lookup("#myButton").queryAs(Button.class)).hasText("click me!");
        // or (lookup by css class):
//        Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).hasText("click me!");
        // or (query specific type):
//        Assertions.assertThat(robot.lookup(".button").queryButton()).hasText("click me!");
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
//    @Test
    void when_button_is_clicked_text_changes(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // then:
        Assertions.assertThat(button).hasText("clicked!");
        // or (lookup by css id):
        Assertions.assertThat(robot.lookup("#myButton").queryAs(Button.class)).hasText("clicked!");
        // or (lookup by css class):
        Assertions.assertThat(robot.lookup(".button").queryAs(Button.class)).hasText("clicked!");
        // or (query specific type)
        Assertions.assertThat(robot.lookup(".button").queryButton()).hasText("clicked!");
    }
}
