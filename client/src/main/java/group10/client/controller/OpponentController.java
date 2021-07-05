package group10.client.controller;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Class for handling actions that are taken by the opponent through another thread.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class OpponentController implements Runnable {

    /**
     * Flag to check if the bluff variable at the JavaFX Game controller needs update
     */
    private boolean updateBluff;

    /**
     * Constant logger instance to print messages to console
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OpponentController.class);

    /**
     * One parameter constructor
     *
     * @param updateBluff update bluff flag
     */
    public OpponentController(boolean updateBluff) {
        this.updateBluff = updateBluff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            if (GameController.gameSynchronizer.tryLock(10, TimeUnit.SECONDS)) {
                this.play();
                GameController.gameSynchronizer.unlock();
            }
        } catch (InterruptedException e) {
            LOGGER.error("User interrupt");
        }
    }

    /**
     * Handles opponent actions
     */
    public void play() {

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(125, 870));
        } catch (InterruptedException e) {
            LOGGER.error("User interrupt");
        }

        Platform.runLater(() -> {
            GameController._instance.playAsOpponent(updateBluff);
            GameController._instance.doTableActions();
            GameController._instance.toggleClickable(true);
        });
    }
}
