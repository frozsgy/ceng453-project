package group10.client.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.constants.UiConstants;
import group10.client.model.PagedEntity;
import group10.client.model.Scoreboard;
import group10.client.service.HTTPService;
import group10.client.state.ScoreboardStorage;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @FXML
    private Text totalEntries;

    @FXML
    private ComboBox<Integer> pageCombo;

    private Gson gson;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gson = new Gson();
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        PagedEntity<Scoreboard> pagedEntity = getScoreboard(30,0);
        ScoreboardStorage.getInstance().setData(pagedEntity);
        ScoreboardStorage.getInstance().setInterval(30);
        List<Integer> pages = IntStream.range(1, (int) pagedEntity.getTotalPages() + 1).boxed().collect(Collectors.toList());
        pageCombo.getItems().addAll(pages);
        int currentPage = (int) pagedEntity.getNumber() + 1;
        pageCombo.getSelectionModel().select(Integer.valueOf(currentPage));

        totalEntries.setText("Total Entries: " + pagedEntity.getNumberOfElements());
    }

    @SuppressWarnings("unchecked")
    protected PagedEntity<Scoreboard> getScoreboard(long days, long page) {
        tableView.getItems().clear();
        String scoreboardString = HTTPService.getInstance().getScoreboard(days, page);
        Type collectionType = new TypeToken<PagedEntity<Scoreboard>>(){}.getType();
        PagedEntity<Scoreboard> pagedEntity = gson.fromJson(scoreboardString, collectionType);
        ScoreboardStorage.getInstance().setData(pagedEntity);
        tableView.getItems().addAll(pagedEntity.getContent());
        tableView.getSortOrder().addAll(scoreColumn);
        return pagedEntity;
    }

    @FXML
    protected void goToFirstPage(ActionEvent event) {
       getScoreboard(ScoreboardStorage.getInstance().getInterval(), 0);
    }

    @FXML
    protected void goToPreviousPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long previousPage = currentPage == 0 ? 0 : currentPage - 1;
        getScoreboard(ScoreboardStorage.getInstance().getInterval(), previousPage);
    }

    @FXML
    protected void goToNextPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long nextPage = currentPage == ScoreboardStorage.getInstance().getData().getTotalPages() - 1 ? currentPage : currentPage + 1;
        getScoreboard(ScoreboardStorage.getInstance().getInterval(), nextPage);
    }

    @FXML
    protected void goToLastPage(ActionEvent event) {
        long totalPageNumber = ScoreboardStorage.getInstance().getData().getTotalPages();
        getScoreboard(ScoreboardStorage.getInstance().getInterval(), totalPageNumber - 1);
    }
}
