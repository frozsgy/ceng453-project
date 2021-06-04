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

import static group10.client.utility.UIUtility.centerScene;

/**
 * Controller class for Home screen.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Component
public class HomeController implements Initializable {

    /**
     * Scoreboard button.
     */
    @FXML
    private Button buttonScoreboard;
    /**
     * New game button.
     */
    @FXML
    private Button buttonNewGame;
    /**
     * Log out button.
     */
    @FXML
    private Button buttonLogout;
    /**
     * Hello message text field.
     */
    @FXML
    private Text helloText;
    /**
     * Stack pane that spinner will be attached to.
     *
     * @see LoadingSpinner
     */
    @FXML
    private StackPane homeStackPane;
    /**
     * Root border pane.
     */
    @FXML
    private BorderPane homeBorderPane;

    /**
     * Initializes the scene
     *
     * @param url            Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = SessionStorage.getInstance().getUsername();
        this.helloText.setText("Hello, " + username + "!");
        centerScene(this.homeBorderPane.getPrefWidth(), this.homeBorderPane.getPrefHeight());
    }

    /**
     * Callback method attached to buttonNewGame. Opens up a new game.
     * Starts and stops spinner when between HTTP request and response.
     *
     * @param event Event caused by buttonNewGame.
     * @see HomeController#buttonNewGame
     */
    @FXML
    protected void navigateToNewGame(ActionEvent event) {
        LoadingSpinner spinner = new LoadingSpinner(homeStackPane, homeBorderPane);
        spinner.start();
        Task<String> newGameTask = new Task<>() {
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
                        newGame.setOnKeyPressed(GameController::keyPressEvent);
                    }
                });
        new Thread(newGameTask).start();
    }

    /**
     * Callback method attached to buttonScoreboard. Sets up the scene to scoreboard.
     *
     * @param event Event caused by buttonScoreboard.
     * @see HomeController#buttonScoreboard
     */
    @FXML
    protected void navigateToScoreboard(ActionEvent event) {
        URL resource = getClass().getResource(UiConstants.SCOREBOARD_FXML);
        Scene scoreboard = UIUtility.navigateTo(event, resource, null);
    }

    /**
     * Callback method attached to buttonLogout.
     * Clears session storage and navigates user to login page.
     *
     * @param event Event caused by buttonLogout.
     * @see HomeController#buttonLogout
     */
    @FXML
    protected void logout(ActionEvent event) {
        SessionStorage.getInstance().logout();
        URL resource = getClass().getResource(UiConstants.LOGIN_FXML);
        String title = UiConstants.getInstance().getWindowTitle();
        Scene login = UIUtility.navigateTo(event, resource, title);
    }

}
