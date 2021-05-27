package group10.client.controller;

import group10.client.constants.ErrorConstants;
import group10.client.model.Player;
import group10.client.service.HTTPService;
import group10.client.utility.LoadingSpinner;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ProgressIndicator registerSpinner;
    @FXML
    private AnchorPane registerMidPane;
    @FXML
    private BorderPane registerBorderPane;
    @FXML
    private StackPane registerStackPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    protected void userRegister(ActionEvent event) throws IOException {
        LoadingSpinner spinner = new LoadingSpinner(registerStackPane, registerBorderPane);
        spinner.start();
        if (validateForm()) {
            this.clearErrorMessage();
            Player player = new Player(username.getText(), password.getText(), email.getText());
            String errorMessage = HTTPService.getInstance().register(player, spinner);
//            spinner.stop();
            if (errorMessage != null && errorMessage.isEmpty()) {
                buttonBackRegister.fire(); // trigger navigation to login.
            } else {
                this.setErrorMessage(errorMessage);
            }
        } else {
            this.setErrorMessage(ErrorConstants.REGISTER_ERROR_MESSAGE);
        }

    }

    @FXML
    protected void navigateToLogin(ActionEvent event) throws IOException {
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
    public void clearErrorMessage() {
        this.registerErrorMsg.setText(ErrorConstants.EMPTY_STRING);
    }

}
