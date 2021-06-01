package group10.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class GameController implements Initializable {

    @FXML
    private Text levelText;

    private int round;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.round = 1;
        setLevelText();
    }

    private void setLevelText() {
        String levelStr = "Level " ;
//        levelText.setText(levelText + this.round.toString());
    }
}
