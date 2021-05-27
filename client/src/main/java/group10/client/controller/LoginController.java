package group10.client.controller;

import group10.client.constants.LoginConstants;
import group10.client.constants.SharedConstants;
import group10.client.model.Player;
import group10.client.service.HTTPService;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @Value("classpath:/fxml/register.fxml")
    private Resource registerResource;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void userLogIn(ActionEvent event) throws IOException {
        if (validateForm()) {
            this.clearErrorMessage();
            Player player = new Player(username.getText(),password.getText());
            if (HTTPService.getInstance().login(player)) {
                // TODO navigation
            } else {
                this.setErrorMessage();
            }
        } else {
            this.setErrorMessage();
        }
    }

    private boolean validateForm() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    @FXML
    protected void navigateToRegister(ActionEvent event) throws IOException{
        URL resource = getClass().getResource("/fxml/register.fxml");
        UIUtility.navigateTo(event, resource, null);
        //UIUtility.navigateTo(event, registerResource, null);

    }

    private void setErrorMessage() {
        loginErrMsg.setText(LoginConstants.ERROR_MESSAGE);
    }

    private void clearErrorMessage() {
        loginErrMsg.setText(SharedConstants.EMPTY_STRING);
    }
}
