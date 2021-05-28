package group10.client.controller;

import com.google.gson.Gson;
import group10.client.model.Scoreboard;
import group10.client.model.ScoreboardEntry;
import group10.client.service.HTTPService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
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
    private TableView<ScoreboardEntry> tableView;

    @FXML
    private TableColumn<ScoreboardEntry, Long> scoreColumn;

    @FXML
    private Text titleText;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);

        getScoreboard(30,0);

    }

    protected void getScoreboard(long days, long page) {



        ObservableList<ScoreboardEntry> data = tableView.getItems();
        data.add(new ScoreboardEntry(12, 123, "test"));
        data.add(new ScoreboardEntry(132, 1233, "test2"));
        tableView.getSortOrder().addAll(scoreColumn);

        String scoreboardString = HTTPService.getInstance().getScoreboard(days, page);
        System.out.println((scoreboardString));
        Gson gson = new Gson();
        Scoreboard scoreboard = gson.fromJson(scoreboardString, Scoreboard.class);
        tableView.getItems().addAll(scoreboard.getContent());
        tableView.getSortOrder().addAll(scoreColumn);



    }
}
