package group10.client.controller;

import group10.client.constants.UiConstants;
import group10.client.enums.Cards;
import group10.client.enums.Suits;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class GameController implements Initializable {

    @FXML
    private Text levelText;
    @FXML
    private Rectangle selfCard1;
    @FXML
    private Rectangle selfCard2;
    @FXML
    private Rectangle selfCard3;
    @FXML
    private Rectangle selfCard4;
    @FXML
    private AnchorPane bottomAnchorPane;
    private int round = 1;
    private static GameController _instance;
    private Stack<Pair<Cards, Suits>> middle;
    private Stack<Pair<Cards, Suits>> allCards;
    private List<Rectangle> currentCards;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        round = 1;
        setLevelText();
        allCards = new Stack();
        this.shuffleCards();
        currentCards = new ArrayList();
        currentCards.add(selfCard1);
        currentCards.add(selfCard2);
        currentCards.add(selfCard3);
        currentCards.add(selfCard4);
        drawAllCards();
        _instance = this;
    }

    private void drawAllCards() {
        for (Rectangle r : currentCards) {
            drawCard(r);
        }
    }

    private void drawCard(Rectangle r) {
        String margin = " ";
        Pair<Cards, Suits> top = allCards.pop();
        Suits suit = top.getValue();
        Cards card = top.getKey();
        String suitName = suit.name();
        String cardName = margin + card.toString();
//        final Rectangle rectangle = new Rectangle();
        final Text text = new Text (suitName);
        final StackPane stack = new StackPane();
        final Text text2 = new Text(cardName);
        final StackPane stack2 = new StackPane();
        stack2.setAlignment(Pos.TOP_LEFT);
        stack2.getChildren().add(text2);
        stack.getChildren().addAll(r, text, stack2);
        stack.setLayoutX(r.getLayoutX());
        stack.setLayoutY(r.getLayoutY());
        stack.setPickOnBounds(false);
        stack2.setPickOnBounds(false);
        bottomAnchorPane.getChildren().add(stack);
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
                Pair<Cards, Suits> added = new Pair(card, suit);
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

    @FXML
    protected void throwCard(MouseEvent event) {

    }
}
