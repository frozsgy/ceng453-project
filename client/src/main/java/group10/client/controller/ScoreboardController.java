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
import javafx.scene.control.*;
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

import static group10.client.constants.UiConstants.LOGIN_FXML;
import static group10.client.constants.UiConstants.MENU_FXML;

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

    @FXML
    private RadioButton radio7;

    @FXML
    private RadioButton radio30;

    @FXML
    private RadioButton radioAll;

    @FXML
    private Button backScoreboard;

    private Gson gson;
    private ToggleGroup group;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.group = new ToggleGroup();
        radio7.setToggleGroup(group);
        radio30.setToggleGroup(group);
        radioAll.setToggleGroup(group);
        this.gson = new Gson();
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        PagedEntity<Scoreboard> pagedEntity = getScoreboard(30,0);
        ScoreboardStorage.getInstance().setData(pagedEntity);
        ScoreboardStorage.getInstance().setInterval(30);
        this.updateComboboxValues();
    }

    private void updateItemNumbers() {
        PagedEntity<Scoreboard> pagedEntity = ScoreboardStorage.getInstance().getData();
        totalEntries.setText("Total Entries: " + pagedEntity.getTotalElements());
    }

    private void updateComboboxValues() {
        PagedEntity<Scoreboard> pagedEntity = ScoreboardStorage.getInstance().getData();
        List<Integer> pages = IntStream.range(1, (int) pagedEntity.getTotalPages() + 1).boxed().collect(Collectors.toList());
        pageCombo.getItems().clear();
        pageCombo.getItems().addAll(pages);
        pageCombo.getSelectionModel().select(0);
        this.updateItemNumbers();
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
        pageCombo.getSelectionModel().select(0);
    }

    @FXML
    protected void goToPreviousPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long previousPage = currentPage == 0 ? 0 : currentPage - 1;
        pageCombo.getSelectionModel().select((int) previousPage);
    }

    @FXML
    protected void goToNextPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long nextPage = currentPage == ScoreboardStorage.getInstance().getData().getTotalPages() - 1 ? currentPage : currentPage + 1;
        pageCombo.getSelectionModel().select((int) nextPage);
    }

    @FXML
    protected void goToLastPage(ActionEvent event) {
        long totalPageNumber = ScoreboardStorage.getInstance().getData().getTotalPages();
        pageCombo.getSelectionModel().select((int) totalPageNumber - 1);
    }

    @FXML
    protected void goToPage(ActionEvent event) {
        Integer selectedPage = (Integer) pageCombo.getSelectionModel().getSelectedItem();
        if (selectedPage != null) {
            getScoreboard(ScoreboardStorage.getInstance().getInterval(), selectedPage - 1);
        }
    }

    @FXML
    protected void changeInterval(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String radioId = selectedRadioButton.getId();
        switch (radioId) {
            case "radio7" -> ScoreboardStorage.getInstance().setInterval(7);
            case "radio30" -> ScoreboardStorage.getInstance().setInterval(30);
            default -> ScoreboardStorage.getInstance().setInterval(365); // TODO -- all time is a year or not? maybe update sql
        }
        this.updateComboboxValues();
        pageCombo.getSelectionModel().select(0);
        //getScoreboard(ScoreboardStorage.getInstance().getInterval(), 0);
    }

    @FXML
    protected void navigateToHome(ActionEvent event) {
        URL resource = getClass().getResource(MENU_FXML);
        UIUtility.navigateTo(event, resource, null);
    }
}
