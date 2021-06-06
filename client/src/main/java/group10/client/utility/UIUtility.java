package group10.client.utility;

import group10.client.StageInitializer;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Utility class that contains UI related helper methods.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class UIUtility {

    /**
     * Navigator method to change scenes.
     * Loads the given resource, changes the current scene to that scene,
     * sets screen titles and displays the stage.
     *
     * @param stage    Primary Stage
     * @param resource Resource URL to be loaded
     * @param title    Title to be set for window.
     * @return new Scene that was created and navigated to
     */
    public static Scene navigateTo(Stage stage, URL resource, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent registerScreen = fxmlLoader.load();
            Scene scene = new Scene(registerScreen);
            stage.setScene(scene);
            if (title != null && !title.isEmpty()) {
                stage.setTitle(title);
            }
            stage.show();
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Changes current scene to new scene by loading the resource.
     *
     * @param event    Event that is attached to stage. Used to retrieve the stage.
     * @param resource Resource URL to be loaded
     * @param title    Title to be set.
     * @return new Scene that was created and navigated to
     */
    public static Scene navigateTo(ActionEvent event, URL resource, String title) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return UIUtility.navigateTo(stage, resource, title);
    }

    /**
     * Given a message, logs it to console by using logger.
     * If textArea parameter provided as not null, also logs the same message there.
     *
     * @param message  Message to be logged.
     * @param textArea Text area that is logs to be written.
     * @param LOGGER   Logger instance that prints the message to console.
     */
    public static void logToScreen(String message, TextArea textArea, Logger LOGGER) {
        if (LOGGER != null) {
            LOGGER.info(message);
        }
        if (textArea != null) {
            textArea.appendText(message + "\n");
        }
    }

    /**
     * Centers the window.
     *
     * @param width  Width of window.
     * @param height Height of window.
     */
    public static void centerScene(double width, double height) {
        if (Screen.getPrimary() != null) {
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            if (StageInitializer.stage != null) {
                StageInitializer.stage.setX((primScreenBounds.getWidth() - width) / 2);
                StageInitializer.stage.setY((primScreenBounds.getHeight() - height) / 2);
            }
        }
    }

    /**
     * Utility method to scroll the game log to the bottom automatically
     *
     * @param textArea textArea to enable autoscroll
     */
    public static void enableAutoScroll(TextArea textArea) {
        textArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            textArea.setScrollTop(Double.MAX_VALUE);
        });
    }
}
