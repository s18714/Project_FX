package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HighscoresCellController {

    private int id;
    private String time;
    @FXML
    private Text idTxt = null;
    @FXML
    private Text timeTxt = null;

    HighscoresCellController(int id, String time) {
        this.id = id;
        this.time = time;
    }

    public void initialize() {
        idTxt.setText(id + "");
        timeTxt.setText(time);
    }
}