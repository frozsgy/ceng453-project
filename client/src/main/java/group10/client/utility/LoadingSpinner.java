package group10.client.utility;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LoadingSpinner {

    StackPane stackPane;
    BorderPane registerBorderPane;

    public LoadingSpinner(StackPane stackPane, BorderPane registerBorderPane) {
        this.stackPane = stackPane;
        this.registerBorderPane = registerBorderPane;
    }

    public void start() {
        try {
            registerBorderPane.setDisable(true);
            ProgressIndicator PI = new ProgressIndicator();
            stackPane.getChildren().add(PI);
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            registerBorderPane.setDisable(false);
            stackPane.getChildren().clear();
            stackPane.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
