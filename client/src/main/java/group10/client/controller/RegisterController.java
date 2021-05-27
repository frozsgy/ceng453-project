package group10.client.controller;

import group10.client.model.Player;
import group10.client.service.HTTPService;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static group10.client.constants.UiConstants.loginFxml;

public class RegisterController implements Initializable {

    @Value("classpath:" + loginFxml)
    private Resource loginResource;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void userRegister(ActionEvent event) throws IOException {
        System.out.println("Registering");
    }

    @FXML
    protected void navigateToLogin(ActionEvent event) throws IOException {
        System.out.println("Going back");
        URL resource = getClass().getResource(loginFxml);
        UIUtility.navigateTo(event, resource, null);
    }
}
