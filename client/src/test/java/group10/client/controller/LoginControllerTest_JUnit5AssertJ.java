package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.DisplayName;
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


    @Test
    @DisplayName("Login Page Button Texts")
    void loginCheckButtonTexts(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);
        Assertions.assertThat(login).hasText("Log In");
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);;
        Assertions.assertThat(register).hasText("Register");
    }


//    @Test
    void when_button_is_clicked_text_changes(FxRobot robot) {
        // when:
        robot.clickOn(".button");

    }
}
