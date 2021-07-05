package group10.client.controller;

import com.google.gson.Gson;
import group10.client.constants.GameConstants;
import group10.client.constants.UiConstants;
import group10.client.entity.GameState;
import group10.client.entity.PlayerGame;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.logic.GameLogic;
import group10.client.model.Card;
import group10.client.model.OpponentInfo;
import group10.client.service.HTTPService;
import group10.client.service.SocketClient;
import group10.client.service.SocketServer;
import group10.client.state.SessionStorage;
import group10.client.utility.PropertiesLoader;
import group10.client.utility.UIUtility;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static group10.client.constants.GameConstants.*;
import static group10.client.constants.UiConstants.*;
import static group10.client.utility.UIUtility.*;

/**
 * Controller class for Game screen.
 * Implements Initializable interface
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * @see Initializable
 */
@Component
public class GameController implements Initializable {

    /**
     * Logger to log messages
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    /**
     * Level text field
     */
    @FXML
    private Text levelText;
    /**
     * Player one score field
     */
    @FXML
    private Text yourScore;
    /**
     * Player two score field
     */
    @FXML
    private Text enemyScore;
    /**
     * Middle stack card count field
     */
    @FXML
    private Text midCartCount;
    /**
     * Leave game button
     */
    @FXML
    private Button leaveButton;
    /**
     * Challenge button for bluff
     */
    @FXML
    private Button challengeButton;
    /**
     * Anchor pane for bottom
     */
    @FXML
    private AnchorPane bottomAnchorPane;
    /**
     * Anchor pane for top
     */
    @FXML
    private AnchorPane upperAnchorPane;
    /**
     * Anchor pane for middle stack cards
     */
    @FXML
    private AnchorPane midStack;
    /**
     * Text area for game logs
     */
    @FXML
    private TextArea logArea;
    /**
     * Anchor pane for the game
     */
    @FXML
    private AnchorPane gameMainAnchor;
    /**
     * Field for round value
     */
    private int round = 1;
    /**
     * Static instance to access from static methods
     */
    public static GameController _instance;
    /**
     * Stack of all cards
     */
    private Stack<Card> allCards;
    /**
     * List of Rectangles of Cards of Player One
     */
    private List<Rectangle> currentCards;
    /**
     * List of Rectangles of Cards of Player Two
     */
    private List<Rectangle> opponentCards;
    /**
     * Map of Rectangles and Cards
     */
    private Map<Rectangle, Card> cardMappings;
    /**
     * Flag to check if third level score was posted
     */
    private boolean thirdLevelScorePosted;
    /**
     * Flag to check if the AI bluffed
     */
    private boolean AiBluffed;
    /**
     * Keeps the total shift count for the mid stack.
     */
    private int midStackShift = 0;

    /**
     * Socket server for multiplayer level
     */
    private SocketServer socketServer;
    /**
     * Socket client for multiplayer level
     */
    private SocketClient socketClient;
    /**
     * Thread of opponent
     */
    private Thread otherPlayerThread;
    /**
     * Reentrant lock to synchronize threads
     */
    public static final ReentrantLock gameSynchronizer = new ReentrantLock();
    /**
     * Flag to keep if the player is host of the multiplayer level
     */
    private boolean isHost;
    /**
     * Flag to keep if the host has bluffed
     */
    private boolean hostBluffed;

    /**
     * Boolean that shows if the game has ended or not.
     */
    private boolean isGameEnded;


    /**
     * Initializes the scene
     *
     * @param url            Address of this scene
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameLogic.getInstance();
        _instance = this;
        this.isHost = false;
        this.thirdLevelScorePosted = false;
        this.leaveButton.setFocusTraversable(false);
        this.round = 0;
        this.setUpNextLevel(false); // set up level 1
        this.AiBluffed = false;
        this.hostBluffed = false;
        enableAutoScroll(this.logArea);
        this.gameMainAnchor.setBackground(UIUtility.getBackground());
        centerScene(this.gameMainAnchor.getPrefWidth(), this.gameMainAnchor.getPrefHeight());
        this.isGameEnded = false;
    }

    /**
     * Initializes card rectangles
     *
     * @param pane        AnchorPane to place the card onto
     * @param name        name of the card
     * @param yLocation   y-location of the card
     * @param accessCards flag to keep if the player can access the initialized cards
     */
    private void initCards(AnchorPane pane, String name, double yLocation, boolean accessCards) {
        pane.getChildren().clear();
        List<Rectangle> cardList;
        boolean isHidden = false;
        if (name.equals("enemy ")) {
            this.opponentCards = new ArrayList<>();
            isHidden = true;
            cardList = this.opponentCards;
        } else {
            this.currentCards = new ArrayList<>();
            cardList = this.currentCards;
        }
        if (accessCards) {
            this.cardMappings.clear();
        }
        for (int i = 0; i < CARD_PER_HAND; i++) {
            Card drawn = null;
            if (accessCards) {
                drawn = GameLogic.getInstance().getPlayerCards().get(PlayerEnum.ONE).get(i);
            }
            Rectangle card = createCardRectangle(isHidden, drawn);
            if (accessCards) {
                this.cardMappings.put(card, drawn);
                card.setOnMouseClicked(this::mouseClickHandler);
            }
            cardList.add(card);
            try {
                pane.getChildren().add(cardList.get(i));
            } catch (IllegalArgumentException e) {
                logToScreen("First initialization of " + name + "card " + (i + 1), this.logArea, LOGGER);
            }
            cardList.get(i).setLayoutX(LEFTMOST_CARD_X + i * HORIZONTAL_CARD_SPACING);
            cardList.get(i).setLayoutY(yLocation);
        }
    }


    /**
     * Initializes opponent cards' rectangles
     */
    public void initOpponentCards() {
        initCards(this.upperAnchorPane, "enemy ", ENEMY_CARD_Y, false);
    }

    /**
     * Initializes player cards' rectangles
     *
     * @param accessCards flag to keep if the player can access the initialized cards
     */
    public void initPlayerCards(boolean accessCards) {
        initCards(this.bottomAnchorPane, "", PLAYER_CARD_Y, accessCards);
    }

    /**
     * Prepares the scene for next hand
     */
    private void nextHand() {
        this.initOpponentCards();
        this.initPlayerCards(false);
        this.drawAllCards();
    }

    /**
     * Adds cards to the middle stack at once
     *
     * @param mid stack of cards to add
     */
    public void bulkAddToMiddle(Stack<Card> mid) {
        this.midStack.getChildren().clear();
        this.setMidCount();
        for (Card c : mid) {
            this.midStack.getChildren().add(createCardRectangle(false, c));
        }
    }

    public void removeOneCardFromOpponent() {
        int size = this.upperAnchorPane.getChildren().size();
        if (size > 0) {
            this.upperAnchorPane.getChildren().remove(0);
        }
    }

    /**
     * Updates scores of players at once
     */
    public void bulkScoreUpdate() {
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
    }

    /**
     * Creates a card Rectangle
     *
     * @param isHidden  if the card is viewed from the back or front
     * @param cardValue card value for the rectangle
     * @return Rectangle for a card
     */
    private Rectangle createCardRectangle(boolean isHidden, Card cardValue) {
        Rectangle card = new Rectangle();
        card.setArcHeight(CARD_ARC);
        card.setArcWidth(CARD_ARC);
        card.setHeight(CARD_HEIGHT);
        card.setWidth(CARD_WIDTH);
        card.setStroke(BLACK);
        card.setStrokeType(StrokeType.valueOf("INSIDE"));
        card.setFill(WHITE);
        Image img = null;
        if (isHidden) {
            img = new Image(CARD_BACK_IMAGE);
        } else {
            if (cardValue != null) {
                img = new Image(cardValue.getImage());
            }
        }
        if (img != null) {
            card.setFill(new ImagePattern(img));
        }
        return card;
    }

    /**
     * Initializes the middle stack
     */
    private void initStack() {
        Image img = new Image(CARD_BACK_IMAGE);
        midStack.getChildren().clear();
        for (int i = 0; i < CARD_PER_HAND; i++) {
            Card card = allCards.pop();
            GameLogic.getInstance().getMiddle().add(card);
            Rectangle rec = createCardRectangle(false, card);
            if (round == MULTIPLAYER_LEVEL || i == CARD_PER_HAND - 1) {
                rec.setLayoutX(midStackShift * MID_STACK_SHIFT);
            } else {
                rec.setLayoutX(midStackShift++ * MID_STACK_SHIFT);
            }
            if (i + 1 != CARD_PER_HAND) {
                rec.setFill(new ImagePattern(img));
                midStack.getChildren().add(rec);
            } else {
                StackPane stackPane = drawCardInsideRectangle(rec, card, false);
                midStack.getChildren().add(stackPane);
            }
            this.cardMappings.put(rec, card);
        }
        logToScreen("Cards were dealt for middle stack", this.logArea, LOGGER);
    }

    /**
     * Deals cards for both of the players.
     */
    public void drawAllCards() {
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
        for (int i = 0; i < CARD_PER_HAND; i++) {
            Card top = allCards.pop();
            cardsTwo.add(top);
            this.cardMappings.put(opponentCards.get(i), top);
        }
        logToScreen("Cards were dealt for Player Two", this.logArea, LOGGER);
        GameLogic.getInstance().setPlayerCards(playerCards);
    }

    /**
     * Sets the rectangle visible
     *
     * @param r Rectangle
     */
    private void setRectangleVisible(Rectangle r) {
        r.setFill(WHITE);
    }

    /**
     * Draws a card inside the given rectangle
     *
     * @param r          Rectangle to draw a card inside
     * @param cardToDraw card to draw
     * @param isHidden   if the card is inverted or not
     * @return StackPane of the card
     */
    private StackPane drawCardInsideRectangle(Rectangle r, Card cardToDraw, boolean isHidden) {
        final StackPane stack = new StackPane();
        if (!isHidden) {
            Image img = new Image(cardToDraw.getImage());
            r.setFill(new ImagePattern(img));
        }
        stack.setLayoutX(r.getLayoutX());
        stack.setLayoutY(r.getLayoutY());
        stack.getChildren().add(r);
        stack.setPickOnBounds(false);
        return stack;
    }

    /**
     * Draws a card for the player
     *
     * @param r          Rectangle of the card
     * @param cardToDraw Card to draw
     */
    private void drawCardForPlayer(Rectangle r, Card cardToDraw) {
        StackPane stack = this.drawCardInsideRectangle(r, cardToDraw, false);
        bottomAnchorPane.getChildren().add(stack);
    }

    /**
     * Shuffles the cards before dealing them.
     */
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

    /**
     * Sets level text
     */
    private void setLevelText() {
        String levelStr = "Level ";
        levelText.setText(levelStr + round);
    }

    /**
     * Sets middle stack count
     */
    private void setMidCount() {
        String text = "Mid Count: ";
        midCartCount.setText(text + GameLogic.getInstance().getMiddle().size());
    }

    /**
     * Clears the view
     */
    public void clearView() {
        this.bottomAnchorPane.getChildren().clear();
        this.upperAnchorPane.getChildren().clear();
        this.midStackShift = 0;
        this.midStack.getChildren().clear();
    }

    /**
     * Prepares the view for the next level
     *
     * @param continued   flag to check if the deal is happening in the same level or not
     * @param playerScore player score
     * @param enemyScore  enemy score
     */
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
        this.initPlayerCards(false);
        this.initStack();
        this.drawAllCards();
        GameLogic.getInstance().setAiStrategy(round);
        this.setMidCount();
    }

    /**
     * Sets up the next level
     *
     * @param continued flag to check if the deal is happening in the same level or not
     */
    private void setUpNextLevel(boolean continued) {
        this.challengeButton.setVisible(false);
        int playerScore = GameLogic.getInstance().getScores().get(PlayerEnum.ONE);
        int enemyScore = GameLogic.getInstance().getScores().get(PlayerEnum.TWO);
        if (round == MULTIPLAYER_LEVEL && !thirdLevelScorePosted) {
            if (playerScore >= MAX_SCORE || enemyScore >= MAX_SCORE) {
                thirdLevelScorePosted = true;
                GameLogic.getInstance().sendScores(logArea);
                logToScreen("---- Round Ended ----", logArea, LOGGER);
            } else {
                this.nextLevelCleanup(true, playerScore, enemyScore);
            }
        } else if (round <= LAST_ROUND) {
            if (this.round != 0 && !continued) {
                GameLogic.getInstance().sendScores(logArea);
                logToScreen("---- Round Ended ----", logArea, LOGGER);
            }
            if (!continued) {
                playerScore = 0;
                enemyScore = 0;
            }
            this.nextLevelCleanup(continued, playerScore, enemyScore);
        }
    }

    /**
     * Sets the player score
     *
     * @param score new score
     */
    private void setPlayerScore(int score) {
        String text = "Your Score: ";
        this.yourScore.setText(text + score);
    }

    /**
     * Sets the enemy score
     *
     * @param score new score
     */
    private void setEnemyScore(int score) {
        String text = "Opponent Score: ";
        this.enemyScore.setText(text + score);
    }

    /**
     * Method to check if the cheat keys have been pressed
     *
     * @param event key event
     */
    protected static void keyPressEvent(KeyEvent event) {
        KeyCombination ctrl9 = new KeyCodeCombination(KeyCode.DIGIT9, KeyCodeCombination.CONTROL_DOWN);
        if (ctrl9.match(event)) {
            if (_instance != null) {
                logToScreen("Cheat activated!", _instance.logArea, LOGGER);
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

    /**
     * Navigates to home
     *
     * @param e action event
     */
    @FXML
    protected void navigateToHome(ActionEvent e) {
        URL resource = getClass().getResource(UiConstants.MENU_FXML);
        UIUtility.navigateTo(e, resource, null);
        Properties properties = PropertiesLoader.getProperties();
        double width = Double.parseDouble(properties.getProperty("width"));
        double height = Double.parseDouble(properties.getProperty("height"));
        centerScene(width, height);
    }

    /**
     * Initializes multiplayer level
     */
    private void initMultiLevel() {
        GameLogic.getInstance().resetScores();
        this.setEnemyScore(0);
        this.setPlayerScore(0);
        StackPane stackPane = new StackPane();
        Text txt = new Text("Looking for players...");
        txt.setFill(WHITE);
        stackPane.setAlignment(txt, Pos.TOP_CENTER); //set it to the Center Left(by default it's on the center)
        stackPane.getChildren().add(txt);
        this.upperAnchorPane.getChildren().add(stackPane);
        int port = 5858;
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.warn("IP could not be grabbed. Aborting");
            return;
        }
        ip = "127.0.0.1"; // TODO -- solve this

        MultiplayerController multiplayerController = new MultiplayerController(ip, port, txt);
        new Thread(multiplayerController).start();
    }

    /**
     * Creates socket for the multiplayer level
     *
     * @param port port of the socket
     * @param txt  Text area for the GUI
     */
    public void createSocket(int port, Text txt) {
        this.isHost = true;
        txt.setText("You are in queue. Please wait...");
        this.socketServer = new SocketServer(port);
        LOGGER.info("Opponent is connected");
        GameState gameState = (GameState) this.socketServer.readSocket();
        if (gameState != null) {
            String username = gameState.getHostPlayerName();
            txt.setText("Your opponent is: " + username + "\nGame will start in 3 seconds.");
        }
        try {
            Thread.sleep(MULTIPLAYER_IDLE_MS);
        } catch (InterruptedException e) {
            LOGGER.info("3s wait failed.");
        }
        Platform.runLater(() -> {
            this.setUpNextLevel(false);
            GameState initialState = new GameState(GameLogic.getInstance(), false, null, isGameEnded);
            this.socketServer.writeSocket(initialState);
        });
    }

    /**
     * Connects to a created socket
     *
     * @param found opponent info to connect the socket of
     * @param txt   Text area for the GUI
     */
    public void connectSocket(OpponentInfo found, Text txt) {
        System.out.println(found.getIp());
        this.socketClient = new SocketClient(found.getIp(), found.getPort());
        LOGGER.info("Host socket accepted the connection");
        txt.setText("Your opponent is: " + found.getUserName() + "\nGame will start in 3 seconds.");
        this.socketClient.writeSocket(new GameState(GameLogic.getInstance(), false, null, isGameEnded));
        try {
            Thread.sleep(MULTIPLAYER_IDLE_MS);
        } catch (InterruptedException e) {
            LOGGER.info("3s wait failed.");
        }
        Platform.runLater(() -> {
            this.setUpNextLevel(false);
            GameState initialState = (GameState) this.socketClient.readSocket();
            if (initialState != null) {
                GameLogic.getInstance().readLogicFromState(initialState);
            }
            GameLogic.getInstance().startWaitForHostTask();
        });

    }

    /**
     * Wrapper for setting up the next level
     */
    private void setUpNextLevelWrapper() {
        if (this.round < LAST_ROUND) {
            Button button = new Button("Next Level");
            button.setOnAction(e -> this.setUpNextLevel(false));
            this.midStack.getChildren().add(button);
        } else if (this.round == LAST_ROUND) {
            GameLogic.getInstance().sendScores(logArea);
            this.initMultiLevel(); // this one clears scores inside.
        } else {
            this.gameOverScreen();
        }
    }

    public void gameOverScreen() {
        this.challengeButton.setVisible(false);
        upperAnchorPane.getChildren().clear();
        midStack.getChildren().clear();
        GameLogic.getInstance().getMiddle().clear();
        setMidCount();
        bottomAnchorPane.getChildren().clear();
        String msg = "Game Over.\n";
        if (GameLogic.getInstance().getScores().get(PlayerEnum.ONE) > GameLogic.getInstance().getScores().get(PlayerEnum.TWO)) {
            msg += "You won!";
        } else if (GameLogic.getInstance().getScores().get(PlayerEnum.ONE) < GameLogic.getInstance().getScores().get(PlayerEnum.TWO)) {
            msg += GameLogic.getInstance().getCurrentState().getHostPlayerName() +" won! You lost!";
        } else {
            msg += "Noone won! It is a draw!";
        }
        Text gameOver = new Text(msg);
        isGameEnded = true;
        gameOver.setFill(WHITE);
        thirdLevelScorePosted = true;
        logToScreen("---- Round Ended ----", logArea, LOGGER);
        GameLogic.getInstance().sendScores(logArea);
        this.midStack.getChildren().add(gameOver);
    }

    /**
     * Handles real bluff for player
     *
     * @param bluffer   bluffing player
     * @param candidate candidate card
     */
    private void handleRealBluffForPlayer(PlayerEnum bluffer, Card candidate) {
        this.midStackShift = 0;
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

    /**
     * Handles fake bluff for player
     *
     * @param bluffer   bluffing player
     * @param candidate candidate card
     * @param bluffed   bluffed card
     * @param added     added rectangle
     */
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
        added.setLayoutX(midStackShift * MID_STACK_SHIFT);
        this.midStack.getChildren().add(added);
        GameLogic.getInstance().getMiddle().push(candidate); // put things back to middle.
        GameLogic.getInstance().getMiddle().push(bluffed); // put things back to middle.
    }

    /**
     * Accepts challenge
     *
     * @param e action event
     */
    @FXML
    private void acceptChallenge(ActionEvent e) {
        logToScreen("Challenge accepted", this.logArea, LOGGER);
        this.challengeButton.setVisible(false); // destroy button.
        this.AiBluffed = false; // handle flag.
        Card bluffed = GameLogic.getInstance().getMiddle().pop(); // get closed card.
        Card candidate = GameLogic.getInstance().getMiddle().pop(); // get prev card.
        if (candidate.getCard() == bluffed.getCard()) {
            // bluff was real
            this.handleRealBluffForPlayer(PlayerEnum.TWO, candidate);
        } else {
            // bluff was fake.
            //logToScreen("Bluff was fake.", this.logArea, LOGGER);
            Rectangle r = GameLogic.getRectangleByCard(this.cardMappings, bluffed); //get the rectangle of closed card.
            this.midStack.getChildren().remove(r);
            this.setRectangleVisible(r); // make rectangle visible.
            this.drawCardInsideRectangle(r, bluffed, false); // put text to it.
            this.handleFakeBluffForPlayer(PlayerEnum.TWO, candidate, bluffed, r);
        }
    }

    /**
     * Handles pressing on the rectangles of the cards
     *
     * @param pressed rectangle that was pressed
     */
    private void controlPlayer(Rectangle pressed) {
        pressed.setLayoutX(midStackShift * MID_STACK_SHIFT);
        midStack.getChildren().add(pressed); // add to middle.
        Card card = this.cardMappings.get(pressed); // get pressed card.
        if (GameLogic.getInstance().getMiddle().isEmpty()) {
            logToScreen("Player One threw " + card + " on an empty stack", this.logArea, LOGGER);
        }
        if (GameLogic.getInstance().handleThrow(card, PlayerEnum.ONE, this.logArea)) { // check if matched
            logToScreen("Cards match for Player One", this.logArea, LOGGER);
            this.midStackShift = 0;
            midStack.getChildren().clear(); // match, clear middle view.
        }
        this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE));
        currentCards.remove(pressed); // remove card from hand.
        pressed.setOnMouseClicked(null); // make unclickable.
    }

    /**
     * Controls the actions of the opponent
     *
     * @return bluffed status
     */
    private boolean controlOpponent() {
        Pair<Rectangle, Card> cardMap = GameLogic.getInstance().getAiStrategy().playAsOpponent(cardMappings);
        boolean bluffed = false;
        if (this.round == MULTIPLAYER_LEVEL) {
            LOGGER.info("Socket read: " + cardMap.getValue());
            bluffed = GameLogic.getInstance().getCurrentState().isBluffed();
        } else {
            bluffed = GameLogic.getInstance().getAiStrategy().getHasBluffed();
        }
        GameController._instance.getChallengeButton().setVisible(bluffed);
        Rectangle removed = cardMap.getKey();
        Card opponentCard = cardMap.getValue();
        this.upperAnchorPane.getChildren().remove(removed); // remove from view.
        this.cardMappings.remove(removed); // remove the mapping.
        Rectangle opponentRectangle = createCardRectangle(bluffed, opponentCard); // generate new view.
        opponentRectangle.setLayoutX(midStackShift * MID_STACK_SHIFT);
        this.cardMappings.put(opponentRectangle, opponentCard); // create new mapping.
        drawCardInsideRectangle(opponentRectangle, opponentCard, true); // put text unless it is hidden.
        midStack.getChildren().add(opponentRectangle); // put it to mid.
        if (bluffed) {
            // handle bluff
            GameLogic.getInstance().getMiddle().add(opponentCard); // put card to bluff, do not make check.
        } else {
            if (GameLogic.getInstance().getMiddle().isEmpty()) {
                logToScreen("Player Two threw " + opponentCard + " on an empty stack", this.logArea, LOGGER);
            }
            if (GameLogic.getInstance().handleThrow(opponentCard, PlayerEnum.TWO, this.logArea)) { // no bluff, check if scored.
                logToScreen("Cards match for Player Two", this.logArea, LOGGER);
                this.midStackShift = 0;
                midStack.getChildren().clear();
            }
        }
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO));
        return bluffed;
    }

    /**
     * Rejects bluff
     */
    private void rejectBluff() {
        this.challengeButton.setVisible(false); // destroy button.
        GameLogic.getInstance().getMiddle().clear(); //clear middle, ai got all cards.
        this.setMidCount(); // reset mid count.
        this.midStackShift = 0;
        this.midStack.getChildren().clear(); // remove cards from view.
        GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, GameConstants.PISTI); // give score to second player.
        this.setEnemyScore(GameLogic.getInstance().getScores().get(PlayerEnum.TWO)); //update score view.
    }

    /**
     * Handler for mouse clicks
     *
     * @param event mouse event
     */
    @FXML
    protected void mouseClickHandler(MouseEvent event) {
        if (this.round == MULTIPLAYER_LEVEL && !this.isHost) {
            this.playAsNonHostPlayer(event);
        } else if (event.getButton() == MouseButton.PRIMARY) {
            this.throwCard(event);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            this.doBluff(event);
        }
    }

    /**
     * Handles playing as non host player for the multiplayer level.
     *
     * @param event Mouse event that triggered this method
     */
    private void playAsNonHostPlayer(MouseEvent event) {
        if (GameLogic.getInstance().getCurrentPlayer() == PlayerEnum.TWO) {
            GameLogic.getInstance().setCurrentPlayer(PlayerEnum.ONE);
            boolean bluffed = event.getButton() == MouseButton.SECONDARY;
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            Card played = this.cardMappings.get(pressed);
            this.cardMappings.remove(pressed);
            GameLogic.getInstance().getPlayerCards().get(PlayerEnum.ONE).remove(played);
            this.bottomAnchorPane.getChildren().remove(pressed);
            // send this via socket,
            // read a new game state,
            // then call GameLogic.getInstance().readLogicFromState with new read state
            GameState state = new GameState(GameLogic.getInstance(), bluffed, played, isGameEnded);
            // Post the Card played variable to host player along side with the bluffed boolean.
            // Read the new data from the host player.
            // Update the view accordingly. (call GameLogic.getInstance().readLogicFromState. That method also needs to be completed.)
            // While updating the view, removing a random Rectangle from the opponent's side (i.e, upperAnchorPane) is enough as
            // this player cannot see the opponents' cards anyway.
            Task<Boolean> newGameTask = new Task<>() {
                @Override
                public Boolean call() {
                    GameController._instance.getSocketClient().writeSocket(state);
                    LOGGER.info("Posted: " + state.getCardThrown());
                    return true;
                }
            };
            newGameTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    (EventHandler<WorkerStateEvent>) t -> {
                        LOGGER.info("Socket write success");
                        GameState newState = (GameState) GameController._instance.getSocketClient().readSocket();
                        LOGGER.info("Read state");
                        if (newState == null) {
                            gameOverScreen();
                        } else {
                            GameLogic.getInstance().readLogicFromState(newState);
                            GameLogic.getInstance().startWaitForHostTask();
                        }

                    });
            new Thread(newGameTask).start();
        }

    }


    /**
     * Method that allows the player to bluff
     *
     * @param event mouse event
     */
    private void doBluff(MouseEvent event) {
        try {
            if (GameLogic.getInstance().getMiddle().size() == 1 && this.round >= LAST_ROUND) {
                gameSynchronizer.lock();
                this.hostBluffed = true;
                this.otherPlayerThread = new Thread(new OpponentController(false));
                logToScreen("You bluffed.", this.logArea, LOGGER);
                Rectangle pressed = (Rectangle) ((Node) event.getTarget());
                Random rand = new Random();
                int accepted = 0;
                int headsTail = rand.nextInt(2);
                this.bottomAnchorPane.getChildren().remove(pressed.getParent());
                currentCards.remove(pressed); // remove card from hand.
                if (headsTail == accepted) {
                    logToScreen("AI accepted the challenge", this.logArea, LOGGER);
                    // ai accepted the challenge.
                    Card bluffed = this.cardMappings.get(pressed);
                    Card candidate = GameLogic.getInstance().getMiddle().pop(); // get prev card.
                    if (candidate.getCard() == bluffed.getCard()) {
                        // bluff was real
                        this.handleRealBluffForPlayer(PlayerEnum.ONE, candidate);
                        this.otherPlayerThread.start();
                        gameSynchronizer.unlock();
                    } else {
                        // bluff was fake.
                        this.handleFakeBluffForPlayer(PlayerEnum.ONE, candidate, bluffed, pressed);
                        this.otherPlayerThread.start();
                        gameSynchronizer.unlock();
                    }

                } else {
                    // ai rejected the bluff.
                    logToScreen("AI rejected the bluff", this.logArea, LOGGER);
                    this.midStackShift = 0;
                    this.midStack.getChildren().clear(); // clear mid view.
                    GameLogic.getInstance().getMiddle().clear(); // clear mid.
                    GameLogic.getInstance().addScoreToPlayer(PlayerEnum.ONE, GameConstants.PISTI); // give score to first player.
                    this.setPlayerScore(GameLogic.getInstance().getScores().get(PlayerEnum.ONE)); //update score view.
                    this.otherPlayerThread.start();
                    gameSynchronizer.unlock();
                }
            }
        } catch (IllegalArgumentException ex) {
            logToScreen("Already played", this.logArea, LOGGER);
        }
    }

    /**
     * Handles serving hands until one party reaches MAX_SCORE
     */
    private void serveHand() {
        if (GameLogic.getInstance().isHandEmpty()) {
            if (!this.allCards.isEmpty()) {
                logToScreen("Cards dealt", this.logArea, LOGGER);
                this.nextHand();
            } else {
                GameLogic.getInstance().giveMidStackCardsToLastWinner();
                this.midStackShift = 0;
                this.midStack.getChildren().clear();
                this.setMidCount();
                int playerScore = GameLogic.getInstance().getScores().get(PlayerEnum.ONE);
                int enemyScore = GameLogic.getInstance().getScores().get(PlayerEnum.TWO);
                this.setPlayerScore(playerScore);
                this.setEnemyScore(enemyScore);
                logToScreen("Deck consumed for level " + this.round, this.logArea, LOGGER);
                if (playerScore < MAX_SCORE && enemyScore < MAX_SCORE) {
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

    /**
     * Handles throwing card for player
     *
     * @param event mouse event
     */
    private void throwCard(MouseEvent event) {
        try {
            this.otherPlayerThread = new Thread(new OpponentController(false));
            gameSynchronizer.lock();
            this.hostBluffed = false;
            if (this.AiBluffed) {
                this.AiBluffed = false;
                this.rejectBluff();
                logToScreen("You rejected the bluff.", this.logArea, LOGGER);
            }
            Rectangle pressed = (Rectangle) ((Node) event.getTarget());
            this.controlPlayer(pressed);
            GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
            this.toggleClickable(false);
            this.setMidCount();
            if (this.round == MULTIPLAYER_LEVEL && isHost) {
                GameState state = new GameState(GameLogic.getInstance(), false, null, isGameEnded);
                socketServer.writeSocket(state);
            }
            this.otherPlayerThread.start();
            gameSynchronizer.unlock();
        } catch (IllegalArgumentException ex) {
            logToScreen("Already played", this.logArea, LOGGER);
        }
    }

    /**
     * Plays as the opponent
     *
     * @param updateAiBluffed flag to check if bluffing active
     */
    public void playAsOpponent(boolean updateAiBluffed) {
        boolean bluffed = this.controlOpponent();
        if (updateAiBluffed) {
            this.AiBluffed = bluffed;
        }
    }

    /**
     * Handles table actions after opponent plays
     */
    public void doTableActions() {
        this.setMidCount();
        this.serveHand();
        if (this.round == MULTIPLAYER_LEVEL && this.isHost) {
            GameState newState = new GameState(GameLogic.getInstance(), this.hostBluffed, null, isGameEnded);
            LOGGER.info("Writing state");
            this.socketServer.writeSocket(newState);
        }
    }

    /**
     * Toggles clickable setting of player cards
     *
     * @param clickable true for clickable, otherwise false
     */
    public void toggleClickable(boolean clickable) {
        if (clickable) {
            this.currentCards.forEach(e -> e.setOnMouseClicked(this::mouseClickHandler));
        } else {
            this.currentCards.forEach(e -> e.setOnMouseClicked(null));
        }

    }

    /**
     * Gets challenge button
     *
     * @return challenge button
     */
    public Button getChallengeButton() {
        return challengeButton;
    }

    /**
     * Handler for leaving the game early
     *
     * @param e action event
     */
    @FXML
    public void leaveGame(ActionEvent e) {
        logToScreen("Player " + SessionStorage.getInstance().getUsername() + " has left the game", this.logArea, LOGGER);
        GameLogic.getInstance().getScores().replace(PlayerEnum.ONE, 0);
        GameLogic.getInstance().getScores().replace(PlayerEnum.TWO, 0);
        GameLogic.getInstance().getMiddle().clear();
        for (int i = this.round; i <= LAST_ROUND; i++) {
            GameLogic.getInstance().sendScores(logArea);
        }
        if (socketServer != null) {
            socketServer.closeSocket();
        }
        if (socketClient != null) {
            socketClient.closeSocket();
        }
        Boolean dequeue = HTTPService.getInstance().dequeue(new OpponentInfo("", 5858));
        if (dequeue) {
            LOGGER.info(SessionStorage.getInstance().getUsername() + " has been removed from the queue");
        }
        this.navigateToHome(e);
    }

    /**
     * Gets socket server
     *
     * @return socket server
     */
    public SocketServer getSocketServer() {
        return socketServer;
    }

    /**
     * Sets socket server
     *
     * @param socketServer new value of socket server
     */
    public void setSocketServer(SocketServer socketServer) {
        this.socketServer = socketServer;
    }

    /**
     * Gets socket client
     *
     * @return socket client
     */
    public SocketClient getSocketClient() {
        return socketClient;
    }

    /**
     * Sets socket client
     *
     * @param socketClient new value of socket client
     */
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }
}
