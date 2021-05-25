package group10.client.controller;

import group10.client.constants.LoginConstants;
import group10.client.constants.SharedConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginErrMsg;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void userLogIn(ActionEvent event) throws IOException {
        if (validateForm()) {
            this.clearErrorMessage();
        } else {
            this.setErrorMessage();
        }
    }

    private boolean validateForm() {
        if (username.getText().equals(SharedConstants.EMPTY_STRING) || password.getText().equals(SharedConstants.EMPTY_STRING)) {
            return false;
        }
        return true;
    }

    private void setErrorMessage() {
        loginErrMsg.setText(LoginConstants.ERROR_MESSAGE);
    }

    private void clearErrorMessage() {
        loginErrMsg.setText(SharedConstants.EMPTY_STRING);
    }
}
