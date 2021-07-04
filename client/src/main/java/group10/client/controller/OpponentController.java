package group10.client.controller;

import javafx.application.Platform;

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
     * One parameter constructor
     *
     * @param updateBluff update bluff flag
     */
    public OpponentController(boolean updateBluff) {
        this.updateBluff = updateBluff;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        try {
            if (GameController.gameSynchronizer.tryLock(10, TimeUnit.SECONDS)) {
                this.play();
                GameController.gameSynchronizer.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles opponent actions
     */
    public void play() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(125, 1570));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            GameController._instance.playAsOpponent(updateBluff);
            GameController._instance.doTableActions();
            GameController._instance.toggleClickable(true);
        });
    }
}
