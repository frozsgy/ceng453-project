package group10.client.controller;

import group10.client.constants.GameConstants;
import group10.client.constants.UiConstants;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.logic.GameLogic;
import group10.client.model.Card;
import group10.client.state.SessionStorage;
import group10.client.utility.UIUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

import static group10.client.constants.GameConstants.LAST_ROUND;
import static group10.client.constants.GameConstants.MAX_SCORE;
import static group10.client.constants.UiConstants.*;
import static group10.client.utility.UIUtility.logToScreen;

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
    @FXML
    private TextArea logArea;
    private int round = 1;
    public static GameController _instance;
    private Stack<Card> allCards;
    private List<Rectangle> currentCards;
    private List<Rectangle> opponentCards;
    private Map<Rectangle, Card> cardMappings;
    private boolean thirdLevelScorePosted;
    private boolean AiBluffed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameLogic.getInstance();
        _instance = this;
        this.thirdLevelScorePosted = false;
        this.leaveButton.setFocusTraversable(false);
        this.round = 0;
        this.setUpNextLevel(false); // set up level 1
        this.AiBluffed = false;
        this.enableAutoScroll();
    }

    private void enableAutoScroll() {
        logArea.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                logArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });
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
            try {
                upperAnchorPane.getChildren().add(opponentCards.get(i));
            } catch (IllegalArgumentException e) {
                logToScreen("First initialization of enemy card " + (i + 1), this.logArea, LOGGER);
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
            try {
                bottomAnchorPane.getChildren().add(currentCards.get(i));
            } catch (IllegalArgumentException e) {
                logToScreen("First initialization of card " + (i + 1), this.logArea, LOGGER);
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
        logToScreen("Cards were dealt for middle stack", this.logArea, LOGGER);
    }

    private void drawAllCards() {
        Map<PlayerEnum, List<Card>> playerCards = GameLogic.getInstance().getPlayerCards();
        List<Card> cardsOne = playerCards.get(PlayerEnum.ONE);
        List<Card> cardsTwo = playerCards.get(PlayerEnum.TWO);
        for (Rectangle r : currentCards) {
            // human cards
            // enable clickable
            r.setOnMouseClicked(this::mouseClickHandler);
            Card top = allCards.pop();
            cardsOne.add(top);
            drawCardForPlayer(r, top);
            this.cardMappings.put(r, top);
        }
        logToScreen("Cards were dealt for Player One", this.logArea, LOGGER);
        for (int i = 0; i < GameConstants.CARD_PER_HAND; i++) {
            Card top = allCards.pop();
            cardsTwo.add(top);
            this.cardMappings.put(opponentCards.get(i), top);
        }
        logToScreen("Cards were dealt for Player Two", this.logArea, LOGGER);
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

    private void nextLevelCleanup(boolean continued, int playerScore, int enemyScore) {
        if (!continued) {
            round++;
            GameLogic.getInstance().resetScores();
        }
        GameLogic.getInstance().resetFields();
        setLevelText();
        this.clearView();
        this.setEnemyScore(enemyScore);
        this.setPlayerScore(playerScore);
        this.initOpponentCards();
        this.shuffleCards();
        this.initPlayerCards();
        this.initStack();
        this.drawAllCards();
        GameLogic.getInstance().setAiStrategy(round);
        this.setMidCount();
    }

    private void setUpNextLevel(boolean continued) {
        int playerScore = GameLogic.getInstance().getScores().get(PlayerEnum.ONE);
        int enemyScore = GameLogic.getInstance().getScores().get(PlayerEnum.TWO);
        if (round == LAST_ROUND && !thirdLevelScorePosted) {
            if (playerScore >= MAX_SCORE || enemyScore >= MAX_SCORE) {
                thirdLevelScorePosted = true;
                GameLogic.getInstance().sendScores();
            } else {
                this.nextLevelCleanup(true, playerScore, enemyScore);
            }
        } else if (round < LAST_ROUND) {
            if (this.round != 0 && !continued) {
                GameLogic.getInstance().sendScores();
            }
            if (!continued) {
                playerScore = 0;
                enemyScore = 0;
            }
            this.nextLevelCleanup(continued, playerScore, enemyScore);
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
                    _instance.setPlayerScore(MAX_SCORE);
                    _instance.setEnemyScore(resetEnemyScoreTo);
                    _instance.clearView();
                    _instance.setUpNextLevelWrapper();
                    GameLogic.getInstance().getMiddle().clear();
                    _instance.setMidCount();
                } else {
                    _instance.setUpNextLevel(false);
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
            button.setOnAction(e -> this.setUpNextLevel(false));
            this.midStack.getChildren().add(button);
        } else if (this.round == LAST_ROUND) {
            this.challengeButton.setVisible(false);
            Text gameOver = new Text("Game Over");
            thirdLevelScorePosted = true;
            GameLogic.getInstance().sendScores();
            this.midStack.getChildren().add(gameOver);
        }
    }

    private void handleRealBlufForPlayer(PlayerEnum bluffer, Card candidate) {
        this.midStack.getChildren().clear(); // clear mid view.
        GameLogic.getInstance().getMiddle().clear(); // clear middle.
        this.setMidCount(); // update mid count.
        if (candidate.getCard() == Cards.JACK) {
            GameLogic.getInstance().addScoreToPlayer(bluffer, GameConstants.DOUBLE_PISTI); // double for jack
        }
        GameLogic.getInstance().addScoreToPlayer(bluffer, GameConstants.DOUBLE_PISTI); // give points to ai.
        if (bluffer == PlayerEnum.ONE) {
            logToScreen("Your bluff was real.", this.logArea, LOGGER);
            this.setPlayerScore(GameLogic.getInstance().getScores().get(bluffer)); // update ai score.
        } else {
            logToScreen("AI bluff was real.", this.logArea, LOGGER);
            this.setEnemyScore(GameLogic.getInstance().getScores().get(bluffer)); // update ai score.
        }
    }

    private void handleFakeBluffForPlayer(PlayerEnum bluffer, Card candidate, Card bluffed, Rectangle added) {
        if (bluffer == PlayerEnum.ONE) {
            logToScreen("Your bluff was fake.", this.logArea, LOGGER);
            GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, GameConstants.DOUBLE_PISTI); // give points to player.
            this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        } else {
            logToScreen("AI bluff was fake.", this.logArea, LOGGER);
            GameLogic.getInstance().addScoreToPlayer(PlayerEnum.ONE, GameConstants.DOUBLE_PISTI); // give points to player.
            this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE)); // update player score view.
        }
        this.midStack.getChildren().add(added.getParent());
        GameLogic.getInstance().getMiddle().push(candidate); // put things back to middle.
        GameLogic.getInstance().getMiddle().push(bluffed); // put things back to middle.
    }

    @FXML
    private void acceptChallenge(ActionEvent e) {
        logToScreen("Challenge accepted", this.logArea, LOGGER);
        this.challengeButton.setVisible(false); // destroy button.
        this.AiBluffed = false; // handle flag.
        Card bluffed = GameLogic.getInstance().getMiddle().pop(); // get closed card.
        Card candidate = GameLogic.getInstance().getMiddle().pop(); // get prev card.
        if (candidate.getCard() == bluffed.getCard()) {
            // bluff was real
            this.handleRealBlufForPlayer(PlayerEnum.TWO, candidate);
        } else {
            // bluff was fake.
            logToScreen("Bluff was fake.", this.logArea, LOGGER);
            Rectangle r = GameLogic.getRectangleByCard(this.cardMappings, bluffed); //get the rectangle of closed card.
            this.midStack.getChildren().remove(r);
            this.setRectangleVisible(r); // make rectangle visible.
            this.drawCardInsideRectangle(r, bluffed); // put text to it.
            this.handleFakeBluffForPlayer(PlayerEnum.TWO, candidate, bluffed, r);
        }
    }

    private void controlPlayer(Rectangle pressed) {
        midStack.getChildren().add(pressed.getParent()); // add to middle.
        Card card = this.cardMappings.get(pressed); // get pressed card.
        if (GameLogic.getInstance().getMiddle().isEmpty()) {
            logToScreen("Player One threw " + card + " on an empty stack", this.logArea, LOGGER);
        }
        if (GameLogic.getInstance().handleThrow(card, PlayerEnum.ONE, this.logArea)) { // check if matched
            logToScreen("Cards match for Player One", this.logArea, LOGGER);
            midStack.getChildren().clear(); // match, clear middle view.
        }
        this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
        currentCards.remove(pressed); // remove card from hand.
        // disable clickable
        pressed.setOnMouseClicked(null); // make unclickable.
    }

    private boolean controlOpponent() {
        Pair<Rectangle, Card> cardMap = GameLogic.getInstance().getAiStrategy().playAsComputer(cardMappings);
        boolean bluffed = GameLogic.getInstance().getAiStrategy().getHasBluffed();
        GameController._instance.getChallengeButton().setVisible(bluffed);
        Rectangle removed = cardMap.getKey();
        Card opponentCard = cardMap.getValue();
        this.upperAnchorPane.getChildren().remove(removed); // remove from view.
        this.cardMappings.remove(removed); // remove the mapping.
        Rectangle opponentRectangle = createCardRectangle(bluffed); // generate new view.
        this.cardMappings.put(opponentRectangle, opponentCard); // create new mapping.
        drawCardInsideRectangle(opponentRectangle, opponentCard); // put text unless it is hidden.
        midStack.getChildren().add(opponentRectangle.getParent()); // put it to mid.
        if (bluffed) {
            // handle bluff
            GameLogic.getInstance().getMiddle().add(opponentCard); // put card to bluff, do not make check.
        } else {
            if (GameLogic.getInstance().getMiddle().isEmpty()) {
                logToScreen("Player Two threw " + opponentCard + " on an empty stack", this.logArea, LOGGER);
            }
            if (GameLogic.getInstance().handleThrow(opponentCard, PlayerEnum.TWO, this.logArea)) { // no bluff, check if scored.
                logToScreen("Cards match for Player Two", this.logArea, LOGGER);
                midStack.getChildren().clear();
            }
        }
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        return bluffed;
    }

    private void rejectBluff() {
        this.challengeButton.setVisible(false); // destroy button.
        GameLogic.getInstance().getMiddle().clear(); //clear middle, ai got all cards.
        this.setMidCount(); // reset mid count.
        this.midStack.getChildren().clear(); // remove cards from view.
        GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, GameConstants.PISTI); // give score to second player.
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO)); //update score view.
    }

    @FXML
    protected void mouseClickHandler(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            this.throwCard(event);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            this.doBluff(event);
        }
    }

    private void doBluff(MouseEvent event) {
        try{
            logToScreen("You bluffed.");
            if (GameLogic.getInstance().getMiddle().size() == 1 && this.round >= LAST_ROUND) {
                Rectangle pressed = (Rectangle) ((Node) event.getTarget());
                // TODO -- bluff
                Random rand = new Random();
                int accepted = 0;
                int headsTail = rand.nextInt(2);
                this.bottomAnchorPane.getChildren().remove(pressed.getParent());
                currentCards.remove(pressed); // remove card from hand.
                if (headsTail == accepted) {
                    logToScreen("AI accepted the challenge");
                    // ai accepted the challenge.
                    Card bluffed = this.cardMappings.get(pressed);
                    Card candidate = GameLogic.getInstance().getMiddle().pop(); // get prev card.
                    if (candidate.getCard() == bluffed.getCard()) {
                        // bluff was real
                        this.handleRealBlufForPlayer(PlayerEnum.ONE, candidate);
                        this.controlOpponent();
                        this.setMidCount();
                        this.serveHand();
                    } else {
                        // bluff was fake.
                        this.handleFakeBluffForPlayer(PlayerEnum.ONE, candidate, bluffed, pressed);
                        this.controlOpponent();
                        this.setMidCount();
                        this.serveHand();
                    }

                } else {
                    // ai rejected the bluff.
                    logToScreen("AI rejected the bluff", this.logArea, LOGGER);
                    this.midStack.getChildren().clear(); // clear mid view.
                    GameLogic.getInstance().getMiddle().clear(); // clear mid.
                    GameLogic.getInstance().addScoreToPlayer(PlayerEnum.ONE, GameConstants.PISTI); // give score to first player.
                    this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE)); //update score view.
                    this.controlOpponent();
                    this.setMidCount();
                    this.serveHand();
                }
            }
        } catch (IllegalArgumentException ex) {
            logToScreen("Already played");
        }
    }

    private void serveHand() {
        if (GameLogic.getInstance().isHandEmpty()) {
            if (!this.allCards.isEmpty()) {
                logToScreen("Cards dealt", this.logArea, LOGGER);
                this.nextHand();
            } else {
                GameLogic.getInstance().giveMidStackCardsToLastWinner();
                this.midStack.getChildren().clear();
                this.setMidCount();
                int playerScore = GameLogic.getInstance().getScores().get(PlayerEnum.ONE);
                int enemyScore = GameLogic.getInstance().getScores().get(PlayerEnum.TWO);
                this.setPlayerScore(playerScore);
                this.setEnemyScore(enemyScore);
                logToScreen("Deck consumed for level " + this.round, this.logArea, LOGGER);
                if (playerScore < 151 && enemyScore < 151) {
                    logToScreen("Redealing cards", this.logArea, LOGGER);
                    this.setUpNextLevel(true);
                } else {
                    this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
                }
            }
        } else if (GameLogic.getInstance().getScores().get(PlayerEnum.ONE) == MAX_SCORE) {
            this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
        } else if (GameLogic.getInstance().getScores().get(PlayerEnum.TWO) == MAX_SCORE) {
            this.setUpNextLevelWrapper(); // adds button or text, then calls next level;
        }
    }

    private void throwCard(MouseEvent event) {
        try {
            if (this.AiBluffed) {
                this.AiBluffed = false;
                this.rejectBluff();
                logToScreen("You rejected the bluff.");
            }
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            this.controlPlayer(pressed);
            GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
            this.AiBluffed = this.controlOpponent();
            this.setMidCount();
            this.serveHand();
        } catch (IllegalArgumentException ex) {
            logToScreen("Already played");
        }
    }

    public Button getChallengeButton() {
        return challengeButton;
    }

    @FXML
    public void leaveGame(ActionEvent e) {
        logToScreen("Player " + SessionStorage.getInstance().getUsername() + " has left the game", this.logArea, LOGGER);
        GameLogic.getInstance().getScores().replace(PlayerEnum.ONE, 0);
        GameLogic.getInstance().getScores().replace(PlayerEnum.TWO, 0);
        GameLogic.getInstance().getMiddle().clear();
        for (int i = this.round; i <= LAST_ROUND; i++) {
            GameLogic.getInstance().sendScores();
        }
        this.navigateToHome(e);
    }
}
