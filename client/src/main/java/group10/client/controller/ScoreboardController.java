package group10.client.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.model.PagedEntity;
import group10.client.model.Scoreboard;
import group10.client.service.HTTPService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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

    private Gson gson;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gson = new Gson();
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        getScoreboard(30,0);

    }

    @SuppressWarnings("unchecked")
    protected void getScoreboard(long days, long page) {
        tableView.getItems().clear();
        String scoreboardString = HTTPService.getInstance().getScoreboard(days, page);
        Type collectionType = new TypeToken<PagedEntity<Scoreboard>>(){}.getType();
        PagedEntity<Scoreboard> pagedEntity = gson.fromJson(scoreboardString, collectionType);
        tableView.getItems().addAll(pagedEntity.getContent());
        tableView.getSortOrder().addAll(scoreColumn);
    }
}
