package group10.client.controller;

import group10.client.constants.UiConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    Scene loginScene;
    Stage stage;
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
        this.loginScene = scene;
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }


    @Test
    @DisplayName("Login Button Text")
    void loginButtonText(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);
        Assertions.assertThat(login).hasText("Log In");

    }

    @Test
    @DisplayName("Login Button Visibility")
    void loginButtonVisibility(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);;
        assertEquals(login.isVisible(), true);
    }

    @Test
    @DisplayName("Login Button Active")
    void loginButtonActive(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);;
        assertEquals(login.isDisabled(), false);
    }

    @Test
    @DisplayName("Login Button Check Is Default")
    void loginButtonDefault(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);;
        assertEquals(login.isDefaultButton(), true);
    }

    @Test
    @DisplayName("Login Button Event")
    void loginButtonEvent(FxRobot robot) {
        Button login = robot.lookup("#buttonLogin").queryAs(Button.class);
        Text err = robot.lookup("#loginErrMsg").queryAs(Text.class);
        robot.clickOn(login);
        assertNotEquals(err.getText(), "");
    }


    @Test
    @DisplayName("Register Button Text")
    void registerButtonText(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);;
        Assertions.assertThat(register).hasText("Register");
    }

    @Test
    @DisplayName("Register Button Visibility")
    void registerButtonVisibility(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);;
        assertEquals(register.isVisible(), true);
    }

    @Test
    @DisplayName("Register Button Active")
    void registerButtonActive(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);;
        assertEquals(register.isDisabled(), false);
    }

    @Test
    @DisplayName("Register Button Navigation")
    void registerButtonEvent(FxRobot robot) {
        Button register = robot.lookup("#buttonRegister").queryAs(Button.class);
        robot.clickOn(register);
        Scene registerScene = stage.getScene();
        assertNotEquals(registerScene, loginScene);
    }

    @Test
    @DisplayName("Stack Pane Disabled")
    void stackPaneDisabled(FxRobot robot) {
        StackPane sp = robot.lookup("#loginStackPane").queryAs(StackPane.class);;
        assertEquals(sp.isDisabled(), true);
    }

    @Test
    @DisplayName("Stack Pane Visible")
    void stackPaneVisible(FxRobot robot) {
        StackPane sp = robot.lookup("#loginStackPane").queryAs(StackPane.class);;
        assertEquals(sp.isVisible(), true);
    }

    @Test
    @DisplayName("Hyperlink Visible")
    void hyperlinkVisible(FxRobot robot) {
        Hyperlink hp = robot.lookup("#forgetPwForwarder").queryAs(Hyperlink.class);;
        assertEquals(hp.isVisible(), true);
    }

    @Test
    @DisplayName("Hyperlink Enabled")
    void hyperlinkEnabled(FxRobot robot) {
        Hyperlink hp = robot.lookup("#forgetPwForwarder").queryAs(Hyperlink.class);;
        assertEquals(hp.isDisabled(), false);
    }

    @Test
    @DisplayName("Hyperlink Navigation")
    void hyperlinkEvent(FxRobot robot) {
        Hyperlink hp = robot.lookup("#forgetPwForwarder").queryAs(Hyperlink.class);;
        robot.clickOn(hp);
        Scene forgotScene = stage.getScene();
        assertNotEquals(forgotScene, loginScene);
    }

    @Test
    @DisplayName("Username Textbox Enabled")
    void unameEnabled(FxRobot robot) {
        TextField txt = robot.lookup("#username").queryAs(TextField.class);;
        assertEquals(txt.isDisabled(), false);
    }

    @Test
    @DisplayName("Username Textbox Visible")
    void unameVisible(FxRobot robot) {
        TextField txt = robot.lookup("#username").queryAs(TextField.class);;
        assertEquals(txt.isVisible(), true);
    }

    @Test
    @DisplayName("Password Textbox Enabled")
    void pwEnabled(FxRobot robot) {
        PasswordField pw = robot.lookup("#password").queryAs(PasswordField.class);;
        assertEquals(pw.isDisabled(), false);
    }

    @Test
    @DisplayName("Password Textbox Visible")
    void pwVisible(FxRobot robot) {
        PasswordField pw = robot.lookup("#password").queryAs(PasswordField.class);;
        assertEquals(pw.isVisible(), true);
    }


}
