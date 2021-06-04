package group10.client.controller;

import group10.client.constants.UiInfoConstants;
import group10.client.model.Player;
import group10.client.service.HTTPService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static group10.client.constants.UiConstants.LOGIN_FXML;
import static group10.client.utility.UIUtility.centerScene;

/**
 * Controller class for Register screen.
 * Implements FormView interface
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * @see FormView
 */
public class RegisterController implements Initializable, FormView {

    /**
     * Username text input field
     */
    @FXML
    private TextField username;
    /**
     * Password password input field
     */
    @FXML
    private PasswordField password;
    /**
     * Email text input field
     */
    @FXML
    private TextField email;
    /**
     * Button to navigate login page.
     */
    @FXML
    private Button buttonBackRegister;
    /**
     * Button to submit register form.
     */
    @FXML
    private Button buttonRegister;
    /**
     * Error message to display on unsuccessful register.
     */
    @FXML
    private Text registerErrorMsg;
    /**
     * Middle anchor pane of screen.
     */
    @FXML
    private AnchorPane registerMidPane;
    /**
     * Root border pane
     */
    @FXML
    private BorderPane registerBorderPane;
    /**
     * Stackpane to attach loading spinner.
     *
     * @see LoadingSpinner
     */
    @FXML
    private StackPane registerStackPane;

    /**
     * Initializes the scene
     *
     * @param url            Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRegister.setDefaultButton(true);
        centerScene(this.registerBorderPane.getPrefWidth(), this.registerBorderPane.getPrefHeight());
    }

    /**
     * Callback method for buttonRegister.
     * If the form is valid, submits form to server and starts spinner. Stops it when receives answer.
     * If successful register, fires buttonBackRegister event and navigates back to login.
     * If not successful, displays error message.
     *
     * @param event action event
     * @see RegisterController#navigateToLogin(ActionEvent)
     */
    @FXML
    protected void userRegister(ActionEvent event) {
        if (validateForm()) {
            LoadingSpinner spinner = new LoadingSpinner(registerStackPane, registerBorderPane);
            this.clearErrorMessage();
            Player player = new Player(username.getText(), password.getText(), email.getText());
            spinner.start();
            Task<String> registerTask = new Task<>() {
                @Override
                public String call() {
                    return HTTPService.getInstance().register(player);
                }
            };
            registerTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    (EventHandler<WorkerStateEvent>) t -> {
                        spinner.stop();
                        String errorMessage = (String) registerTask.getValue();
                        if (errorMessage != null && errorMessage.isEmpty()) {
                            buttonBackRegister.fire(); // trigger navigation to login.
                        } else {
                            setErrorMessage(errorMessage);
                        }
                    });
            new Thread(registerTask).start();
        } else {
            this.setErrorMessage(UiInfoConstants.EMPTY_FIELD_ERROR_MESSAGE);
        }
    }

    /**
     * Callback method for buttonBackRegister button. Navigates to login page.
     *
     * @param event Event caused by buttonBackRegister
     */
    @FXML
    protected void navigateToLogin(ActionEvent event) {
        URL resource = getClass().getResource(LOGIN_FXML);
        Scene login = UIUtility.navigateTo(event, resource, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateForm() {
        return !username.getText().isEmpty() && !password.getText().isEmpty() && !email.getText().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String msg) {
        this.registerErrorMsg.setText(msg);
    }

    /**
     * Not implemented as on successful registration, navigates to login page.
     */
    @Override
    public void setSuccessMessage(String msg) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearErrorMessage() {
        this.registerErrorMsg.setText(UiInfoConstants.EMPTY_STRING);
    }

}
