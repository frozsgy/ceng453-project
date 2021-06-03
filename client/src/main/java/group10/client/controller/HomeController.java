package group10.client.controller;

import com.google.gson.Gson;
import group10.client.constants.UiConstants;
import group10.client.entity.PlayerGame;
import group10.client.logic.GameLogic;
import group10.client.service.HTTPService;
import group10.client.state.SessionStorage;
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
        LoadingSpinner spinner = new LoadingSpinner(homeStackPane, homeBorderPane);
        spinner.start();
        Task newGameTask = new Task<String>() {
            @Override
            public String call() {
                return HTTPService.getInstance().startNewGame();
            }
        };
        newGameTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                (EventHandler<WorkerStateEvent>) t -> {
                    Gson gson = new Gson();
                    String newGameString = (String) newGameTask.getValue();
                    PlayerGame playerGameEntity = gson.fromJson(newGameString, PlayerGame.class);
                    GameLogic.getInstance().setPlayerGameEntity(playerGameEntity);
                    spinner.stop();
                    URL resource = getClass().getResource(UiConstants.GAME_FXML);
                    Scene newGame = UIUtility.navigateTo(event, resource, null);
                    if (newGame != null) {
                        newGame.setOnKeyPressed(e -> GameController.keyPressEvent(e));
                    }
                });
        new Thread(newGameTask).start();
    }

    @FXML
    protected void navigateToScoreboard(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.SCOREBOARD_FXML);
        Scene scoreboard = UIUtility.navigateTo(event, resource, null);
    }

    @FXML
    protected void logout(ActionEvent event) {
        SessionStorage.getInstance().logout();
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        String title = UiConstants.getInstance().getWindowTitle();
        Scene login = UIUtility.navigateTo(event, resource, title);
    }

}
