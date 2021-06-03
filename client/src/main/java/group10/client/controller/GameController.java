package group10.client.controller;

import group10.client.constants.GameConstants;
import group10.client.constants.UiConstants;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.logic.GameLogic;
import group10.client.model.Card;
import group10.client.utility.UIUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

import static group10.client.constants.GameConstants.LAST_ROUND;
import static group10.client.constants.GameConstants.MAX_SCORE;
import static group10.client.constants.UiConstants.*;

@Component
public class GameController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @FXML
    private Text levelText;
    @FXML
    private Text yourScore;
    @FXML
    private Text enemyScore;
    @FXML
    private Text midCartCount;
    @FXML
    private Button leaveButton;
    @FXML
    private Button challengeButton;
    private Rectangle selfCard1;
    private Rectangle selfCard2;
    private Rectangle selfCard3;
    private Rectangle selfCard4;
    private Rectangle opponentCard1;
    private Rectangle opponentCard2;
    private Rectangle opponentCard3;
    private Rectangle opponentCard4;
    @FXML
    private AnchorPane bottomAnchorPane;
    @FXML
    private AnchorPane upperAnchorPane;
    @FXML
    private StackPane midStack;
    private int round = 1;
    public static GameController _instance;
    private Stack<Card> allCards;
    private List<Rectangle> currentCards;
    private List<Rectangle> opponentCards;
    private Map<Rectangle, Card> cardMappings;
    private boolean thirdLevelScorePosted;
    private boolean bluffed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameLogic.getInstance();
        _instance = this;
        this.thirdLevelScorePosted = false;
        this.leaveButton.setFocusTraversable(false);
        this.round = 0;
        this.setUpNextLevel(); // set up level 1
        this.bluffed = false;
    }

    private void initOpponentCards() {
        this.opponentCards = new ArrayList<>();
        this.opponentCard1 = createCardRectangle(true);
        this.opponentCard2 = createCardRectangle(true);
        this.opponentCard3 = createCardRectangle(true);
        this.opponentCard4 = createCardRectangle(true);
        this.opponentCards.add(opponentCard1);
        this.opponentCards.add(opponentCard2);
        this.opponentCards.add(opponentCard3);
        this.opponentCards.add(opponentCard4);
        for (int i = 0; i < opponentCards.size(); i++) {
            try{
                upperAnchorPane.getChildren().add(opponentCards.get(i));
            } catch (IllegalArgumentException e) {
                LOGGER.info("First initialization of enemy card " + (i + 1));
            }
            opponentCards.get(i).setLayoutX(LEFTMOST_CARD_X + i * HORIZONTAL_CARD_SPACING);
            opponentCards.get(i).setLayoutY(UiConstants.ENEMY_CARD_Y);
        }
    }

    private void initPlayerCards() {
        this.currentCards = new ArrayList<>();
        selfCard1 = createCardRectangle(false);
        selfCard2 = createCardRectangle(false);
        selfCard3 = createCardRectangle(false);
        selfCard4 = createCardRectangle(false);
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

    private Rectangle createCardRectangle(boolean hasImage) {
        Rectangle card = new Rectangle();
        card.setArcHeight(5.0);
        card.setArcWidth(5.0);
        card.setHeight(114.0);
        card.setWidth(117.0);
        card.setStroke(BLACK);
        card.setStrokeType(StrokeType.valueOf("INSIDE"));
        card.setFill(WHITE);
        if (hasImage) {
            Image img = new Image(CARD_BACK_IMAGE);
            card.setFill(new ImagePattern(img));
        }
        return card;
    }

    private void initStack() {
        Image img = new Image(CARD_BACK_IMAGE);
        midStack.getChildren().clear();
        for (int i = 0; i < GameConstants.CARD_PER_HAND; i++) {
            Card card = allCards.pop();
            GameLogic.getInstance().getMiddle().add(card);
            Rectangle rec = createCardRectangle(false);
            if (i + 1 != GameConstants.CARD_PER_HAND) {
                rec.setFill(new ImagePattern(img));
                midStack.getChildren().add(rec);
            } else {
                StackPane stackPane = drawCardInsideRectangle(rec, card);
                midStack.getChildren().add(stackPane);
            }
            this.cardMappings.put(rec, card);
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
            drawCardForPlayer(r, top);
            this.cardMappings.put(r, top);
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

    private boolean isCardHidden(Rectangle r) {
        return !r.getFill().equals(WHITE);
    }

    private void setRectangleVisible(Rectangle r) {
        r.setFill(WHITE);
    }

    private StackPane drawCardInsideRectangle(Rectangle r, Card cardToDraw) {
        String margin = " ";
        Suits suit = cardToDraw.getSuit();
        Cards card = cardToDraw.getCard();
        String suitName = "";
        String cardName = "";
        if (!isCardHidden(r)) {
            // if not white, hide details.
            suitName = suit.name();
            cardName = margin + card.toString();
        }
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

    private void drawCardForPlayer(Rectangle r, Card cardToDraw) {
        StackPane stack = this.drawCardInsideRectangle(r, cardToDraw);
        bottomAnchorPane.getChildren().add(stack);
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

    private void setMidCount() {
        String text = "Mid Count: ";
        midCartCount.setText(text + GameLogic.getInstance().getMiddle().size());
    }

    private void clearView() {
        this.bottomAnchorPane.getChildren().clear();
        this.upperAnchorPane.getChildren().clear();
        this.midStack.getChildren().clear();
    }

    private void setUpNextLevel() {
        if (round == LAST_ROUND && !thirdLevelScorePosted) {
            thirdLevelScorePosted = true;
            // TODO post score here in a seperate method!

        } else if (round < LAST_ROUND) {
            // TODO post score here in a seperate method!
            round++;
            GameLogic.getInstance().resetFields();
            setLevelText();
            this.clearView();
            this.setEnemyScore(0);
            this.setPlayerScore(0);
            this.initOpponentCards();
            this.shuffleCards();
            this.initPlayerCards();
            this.initStack();
            this.drawAllCards();
            GameLogic.getInstance().setAiStrategy(round);
            this.setMidCount();
        }

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
                int resetEnemyScoreTo = 0;
                GameLogic.getInstance().getScores().replace(PlayerEnum.ONE, MAX_SCORE);
                GameLogic.getInstance().getScores().replace(PlayerEnum.TWO, resetEnemyScoreTo);
                if (_instance.round == LAST_ROUND) {
                    _instance.setUpNextLevelWrapper();
                    _instance.setPlayerScore(MAX_SCORE);
                    _instance.setEnemyScore(resetEnemyScoreTo);
                    _instance.clearView();
                    GameLogic.getInstance().getMiddle().clear();
                    _instance.setMidCount();
                } else {
                    _instance.setUpNextLevel();
                }
            }
        }
    }

    @FXML
    protected void navigateToHome(ActionEvent e) {
        URL resource = getClass().getResource(UiConstants.MENU_FXML);
        Scene menu = UIUtility.navigateTo(e, resource, null);
    }

    private void setUpNextLevelWrapper() {
        if (this.round < LAST_ROUND) {
            Button button = new Button("Next Level");
            button.setOnAction(e -> this.setUpNextLevel());
            this.midStack.getChildren().add(button);
        } else if (this.round == LAST_ROUND) {
            Text gameOver = new Text("Game Over");
            // send score here.
            this.midStack.getChildren().add(gameOver);
        }
    }

    @FXML
    private void acceptChallenge(ActionEvent e) {
        System.out.println("Challenge Accepted");
        this.challengeButton.setVisible(false);
        this.bluffed = false;
        Card bluffed = GameLogic.getInstance().getMiddle().pop();
        Card candidate = GameLogic.getInstance().getMiddle().pop();
        if (candidate.getCard() == bluffed.getCard()) {
            this.midStack.getChildren().clear();
            GameLogic.getInstance().getMiddle().clear();
            this.setMidCount();
            GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, GameLogic.DOUBLE_PISTI);
            this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        } else {
            Rectangle r = GameLogic.getRectangleByCard(this.cardMappings, bluffed);
            this.setRectangleVisible(r);
            this.drawCardInsideRectangle(r, bluffed);
            GameLogic.getInstance().addScoreToPlayer(PlayerEnum.ONE, GameLogic.DOUBLE_PISTI);
            this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
            GameLogic.getInstance().getMiddle().push(candidate);
            GameLogic.getInstance().getMiddle().push(bluffed);
        }

    }

    private void controlPlayer(Rectangle pressed) {
        midStack.getChildren().add(pressed.getParent());
        Card card = this.cardMappings.get(pressed);
        if (GameLogic.getInstance().checkIfMatch(card, PlayerEnum.ONE)) {
            LOGGER.info("match - player one");
            midStack.getChildren().clear();
        } else {
            GameLogic.getInstance().getMiddle().add(card);
        }
        this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
        currentCards.remove(pressed);
        // disable clickable
        pressed.setOnMouseClicked(null);
    }

    private boolean controlOpponent() {
        boolean bluffed = false;
        Pair<Rectangle, Card> cardMap = GameLogic.getInstance().getAiStrategy().playAsComputer(cardMappings);
        Rectangle removed = cardMap.getKey();
        Card opponentCard = cardMap.getValue();
        this.upperAnchorPane.getChildren().remove(removed);
        Rectangle opponentRectangle = createCardRectangle(challengeButton.isVisible());
        drawCardInsideRectangle(opponentRectangle, opponentCard);
        midStack.getChildren().add(opponentRectangle.getParent());
        if (isCardHidden(opponentRectangle)) {
            // handle bluf
            GameLogic.getInstance().getMiddle().add(opponentCard);
            bluffed = true;
        } else {
            if (GameLogic.getInstance().checkIfMatch(opponentCard, PlayerEnum.TWO)) {
                LOGGER.info("match - player two");
                midStack.getChildren().clear();
            } else {
                GameLogic.getInstance().getMiddle().add(opponentCard);
            }
        }
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        return bluffed;
    }

    private void rejectBluff() {
        this.challengeButton.setVisible(false);
        GameLogic.getInstance().getMiddle().clear();
        this.setMidCount();
        this.midStack.getChildren().clear();
        GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, GameLogic.PISTI);
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
    }

    @FXML
    protected void throwCard(MouseEvent event) {
        /**
         * TODO
         * implement game logic. This currently updates view for the playing player.
         * We also need to make a connection between Rectangle and Cards
         */
        try {
            if (this.bluffed) {
                this.bluffed = false;
                this.rejectBluff();
            }
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            this.controlPlayer(pressed);
            // TODO -- add a pause to let the player thinks the AI is thinking
            GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
            this.bluffed = this.controlOpponent();
            this.setMidCount();
            if (GameLogic.getInstance().isHandEmpty()) {
                if (!this.allCards.isEmpty()) {
                    LOGGER.info("deal cards again");
                    this.nextHand();
                } else {
                    PlayerEnum lastWinner = GameLogic.getInstance().getLastWinner();
                    GameLogic.getInstance().giveMidStackCardsToLastWinner();
                    this.midStack.getChildren().clear();
                    this.setMidCount();
                    this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
                    this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
                    LOGGER.info("end level " + this.round);
                    // TODO -- send scores to server (gameId from GameLogic -> playerGameEntity :: gameId)
                    this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
                }
            } else if (GameLogic.getInstance().getScores().get(PlayerEnum.ONE) == MAX_SCORE) {
                // TODO -- send scores to server (gameId from GameLogic -> playerGameEntity :: gameId)
                this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
            } else if (GameLogic.getInstance().getScores().get(PlayerEnum.TWO) == MAX_SCORE) {
                // TODO -- send scores to server (gameId from GameLogic -> playerGameEntity :: gameId)
                this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
            }


        } catch (IllegalArgumentException ex) {
            LOGGER.warn("Already played");
        }
    }

    public Button getChallengeButton() {
        return challengeButton;
    }
}
