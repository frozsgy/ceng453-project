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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static group10.client.constants.UiConstants.LOGIN_FXML;

public class RegisterController implements Initializable, FormView {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private Button buttonBackRegister;
    @FXML
    private Button buttonRegister;
    @FXML
    private Text registerErrorMsg;
    @FXML
    private AnchorPane registerMidPane;
    @FXML
    private BorderPane registerBorderPane;
    @FXML
    private StackPane registerStackPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRegister.setDefaultButton(true);
    }

    @FXML
    protected void userRegister(ActionEvent event) {
        if (validateForm()) {
            LoadingSpinner spinner = new LoadingSpinner(registerStackPane, registerBorderPane);
            this.clearErrorMessage();
            Player player = new Player(username.getText(), password.getText(), email.getText());
            spinner.start();
            Task registerTask = new Task<String>() {
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
            this.setErrorMessage(ErrorConstants.EMPTY_FIELD_ERROR_MESSAGE);
        }
    }

    @FXML
    protected void navigateToLogin(ActionEvent event) {
        System.out.println("Going back");
        URL resource = getClass().getResource(LOGIN_FXML);
        UIUtility.navigateTo(event, resource, null);
    }

    @Override
    public boolean validateForm() {
        if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void setErrorMessage(String msg) {
        this.registerErrorMsg.setText(msg);
    }


    @Override
    public void setSuccessMessage(String msg) {

    }

    @Override
    public void clearErrorMessage() {
        this.registerErrorMsg.setText(ErrorConstants.EMPTY_STRING);
    }

}
