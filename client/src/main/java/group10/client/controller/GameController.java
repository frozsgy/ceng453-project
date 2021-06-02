package group10.client.controller;

import group10.client.constants.GameConstants;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.logic.GameLogic;
import group10.client.model.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class GameController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

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
    private Rectangle opponentCard1;
    @FXML
    private Rectangle opponentCard2;
    @FXML
    private Rectangle opponentCard3;
    @FXML
    private Rectangle opponentCard4;
    @FXML
    private AnchorPane bottomAnchorPane;
    @FXML
    private StackPane midStack;
    private int round = 1;
    private static GameController _instance;
    private Stack<Card> allCards;
    private List<Rectangle> currentCards;
    private Map<Rectangle, Card> cardMappings;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameLogic.getInstance();
        this.cardMappings = new HashMap<>();
        Image img = new Image("/static/card_full.png");
        this.opponentCard1.setFill(new ImagePattern(img));
        this.opponentCard2.setFill(new ImagePattern(img));
        this.opponentCard3.setFill(new ImagePattern(img));
        this.opponentCard4.setFill(new ImagePattern(img));
        this.round = 1;
        this.setLevelText();
        this.allCards = new Stack<>();
        this.shuffleCards();
        this.currentCards = new ArrayList<>();
        this.currentCards.add(selfCard1);
        this.currentCards.add(selfCard2);
        this.currentCards.add(selfCard3);
        this.currentCards.add(selfCard4);
        this.initStack();
        this.drawAllCards();
        _instance = this;
    }

    private void initStack() {
        Image img = new Image("/static/card_full.png");

        for (int i = 0; i < GameConstants.CARD_PER_HAND; i++) {
            Card top = allCards.pop();
            GameLogic.getInstance().getMiddle().push(top);
            Rectangle card = new Rectangle();
            card.setArcHeight(5.0);
            card.setArcWidth(5.0);
            card.setHeight(114.0);
            card.setWidth(117.0);
            card.setStroke(Paint.valueOf("BLACK"));
            card.setStrokeType(StrokeType.valueOf("INSIDE"));
            card.setFill(Paint.valueOf("WHITE"));
            if (i + 1 != GameConstants.CARD_PER_HAND) {
                card.setFill(new ImagePattern(img));
                midStack.getChildren().add(card);
            } else {
                StackPane stackPane = drawCardInsideRectangle(card, top);
                midStack.getChildren().add(stackPane);
            }
        }
        LOGGER.info("Cards were dealt for middle stack");
    }

    private void drawAllCards() {
        Map<PlayerEnum, List<Card>> playerCards = GameLogic.getInstance().getPlayerCards();
        List<Card> cardsOne = playerCards.get(PlayerEnum.ONE);
        List<Card> cardsTwo = playerCards.get(PlayerEnum.TWO);
        for (Rectangle r : currentCards) {
            // human cards
            // enable clickable
            r.setOnMouseClicked(this::throwCard);
            Card top = allCards.pop();
            cardsOne.add(top);
            drawCardForPlayers(r, top);
        }
        LOGGER.info("Cards were dealt for Player One");
        for (int i = 0; i < GameConstants.CARD_PER_HAND; i++) {
            Card top = allCards.pop();
            cardsTwo.add(top);
        }
        LOGGER.info("Cards were dealt for Player Two");
        GameLogic.getInstance().setPlayerCards(playerCards);
    }

    private StackPane drawCardInsideRectangle(Rectangle r, Card cardToDraw) {
        String margin = " ";
        Suits suit = cardToDraw.getSuit();
        Cards card = cardToDraw.getCard();
        String suitName = suit.name();
        String cardName = margin + card.toString();
        final Text text = new Text(suitName);
        final StackPane stack = new StackPane();
        final Text text2 = new Text(cardName);
        final StackPane stack2 = new StackPane();
        stack2.setAlignment(Pos.TOP_LEFT);
        stack2.getChildren().add(text2);
        stack.getChildren().addAll(r, text, stack2);
        stack.setLayoutX(r.getLayoutX());
        stack.setLayoutY(r.getLayoutY());
        stack2.setLayoutX(r.getLayoutX());
        stack2.setLayoutY(r.getLayoutY());
        stack.setPickOnBounds(false);
        stack2.setPickOnBounds(false);
        return stack;
    }

    private void drawCardForPlayers(Rectangle r, Card cardToDraw) {
        StackPane stack = this.drawCardInsideRectangle(r, cardToDraw);
        bottomAnchorPane.getChildren().add(stack);
        this.cardMappings.put(r, cardToDraw);
    }

    private void shuffleCards() {
        List<Suits> suits = new ArrayList<>();
        List<Cards> cards = new ArrayList<>();
        Collections.addAll(suits, Suits.values());
        Collections.addAll(cards, Cards.values());
        for (Suits suit : suits) {
            for (Cards card : cards) {
                Card added = new Card(card, suit);
                allCards.add(added);
            }
        }
        Collections.shuffle(allCards);
    }

    private void setLevelText() {
        String levelStr = "Level ";
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
        /**
         * TODO
         * implement game logic. This currently updates view for the playing player.
         * We also need to make a connection between Rectangle and Cards
         */
        try {
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            midStack.getChildren().add(pressed.getParent());
            Card card = this.cardMappings.get(pressed);
            if (GameLogic.getInstance().checkIfMatch(card, PlayerEnum.ONE)) {
                // TODO -- clean stack display
            }
            // TODO -- let other player play
            currentCards.remove(pressed);
            // TODO -- if last card, assign the mid stack to the last winning team
            // if match, save lastWinner as 1 or 2.

            // disable clickable
            pressed.setOnMouseClicked(null);
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Already played");
        }
    }

}
