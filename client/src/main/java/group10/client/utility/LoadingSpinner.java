package group10.client.utility;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spinner class to render loading spinner for network requests.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class LoadingSpinner {

    /**
     * Logger to log messages
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingSpinner.class);

    /**
     * StackPane instance that spinner will be attached to.
     */
    StackPane stackPane;
    /**
     * Border pane that is the root of the scene.
     */
    BorderPane borderPane;

    /**
     * Constructor for spinner.
     *
     * @param stackPane          StackPane instance that spinner will be attached to.
     * @param registerBorderPane Border pane that is the root of the scene.
     */
    public LoadingSpinner(StackPane stackPane, BorderPane registerBorderPane) {
        this.stackPane = stackPane;
        this.borderPane = registerBorderPane;
    }

    /**
     * Makes everything on the scene disabled (i.e, not clickable)
     * Makes stack pane visible and renders a spinner on it.
     */
    public void start() {
        try {
            borderPane.setDisable(true);
            stackPane.setVisible(true);
            ProgressIndicator PI = new ProgressIndicator();
            stackPane.getChildren().add(PI);
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setDisable(false);
        } catch (Exception e) {
            LOGGER.warn("Exception at starting Spinner");
        }
    }

    /**
     * Makes everything on the scene enabled (i.e, clickable)
     * Makes stack pane invisible and stops rendering the spinner.
     */
    public void stop() {
        try {
            borderPane.setDisable(false);
            stackPane.setVisible(false);
            stackPane.getChildren().clear();
            stackPane.setDisable(true);
        } catch (Exception e) {
            LOGGER.warn("Exception at stopping Spinner");
        }
    }
}
