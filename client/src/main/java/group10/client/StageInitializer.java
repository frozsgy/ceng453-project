package group10.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static group10.client.utility.UIUtility.centerScene;


/**
 * Stage Initializer to initialize the stage
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {
    /**
     * Location of the login page fxml
     */
    @Value("classpath:/fxml/login.fxml")
    private Resource loginResource;
    /**
     * Window width
     */
    @Value("${spring.application.ui.width}")
    private int windowWidth;
    /**
     * Window height
     */
    @Value("${spring.application.ui.height}")
    private int windowHeight;
    /**
     * Application title for the window
     */
    private String applicationTitle;
    /**
     * Application context for Spring
     */
    private ApplicationContext applicationContext;
    /**
     * Stage field
     */
    public static Stage stage;

    /**
     * Constructor that initializes the application
     *
     * @param applicationTitle   window title
     * @param applicationContext application context of Spring
     */
    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(loginResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();

            stage = stageReadyEvent.getStage();
            stage.setScene(new Scene(parent, windowWidth, windowHeight));
            stage.setTitle(applicationTitle);
            stage.show();
            centerScene(windowWidth, windowHeight);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
