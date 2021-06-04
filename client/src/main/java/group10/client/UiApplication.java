package group10.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * UI Application to initialize Spring and JavaFX UI
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class UiApplication extends Application {

    /**
     * Application context field
     */
    private ConfigurableApplicationContext applicationContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ClientApplication.class).run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    /**
     * Static method that sets the stage ready
     */
    static class StageReadyEvent extends ApplicationEvent {
        /**
         * Stage ready event
         *
         * @param stage stage
         */
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        /**
         * Gets stage
         *
         * @return stage
         */
        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
