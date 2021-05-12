package group10.client;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {

    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
    }
}