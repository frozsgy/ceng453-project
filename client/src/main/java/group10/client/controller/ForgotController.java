package group10.client.controller;

import group10.client.constants.UiInfoConstants;
import group10.client.constants.UiConstants;
import group10.client.service.HTTPService;
import group10.client.utility.UIUtility;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ForgotController implements Initializable, FormView {

    private final static String BUTTON_EMAIL_TEXT = "Send Email";
    private final static String BUTTON_RESET_TEXT = "Change Password";
    private final static String RED_COLOR = "-fx-text-inner-color: red";
    private final static String GREEN_COLOR = "-fx-text-inner-color: green";

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("asdsadsa");
        this.isEmailSubmitted = false;
    }

    @FXML
    public void navigateToLogin(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        UIUtility.navigateTo(event, resource, null);

    }

    private boolean sendCodeRequest(String email) {
        Task requestCodeTask = new Task<Boolean>() {
            @Override
            public Boolean call() {
                return HTTPService.getInstance().sendCode(email);
            }
        };
        requestCodeTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                (EventHandler<WorkerStateEvent>) t -> {
//                    spinner.stop();
                    boolean out = (Boolean) requestCodeTask.getValue();
                    this.isEmailSubmitted = out;
                    if (out) {
                        setSuccessMessage(UiInfoConstants.EMAIL_SENT_SUCCESS);
                    } else {
                        setErrorMessage(UiInfoConstants.USER_NOT_FOUND);
                    }
                });
        new Thread(requestCodeTask).start();
    }
    @FXML
    public void submitForgot(ActionEvent event) {
        if (this.validateForm()) {
            this.setErrorMessage(UiInfoConstants.EMPTY_FIELD_ERROR_MESSAGE);
        } else {
            if (!isEmailSubmitted) {
                this.forgotSendEmailButton.setText(BUTTON_RESET_TEXT);
                this.sendCodeRequest(this.forgotEmail.getText());
            } else {
                this.forgotSendEmailButton.setText(BUTTON_EMAIL_TEXT);
                isEmailSubmitted = false;
            }
        }
    }

    @Override
    public void setErrorMessage(String msg) {

        this.forgotInfoText.setStyle(RED_COLOR);
        this.forgotInfoText.setText(msg);

    }

    @Override
    public void setSuccessMessage(String msg) {
        this.forgotInfoText.setStyle(GREEN_COLOR);
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
