package group10.client.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.entity.PagedEntity;
import group10.client.entity.Scoreboard;
import group10.client.service.HTTPService;
import group10.client.state.ScoreboardStorage;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

import static group10.client.constants.UiConstants.MENU_FXML;
import static group10.client.utility.UIUtility.centerScene;

/**
 * Controller class for Scoreboard screen.
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Component
public class ScoreboardController implements Initializable {

    /**
     * Stackpane for scoreboard
     */
    @FXML
    private StackPane scoreboardStackPane;
    /**
     * Root border pane
     */
    @FXML
    private BorderPane scoreboardBorderPane;

    /**
     * Table view for scoreboard.
     */
    @FXML
    private TableView<Scoreboard> tableView;

    /**
     * Table column for score.
     */
    @FXML
    private TableColumn<Scoreboard, Long> scoreColumn;

    /**
     * Title text
     */
    @FXML
    private Text titleText;

    /**
     * Total of entries text
     */
    @FXML
    private Text totalEntries;

    /**
     * Combobox to change page as scoreboard supports pagination.
     */
    @FXML
    private ComboBox<Integer> pageCombo;

    /**
     * Radio button to fetch 7 days score.
     */
    @FXML
    private RadioButton radio7;
    /**
     * Radio button to fetch 30 days score.
     */
    @FXML
    private RadioButton radio30;
    /**
     * Radio button to fetch all times score.
     */
    @FXML
    private RadioButton radioAll;

    /**
     * Button to navigate home page.
     */
    @FXML
    private Button backScoreboard;

    /**
     * Gson serializer
     */
    private Gson gson;
    /**
     * Toggle group for radios
     */
    private ToggleGroup group;

    /**
     * Initializes the scene
     * @param url Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.group = new ToggleGroup();
        radio7.setToggleGroup(group);
        radio30.setToggleGroup(group);
        radioAll.setToggleGroup(group);
        this.gson = new Gson();
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        PagedEntity<Scoreboard> pagedEntity = getScoreboard(30, 0);
        ScoreboardStorage.getInstance().setData(pagedEntity);
        ScoreboardStorage.getInstance().setInterval(30);
        this.updateComboboxValues();
        centerScene(this.scoreboardBorderPane.getPrefWidth(), this.scoreboardBorderPane.getPrefHeight());
    }

    /**
     * Updates the number of total entries text
     */
    private void updateItemNumbers() {
        PagedEntity<Scoreboard> pagedEntity = ScoreboardStorage.getInstance().getData();
        totalEntries.setText("Total Entries: " + pagedEntity.getTotalElements());
    }

    /**
     * Updates the combobox values and sets total entries text via updateItemNumbers
     * @see ScoreboardController#updateItemNumbers()
     */
    private void updateComboboxValues() {
        PagedEntity<Scoreboard> pagedEntity = ScoreboardStorage.getInstance().getData();
        List<Integer> pages = IntStream.range(1, (int) pagedEntity.getTotalPages() + 1).boxed().collect(Collectors.toList());
        pageCombo.getItems().clear();
        pageCombo.getItems().addAll(pages);
        pageCombo.getSelectionModel().select(0);
        this.updateItemNumbers();
    }

    /**
     * Gets scoreboard via sending HTTP request to server.
     * @param days Requested day interval, such as last 7 days
     * @param page Page number as Scoreboard supports server side pagination.
     * @return Scoreboard page
     */
    @SuppressWarnings("unchecked")
    protected PagedEntity<Scoreboard> getScoreboard(long days, long page) {
        tableView.getItems().clear();
        String scoreboardString = HTTPService.getInstance().getScoreboard(days, page);
        Type collectionType = new TypeToken<PagedEntity<Scoreboard>>() {
        }.getType();
        PagedEntity<Scoreboard> pagedEntity = gson.fromJson(scoreboardString, collectionType);
        ScoreboardStorage.getInstance().setData(pagedEntity);
        tableView.getItems().addAll(pagedEntity.getContent());
        tableView.getSortOrder().addAll(scoreColumn);
        return pagedEntity;
    }

    /**
     * Button callback function. Navigates to first page.
     * @param event Event caused by button.
     */
    @FXML
    protected void goToFirstPage(ActionEvent event) {
        pageCombo.getSelectionModel().select(0);
    }

    /**
     * Button callback function. Navigates to previous page.
     * @param event Event caused by button.
     */
    @FXML
    protected void goToPreviousPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long previousPage = currentPage == 0 ? 0 : currentPage - 1;
        pageCombo.getSelectionModel().select((int) previousPage);
    }
    /**
     * Button callback function. Navigates to next page.
     * @param event Event caused by button.
     */
    @FXML
    protected void goToNextPage(ActionEvent event) {
        long currentPage = ScoreboardStorage.getInstance().getData().getPageDetails().getPageNumber();
        long nextPage = currentPage == ScoreboardStorage.getInstance().getData().getTotalPages() - 1 ? currentPage : currentPage + 1;
        pageCombo.getSelectionModel().select((int) nextPage);
    }

    /**
     * Button callback function. Navigates to last page.
     * @param event Event caused by button.
     */
    @FXML
    protected void goToLastPage(ActionEvent event) {
        long totalPageNumber = ScoreboardStorage.getInstance().getData().getTotalPages();
        pageCombo.getSelectionModel().select((int) totalPageNumber - 1);
    }

    /**
     * Button callback function. Navigates to selected page.
     * @param event Event caused by button.
     */
    @FXML
    protected void goToPage(ActionEvent event) {
        Integer selectedPage = (Integer) pageCombo.getSelectionModel().getSelectedItem();
        if (selectedPage != null) {
            getScoreboard(ScoreboardStorage.getInstance().getInterval(), selectedPage - 1);
        }
    }

    /**
     * Radio callback function.
     * Changes the requested number of days parameter of scoreboard and fetches the selected number of days
     * Supports 7 days, 30 days and all times.
     * @param event Event caused by radio.
     */
    @FXML
    protected void changeInterval(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String radioId = selectedRadioButton.getId();
        switch (radioId) {
            case "radio7" -> ScoreboardStorage.getInstance().setInterval(7);
            case "radio30" -> ScoreboardStorage.getInstance().setInterval(30);
            default -> ScoreboardStorage.getInstance().setInterval(100 * 365);
        }
        this.updateComboboxValues();
        pageCombo.getSelectionModel().select(0);
        //getScoreboard(ScoreboardStorage.getInstance().getInterval(), 0);
    }

    /**
     * Callback method for button. Navigates to home page.
     * @param event Event caused by radio.
     */
    @FXML
    protected void navigateToHome(ActionEvent event) {
        URL resource = getClass().getResource(MENU_FXML);
        Scene home = UIUtility.navigateTo(event, resource, null);
    }
}
