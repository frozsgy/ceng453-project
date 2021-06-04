package group10.client.controller;

import group10.client.constants.UiConstants;
import group10.client.constants.UiInfoConstants;
import group10.client.model.PasswordReset;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static group10.client.utility.UIUtility.centerScene;

/**
 * Controller class for Forget Password screen.
 * Implements FormView interface
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * @see FormView
 */
@Component
public class ForgotController implements Initializable, FormView {

    private boolean isEmailSubmitted;

    /**
     * Email input field
     */
    @FXML
    private TextField forgotEmail;
    /**
     * Reset code input field
     */
    @FXML
    private TextField forgotCode;
    /**
     * Username input field
     */
    @FXML
    private TextField forgotUsername;
    /**
     * New password input field
     */
    @FXML
    private PasswordField forgotNewPassword;
    /**
     * Send email button.
     */
    @FXML
    private Button forgotSendEmailButton;
    /**
     * Message text field to inform user.
     */
    @FXML
    private Text forgotInfoText;
    /**
     * Button to navigate back to login screen.
     */
    @FXML
    private Button forgotBackButton;
    /**
     * Stack pane to attach Spinner.
     *
     * @see LoadingSpinner
     */
    @FXML
    private StackPane forgotStackPane;
    /**
     * Root pane
     */
    @FXML
    private BorderPane forgotBorderPane;

    /**
     * Initializes the scene
     *
     * @param url            Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.isEmailSubmitted = false;
        forgotSendEmailButton.setDefaultButton(true);
        centerScene(this.forgotBorderPane.getPrefWidth(), this.forgotBorderPane.getPrefHeight());
    }

    /**
     * Callback method attached to forgotBackButton. Navigates to login page.
     *
     * @param event Event caused by forgotBackButton.
     */
    @FXML
    public void navigateToLogin(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        Scene login = UIUtility.navigateTo(event, resource, null);
    }

    /**
     * Upon submitting the email, sets up the scene to send reset code, username and new password.
     */
    private void makeStateTransition() {
        this.forgotSendEmailButton.setText(UiInfoConstants.BUTTON_RESET_TEXT);
        this.forgotEmail.setDisable(true);
        this.forgotCode.setDisable(false);
        this.forgotUsername.setDisable(false);
        this.forgotNewPassword.setDisable(false);
    }

    /**
     * Depending on the state, sends a reset code request, or reset code, username and new password.
     * Enables spinner before sending the request and disables it after response.
     *
     * @param resetContainer Request body container.
     * @see PasswordReset
     */
    private void sendRequest(PasswordReset resetContainer) {
        LoadingSpinner spinner = new LoadingSpinner(forgotStackPane, forgotBorderPane);
        spinner.start();
        Task<String> requestCodeTask = new Task<>() {
            @Override
            public String call() {
                if (!isEmailSubmitted) {
                    return HTTPService.getInstance().sendCode(resetContainer);
                }
                return HTTPService.getInstance().updatePassword(resetContainer);
            }
        };
        requestCodeTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                (EventHandler<WorkerStateEvent>) t -> {
                    String msg = (String) requestCodeTask.getValue();
                    if (msg.isEmpty()) {
                        if (!isEmailSubmitted) {
                            // code request completed.
                            setSuccessMessage(UiInfoConstants.EMAIL_SENT_SUCCESS);
                            this.makeStateTransition();
                            isEmailSubmitted = true;
                        } else {
                            // reset completed.
                            setSuccessMessage(UiInfoConstants.PASSWORD_RESET_SUCCESS);
                            forgotBackButton.fire(); // go back to login.
                        }
                    } else {
                        setErrorMessage(msg);
                    }
                    spinner.stop();
                });
        new Thread(requestCodeTask).start();
    }

    /**
     * Callback method for forgotSendEmailButton.
     * Sets up request body according to state and calls sendRequest method.
     *
     * @param event Event caused by forgotSendEmailButton
     * @see ForgotController#sendRequest(PasswordReset)
     */
    @FXML
    public void submitForgot(ActionEvent event) {
        if (!this.validateForm()) {
            this.setErrorMessage(UiInfoConstants.EMPTY_FIELD_ERROR_MESSAGE);
        } else {
            clearErrorMessage();
            if (!isEmailSubmitted) {
                PasswordReset emailContainer = new PasswordReset(this.forgotEmail.getText());
                this.sendRequest(emailContainer);
            } else {
                PasswordReset updateContainer = new PasswordReset(this.forgotUsername.getText(), this.forgotCode.getText(), this.forgotNewPassword.getText());
                this.sendRequest(updateContainer);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String msg) {
        this.forgotInfoText.setFill(Paint.valueOf(UiInfoConstants.RED_COLOR));
        this.forgotInfoText.setText(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSuccessMessage(String msg) {
        this.forgotInfoText.setFill(Paint.valueOf(UiInfoConstants.GREEN_COLOR));
        this.forgotInfoText.setText(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearErrorMessage() {
        this.forgotInfoText.setText(UiInfoConstants.EMPTY_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateForm() {
        if (!isEmailSubmitted) {
            // check email
            if (this.forgotEmail.getText().isEmpty()) {
                return false;
            }
        } else {
            if (this.forgotCode.getText().isEmpty() || this.forgotUsername.getText().isEmpty() || this.forgotNewPassword.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }


}
