package group10.client.controller;

import group10.client.constants.UiConstants;
import group10.client.enums.Cards;
import group10.client.enums.Suits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class GameController implements Initializable {

    @FXML
    public Text levelText;
    private int round = 1;
    private static GameController _instance;
    private Stack<Pair<Cards, Suits>> middle;
    private List<Pair<Cards, Suits>> allCards;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        round = 1;
        setLevelText();
        allCards = new ArrayList();
        this.shuffleCards();
        _instance = this;
    }

    private void shuffleCards() {
        List<Suits> suits = new ArrayList();
        List<Cards> cards = new ArrayList();
        for (Suits suit : Suits.values()) {
            suits.add(suit);
        }
        for (Cards card : Cards.values()) {
            cards.add(card);
        }
        for (Suits suit : suits) {
            for (Cards card : cards) {
                Pair<Cards, Suits> added = new Pair(suit, card);
                allCards.add(added);
            }
        }
        Collections.shuffle(allCards);
    }

    private void setLevelText() {
        String levelStr = "Level " ;
        levelText.setText(levelStr + round);
    }

    private void setUpNextLevel() {
        round++;
        setLevelText();
    }

    protected static void keyPressEvent(KeyEvent event) {
        KeyCombination ctrl9 = new KeyCodeCombination(KeyCode.DIGIT9, KeyCodeCombination.CONTROL_DOWN);
        if (ctrl9.match(event)) {
            if (_instance != null) {
                _instance.setUpNextLevel();
            }
            return;
            /**
             * TODO
             * implement hack
             */
        }
    }
}
