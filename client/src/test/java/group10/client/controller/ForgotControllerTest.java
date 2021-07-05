package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
class ForgotControllerTest {

    Scene forgotScene;
    Stage stage;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.FORGOT_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent forgotScene = fxmlLoader.load();
        Scene scene = new Scene(forgotScene);
        this.forgotScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @DisplayName("Email Input Active, Visible")
    void emailInput(FxRobot robot) {
        TextField email = robot.lookup("#forgotEmail").queryAs(TextField.class);
        assertEquals(email.isVisible(), true);
        assertEquals(email.isDisabled(), false);
    }

    @Test
    @DisplayName("Username Input Disabled, Visible")
    void usernameInput(FxRobot robot) {
        TextField email = robot.lookup("#forgotEmail").queryAs(TextField.class);
        assertEquals(email.isVisible(), true);
        assertEquals(email.isDisabled(), false);
    }

    @Test
    @DisplayName("Code Input Disabled, Visible")
    void codeInput(FxRobot robot) {
        TextField uname = robot.lookup("#forgotUsername").queryAs(TextField.class);
        assertEquals(uname.isVisible(), true);
        assertEquals(uname.isDisabled(), true);
    }

    @Test
    @DisplayName("Password Input Disabled, Visible")
    void passwordInput(FxRobot robot) {
        PasswordField pw = robot.lookup("#forgotNewPassword").queryAs(PasswordField.class);
        assertEquals(pw.isVisible(), true);
        assertEquals(pw.isDisabled(), true);
    }

    @Test
    @DisplayName("Forgot Stack Pane Visible, Disabled")
    void forgotStackPaneView(FxRobot robot) {
        StackPane sp = robot.lookup("#forgotStackPane").queryAs(StackPane.class);;
        assertEquals(sp.isDisabled(), true);
        assertEquals(sp.isVisible(), true);
    }

    @Test
    @DisplayName("Back Button Navigation")
    void backButtonEvent(FxRobot robot) {
        Button back = robot.lookup("#forgotBackButton").queryAs(Button.class);
        robot.clickOn(back);
        Scene loginScene = stage.getScene();
        assertNotEquals(loginScene, forgotScene);
    }

    @Test
    @DisplayName("Submit Button")
    void forgotButtonEvent(FxRobot robot) {
        Button submit = robot.lookup("#forgotSendEmailButton").queryAs(Button.class);
        Text err = robot.lookup("#forgotInfoText").queryAs(Text.class);
        robot.clickOn(submit);
        assertNotEquals(err.getText(), "");
    }

}
