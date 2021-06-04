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

@Component
public class ForgotController implements Initializable, FormView {

    private final static String BUTTON_EMAIL_TEXT = "Send Email";
    private final static String BUTTON_RESET_TEXT = "Change Password";
    private boolean isEmailSubmitted;

    @FXML
    private TextField forgotEmail;
    @FXML
    private TextField forgotCode;
    @FXML
    private TextField forgotUsername;
    @FXML
    private PasswordField forgotNewPassword;
    @FXML
    private Button forgotSendEmailButton;
    @FXML
    private Text forgotInfoText;
    @FXML
    private Button forgotBackButton;
    @FXML
    private StackPane forgotStackPane;
    @FXML
    private BorderPane forgotBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.isEmailSubmitted = false;
        centerScene(this.forgotBorderPane.getPrefWidth(), this.forgotBorderPane.getPrefHeight());
    }

    @FXML
    public void navigateToLogin(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        Scene login = UIUtility.navigateTo(event, resource, null);
    }

    private void makeStateTransition() {
        this.forgotSendEmailButton.setText(BUTTON_RESET_TEXT);
        this.forgotEmail.setDisable(true);
        this.forgotCode.setDisable(false);
        this.forgotUsername.setDisable(false);
        this.forgotNewPassword.setDisable(false);
    }

    private void sendRequest(PasswordReset resetContainer) {
        LoadingSpinner spinner = new LoadingSpinner(forgotStackPane, forgotBorderPane);
        spinner.start();
        Task requestCodeTask = new Task<String>() {
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

    @Override
    public void setErrorMessage(String msg) {
        this.forgotInfoText.setFill(Paint.valueOf(UiInfoConstants.RED_COLOR));
        this.forgotInfoText.setText(msg);
    }

    @Override
    public void setSuccessMessage(String msg) {
        this.forgotInfoText.setFill(Paint.valueOf(UiInfoConstants.GREEN_COLOR));
        this.forgotInfoText.setText(msg);
    }

    @Override
    public void clearErrorMessage() {
        this.forgotInfoText.setText(UiInfoConstants.EMPTY_STRING);
    }

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
