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
class RegisterControllerTest {

    Scene registerScene;
    Stage stage;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        URL resource = getClass().getResource(UiConstants.REGISTER_FXML);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent registerScene = fxmlLoader.load();
        Scene scene = new Scene(registerScene);
        this.registerScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }


    @Test
    @DisplayName("Username Input Active, Visible")
    void userNameInput(FxRobot robot) {
        TextField username = robot.lookup("#username").queryAs(TextField.class);
        assertEquals(username.isVisible(), true);
        assertEquals(username.isDisabled(), false);
    }

    @Test
    @DisplayName("Password Input Active, Visible")
    void pwInput(FxRobot robot) {
        PasswordField pw = robot.lookup("#password").queryAs(PasswordField.class);
        assertEquals(pw.isVisible(), true);
        assertEquals(pw.isDisabled(), false);
    }

    @Test
    @DisplayName("Email Input Active, Visible")
    void emailInput(FxRobot robot) {
        TextField email = robot.lookup("#email").queryAs(TextField.class);
        assertEquals(email.isVisible(), true);
        assertEquals(email.isDisabled(), false);
    }

    @Test
    @DisplayName("Register Button Active, Visible")
    void registerButtonView(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);
        assertEquals(register.isVisible(), true);
        assertEquals(register.isDisabled(), false);
    }

    @Test
    @DisplayName("Back Button Active, Visible")
    void backButtonView(FxRobot robot) {
        Button back = robot.lookup("#buttonBackRegister").queryAs(Button.class);
        assertEquals(back.isVisible(), true);
        assertEquals(back.isDisabled(), false);
    }

    @Test
    @DisplayName("Back Button Navigation")
    void backButtonEvent(FxRobot robot) {
        Button back = robot.lookup("#buttonBackRegister").queryAs(Button.class);
        robot.clickOn(back);
        Scene loginScene = stage.getScene();
        assertNotEquals(loginScene, registerScene);
    }

    @Test
    @DisplayName("Register Button")
    void registerButtonEvent(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);
        Text err = robot.lookup("#registerErrorMsg").queryAs(Text.class);
        robot.clickOn(register);
        assertNotEquals(err.getText(), "");
    }

    @Test
    @DisplayName("Register Stack Pane Visible, Disabled")
    void registerStackPaneView(FxRobot robot) {
        StackPane sp = robot.lookup("#registerStackPane").queryAs(StackPane.class);;
        assertEquals(sp.isDisabled(), true);
        assertEquals(sp.isVisible(), true);
    }


}
