package group10.client.controller;

import javafx.application.Platform;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class OpponentController implements Runnable {

    private boolean updateBluff;

    public OpponentController(boolean updateBluff) {
        this.updateBluff = updateBluff;
    }

    @Override
    public void run() {
        try {
            while (!GameController.gameSynchronizer.tryLock(1, TimeUnit.SECONDS)) ;
            this.play();
            GameController.gameSynchronizer.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(125, 1570));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            GameController._instance.playAsOpponent(updateBluff);
            GameController._instance.doTableActions();
        });
    }
}
