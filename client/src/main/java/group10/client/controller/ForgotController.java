package group10.client.controller;

import javafx.event.ActionEvent;
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
    }

    @FXML
    public void navigateToLogin(ActionEvent event) {
        System.out.println("Going back");

    }

    @FXML
    public void submitForgot(ActionEvent event) {
        System.out.println("Submitting");

    }

    @Override
    public void setErrorMessage(String msg) {

    }

    @Override
    public void clearErrorMessage() {

    }

    @Override
    public boolean validateForm() {
        return false;
    }


}
