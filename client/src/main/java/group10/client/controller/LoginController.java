package group10.client.controller;

import group10.client.constants.UiConstants;
import group10.client.constants.UiInfoConstants;
import group10.client.model.Player;
import group10.client.service.HTTPService;
import group10.client.state.SessionStorage;
import group10.client.utility.LoadingSpinner;
import group10.client.utility.UIUtility;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static group10.client.utility.UIUtility.centerScene;
/**
 * Controller class for Login screen.
 * Implements FormView interface
 * @see FormView
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Component
public class LoginController implements Initializable, FormView {

    /**
     * Login button
     */
    @FXML
    private Button buttonLogin;
    /**
     * Register button
     */
    @FXML
    private Button buttonRegister;
    /**
     * Username input text field
     */
    @FXML
    private TextField username;
    /**
     * Password input password field
     */
    @FXML
    private PasswordField password;
    /**
     * Text message to display invalid login
     */
    @FXML
    private Text loginErrMsg;
    /**
     * Stack pane that spinner is going to be attached.
     */
    @FXML
    private StackPane loginStackPane;
    /**
     * Root border bane
     */
    @FXML
    private BorderPane loginBorderPane;
    /**
     * Hyperlink to register screen.
     */
    @FXML
    private Hyperlink forgetPwForwarder;

    /**
     * Initializes the scene
     * @param url Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.setDefaultButton(true);
        centerScene(this.loginBorderPane.getPrefWidth(), this.loginBorderPane.getPrefHeight());
    }

    /**
     * Callback method of buttonLogin.
     * If form is valid, sends a login request to server and starts spinner.
     * If login is successful, navigates to home page.
     * Otherwise, displays error message.
     * @param event Event caused by buttonLogin press.
     * @see LoginController#buttonLogin
     */
    @FXML
    protected void userLogIn(ActionEvent event) {
        if (validateForm()) {
            LoadingSpinner spinner = new LoadingSpinner(loginStackPane, loginBorderPane);
            this.clearErrorMessage();
            Player player = new Player(username.getText(), password.getText());
            spinner.start();
            Task loginTask = new Task<Boolean>() {
                @Override
                public Boolean call() {
                    return HTTPService.getInstance().login(player);
                }
            };
            loginTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    (EventHandler<WorkerStateEvent>) t -> {
                        spinner.stop();
                        boolean result = (Boolean) loginTask.getValue();
                        if (result) {
                            // TODO navigation
                            URL resource = getClass().getResource(UiConstants.MENU_FXML);
                            String username = SessionStorage.getInstance().getUsername();
                            String title = UiConstants.getInstance().getWindowTitle();
                            String newTitle = title + " - " + username;
                            Scene menu = UIUtility.navigateTo(event, resource, newTitle);
                        } else {
                            this.setErrorMessage(UiInfoConstants.LOGIN_ERROR_MESSAGE);
                        }
                    });
            new Thread(loginTask).start();
        } else {
            this.setErrorMessage(UiInfoConstants.LOGIN_ERROR_MESSAGE);
        }
    }

    /**
     * Callback method for buttonRegister button. Navigates to register page.
     * @param event Event caused by buttonRegister
     */
    @FXML
    protected void navigateToRegister(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.REGISTER_FXML);
        Scene register = UIUtility.navigateTo(event, resource, null);

    }

    /**
     * Callback method for forgetPwForwarder hyperlink. Navigates to forgot password page.
     * @param event Event caused by forgetPwForwarder hyperlink
     */
    @FXML
    protected void navigateToForgot(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.FORGOT_FXML);
        Scene forgot = UIUtility.navigateTo(event, resource, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String msg) {
        this.loginErrMsg.setText(msg);

    }
    /**
     * Does not have success message as on success, it forwards user to home screen.
     */
    @Override
    public void setSuccessMessage(String msg) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearErrorMessage() {
        this.loginErrMsg.setText(UiInfoConstants.EMPTY_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateForm() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }


}
