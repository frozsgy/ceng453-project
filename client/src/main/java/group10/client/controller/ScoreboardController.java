package group10.client.controller;

import group10.client.model.Scoreboard;
import group10.client.utility.SessionStorage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ScoreboardController implements Initializable {

    @FXML
    private StackPane scoreboardStackPane;
    @FXML
    private BorderPane scoreboardBorderPane;

    @FXML
    private TableView<Scoreboard> tableView;

    @FXML
    private TableColumn<Scoreboard, Long> scoreColumn;

    @FXML
    private Text titleText;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);


        ObservableList<Scoreboard> data = tableView.getItems();
        data.add(new Scoreboard(12, 123, "test"));
        data.add(new Scoreboard(132, 1233, "test2"));
        tableView.getSortOrder().addAll(scoreColumn);


    }
}
