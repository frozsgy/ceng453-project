package group10.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
        levelText.setText(levelStr + this.round);
    }

    protected static void keyPressEvent(KeyEvent event) {
        KeyCombination ctrl9 = new KeyCodeCombination(KeyCode.DIGIT9, KeyCodeCombination.CONTROL_DOWN);
        if (ctrl9.match(event)) {
            return;
            /**
             * TODO
             * implement hack
             */
        }
    }
}
