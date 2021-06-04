package group10.client.utility;

import group10.client.StageInitializer;
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
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

public class UIUtility {

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


    public static Scene navigateTo(Stage stage, Resource resource, String title) {
        try {
            return navigateTo(stage, resource.getURL(), title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Scene navigateTo(ActionEvent event, Resource resource, String title) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return UIUtility.navigateTo(stage, resource, title);
    }

    public static Scene navigateTo(ActionEvent event, URL resource, String title) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return UIUtility.navigateTo(stage, resource, title);
    }

    public static void logToScreen(String message, TextArea textArea, Logger LOGGER) {
        LOGGER.info(message);
        textArea.appendText(message + "\n");
    }

    public static void centerScene(double width, double height) {
        if (Screen.getPrimary() != null) {
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            if (StageInitializer.stage != null) {
                StageInitializer.stage.setX((primScreenBounds.getWidth() - width) / 2);
                StageInitializer.stage.setY((primScreenBounds.getHeight() - height) / 2);
            }
        }
    }
}
