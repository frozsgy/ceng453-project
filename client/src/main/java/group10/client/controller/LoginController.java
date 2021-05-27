package group10.client.controller;

import group10.client.constants.ErrorConstants;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static group10.client.constants.UiConstants.REGISTER_FXML;

@Component
public class LoginController implements Initializable, FormView {
    
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text loginErrMsg;
    @FXML
    private StackPane loginStackPane;
    @FXML
    private BorderPane loginBorderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void userLogIn(ActionEvent event) throws IOException {
        if (validateForm()) {
            LoadingSpinner spinner = new LoadingSpinner(loginStackPane, loginBorderPane);
            this.clearErrorMessage();
            Player player = new Player(username.getText(),password.getText());
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
                        } else {
                            this.setErrorMessage(ErrorConstants.LOGIN_ERROR_MESSAGE);
                        }
                    });
            new Thread(loginTask).start();
        } else {
            this.setErrorMessage(ErrorConstants.LOGIN_ERROR_MESSAGE);
        }
    }

    @FXML
    protected void navigateToRegister(ActionEvent event) {
        URL resource = getClass().getResource(REGISTER_FXML);
        UIUtility.navigateTo(event, resource, null);

    }

    @Override
    public void setErrorMessage(String msg) {
        this.loginErrMsg.setText(msg);

    }

    @Override
    public void clearErrorMessage() {
        this.loginErrMsg.setText(ErrorConstants.EMPTY_STRING);
    }
    @Override
    public boolean validateForm() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            return false;
        }
        return true;
    }
}
