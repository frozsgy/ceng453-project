package group10.client.controller;

import group10.client.constants.UiConstants;
import group10.client.state.SessionStorage;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
public class HomeController implements Initializable {

    @FXML
    private Button buttonScoreboard;
    @FXML
    private Button buttonNewGame;
    @FXML
    private Button buttonLogout;
    @FXML
    private Text helloText;

    @FXML
    private StackPane homeStackPane;
    @FXML
    private BorderPane homeBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = SessionStorage.getInstance().getUsername();
        this.helloText.setText("Hello, " + username + "!");
    }


    @FXML
    protected void navigateToNewGame(ActionEvent event) {


    }

    @FXML
    protected void navigateToScoreboard(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.SCOREBOARD_FXML);
        UIUtility.navigateTo(event, resource, null);


    }

    @FXML
    protected void logout(ActionEvent event) {
        SessionStorage.getInstance().logout();
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        String title = UiConstants.getInstance().getWindowTitle();
        UIUtility.navigateTo(event, resource, title);
    }

}
