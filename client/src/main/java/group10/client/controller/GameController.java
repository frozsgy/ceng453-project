package group10.client.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import group10.client.constants.GameConstants;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.logic.GameLogic;
import group10.client.model.Card;
import group10.client.model.PagedEntity;
import group10.client.model.PlayerGameEntity;
import group10.client.model.Scoreboard;
import group10.client.service.HTTPService;
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
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

import static group10.client.constants.UiConstants.CARD_BACK_IMAGE;

@Component
public class GameController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private static final double LEFTMOST_CARD_X = 81;
    private static final double PLAYER_CARD_Y = 86;
    private static final double ENEMY_CARD_Y = 0;
    private static final double HORIZONTAL_CARD_SPACING = 174;
    @FXML
    private Text levelText;
    @FXML
    private Text yourScore;
    @FXML
    private Text enemyScore;
//    @FXML
    private Rectangle selfCard1;
//    @FXML
    private Rectangle selfCard2;
//    @FXML
    private Rectangle selfCard3;
//    @FXML
    private Rectangle selfCard4;
//    @FXML
    private Rectangle opponentCard1;
//    @FXML
    private Rectangle opponentCard2;
//    @FXML
    private Rectangle opponentCard3;
//    @FXML
    private Rectangle opponentCard4;
    @FXML
    private AnchorPane bottomAnchorPane;
    @FXML
    private AnchorPane upperAnchorPane;
    @FXML
    private StackPane midStack;
    private int round = 1;
    private static GameController _instance;
    private Stack<Card> allCards;
    private List<Rectangle> currentCards;
    private List<Rectangle> opponentCards;
    private Map<Rectangle, Card> cardMappings;

    private Gson gson;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameLogic.getInstance();
        _instance = this;
        this.gson = new Gson();
        this.requestNewGame();
        this.round = 0;
        this.setUpNextLevel(); // set up level 1
    }

    @SuppressWarnings("unchecked")
    private void requestNewGame() {
        String newGameString = HTTPService.getInstance().startNewGame();
        PlayerGameEntity playerGameEntity = gson.fromJson(newGameString, PlayerGameEntity.class);
        GameLogic.getInstance().setPlayerGameEntity(playerGameEntity);
    }

    private void initOpponentCards() {
        this.opponentCards = new ArrayList<>();
        Image img = new Image(CARD_BACK_IMAGE);
        this.opponentCard1 = createCardRectangle();
        this.opponentCard2 = createCardRectangle();
        this.opponentCard3 = createCardRectangle();
        this.opponentCard4 = createCardRectangle();
        this.opponentCard1.setFill(new ImagePattern(img));
        this.opponentCard2.setFill(new ImagePattern(img));
        this.opponentCard3.setFill(new ImagePattern(img));
        this.opponentCard4.setFill(new ImagePattern(img));
        this.opponentCards.add(opponentCard1);
        this.opponentCards.add(opponentCard2);
        this.opponentCards.add(opponentCard3);
        this.opponentCards.add(opponentCard4);
        for (int i = 0; i < opponentCards.size(); i++) {
            try{
                upperAnchorPane.getChildren().add(opponentCards.get(i));
            } catch (IllegalArgumentException e) {
                LOGGER.info("First initalization of enemy card " + (i + 1));
            }
            opponentCards.get(i).setLayoutX(LEFTMOST_CARD_X + i * HORIZONTAL_CARD_SPACING);
            opponentCards.get(i).setLayoutY(ENEMY_CARD_Y);
        }
    }

    private void initPlayerCards() {
        this.currentCards = new ArrayList<>();
        selfCard1 = createCardRectangle();
        selfCard2 = createCardRectangle();
        selfCard3 = createCardRectangle();
        selfCard4 = createCardRectangle();
        this.currentCards.add(selfCard1);
        this.currentCards.add(selfCard2);
        this.currentCards.add(selfCard3);
        this.currentCards.add(selfCard4);
        for (int i = 0; i < currentCards.size(); i++) {
            try{
                bottomAnchorPane.getChildren().add(currentCards.get(i));
            } catch (IllegalArgumentException e) {
                LOGGER.info("First initalization of card " + (i + 1));
            }
            currentCards.get(i).setLayoutX(LEFTMOST_CARD_X + i * HORIZONTAL_CARD_SPACING);
            currentCards.get(i).setLayoutY(PLAYER_CARD_Y);
        }
    }

    private void nextHand() {
        this.initOpponentCards();
        this.initPlayerCards();
        this.drawAllCards();
    }

    private Rectangle createCardRectangle() {
        Rectangle card = new Rectangle();
        card.setArcHeight(5.0);
        card.setArcWidth(5.0);
        card.setHeight(114.0);
        card.setWidth(117.0);
        card.setStroke(Paint.valueOf("BLACK"));
        card.setStrokeType(StrokeType.valueOf("INSIDE"));
        card.setFill(Paint.valueOf("WHITE"));
        return card;
    }

    private void initStack() {
        Image img = new Image(CARD_BACK_IMAGE);
        midStack.getChildren().clear();
        for (int i = 0; i < GameConstants.CARD_PER_HAND; i++) {
            Card top = allCards.pop();
            GameLogic.getInstance().getMiddle().push(top);
            Rectangle card = createCardRectangle();
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
            this.cardMappings.put(opponentCards.get(i), top);
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
        this.allCards = new Stack<>();
        this.cardMappings = new HashMap<>();
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
        GameLogic.getInstance().resetFields();
        setLevelText();
        this.bottomAnchorPane.getChildren().clear();
        this.upperAnchorPane.getChildren().clear();
        this.midStack.getChildren().clear();
        this.setEnemyScore(0);
        this.setPlayerScore(0);
        this.initOpponentCards();
        this.shuffleCards();
        this.initPlayerCards();
        this.initStack();
        this.drawAllCards();
        GameLogic.getInstance().setAiStrategy(round);
    }

    private void setPlayerScore(int score) {
        String text = "Your Score: ";
        this.yourScore.setText(text + score);
    }

    private void setEnemyScore(int score) {
        String text = "Opponent Score: ";
        this.enemyScore.setText(text + score);
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
            GameLogic x = GameLogic.getInstance();
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            midStack.getChildren().add(pressed.getParent());
            Card card = this.cardMappings.get(pressed);
            if (GameLogic.getInstance().checkIfMatch(card, PlayerEnum.ONE)) {
                // TODO -- clean stack display
                LOGGER.info("match - player one");
                midStack.getChildren().clear();
            }
            this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
            // TODO -- let other player play
            currentCards.remove(pressed);
            // TODO -- if last card, assign the mid stack to the last winning team
            // if match, save lastWinner as 1 or 2.
            GameLogic.getInstance().getMiddle().push(card);
            // disable clickable
            pressed.setOnMouseClicked(null);
            // TODO -- add a pause to let the player thinks the AI is thinking
            GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO); // TODO -- generalize this
            Pair<Rectangle, Card> cardMap = GameLogic.getInstance().getAiStrategy().playAsComputer(cardMappings);
            Rectangle removed = cardMap.getKey();
            Card opponentCard = cardMap.getValue();
            this.upperAnchorPane.getChildren().remove(removed);
            Rectangle opponentRectangle = createCardRectangle();
            drawCardInsideRectangle(opponentRectangle, opponentCard);
            midStack.getChildren().add(opponentRectangle.getParent());
            if (GameLogic.getInstance().checkIfMatch(opponentCard, PlayerEnum.TWO)) {
                // TODO -- clean stack display
                LOGGER.info("match - player two");
                midStack.getChildren().clear();
            }
            this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
            GameLogic.getInstance().getMiddle().push(opponentCard);
            if (GameLogic.getInstance().isHandEmpty()) {
                if (!this.allCards.isEmpty()) {
                    LOGGER.info("deal cards again");
                    this.nextHand();
                } else {
                    PlayerEnum lastWinner = GameLogic.getInstance().getLastWinner();
                    // TODO -- give mid stack to lasstWinner
                    LOGGER.info("end level " + this.round);
                    // TODO -- send scores to server (gameId from GameLogic -> playerGameEntity :: gameId)
//                    this.setUpNextLevel();
                }

            }

        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Already played");
        }
    }

}
