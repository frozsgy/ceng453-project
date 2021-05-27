package group10.client.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class UIUtility {

    public static void navigateTo(Stage stage, Resource resource, String title) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            Parent registerScreen = fxmlLoader.load();
            Scene scene = new Scene(registerScreen);
            stage.setScene(scene);
            if (title != null && !title.isEmpty()) {
                stage.setTitle("My New Scene");
            }
            stage.show();
        } catch (IOException e) {

        }
    }
}
