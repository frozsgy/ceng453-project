package group10.client.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class UIUtility {

    public static void navigateTo(Stage stage, URL resource, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent registerScreen = fxmlLoader.load();
            Scene scene = new Scene(registerScreen);
            stage.setScene(scene);
            if (title != null && !title.isEmpty()) {
                stage.setTitle(title);
            }
            stage.show();
        } catch (IOException ignored) {

        }
    }


    public static void navigateTo(Stage stage, Resource resource, String title) {
        try {
            navigateTo(stage, resource.getURL(), title);
        } catch (IOException ignored) {
        }

    }

    public static void navigateTo(ActionEvent event, Resource resource, String title) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        UIUtility.navigateTo(stage, resource, title);

    }

    public static void navigateTo(ActionEvent event, URL resource, String title) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        UIUtility.navigateTo(stage, resource, title);

    }
}
