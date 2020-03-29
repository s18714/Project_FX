package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HighscoresController {

    @FXML
    private VBox listOfTimes;
    private ArrayList<String> times = new ArrayList<>();

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/menu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public HighscoresController() {
        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        int i = 1;
        for (String time : times) {
            createCell(i, time);
            i++;
        }

    }

    private void readFromFile() throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("/Users/denys_kryzhanivskyi/Desktop/2 Semestr/GUI/Project_FX/src/results.txt"));
        String line;
        while ((line=bfr.readLine()) != null) {
            times.add(0, line);
        }
        bfr.close();
    }

    private void createCell(int id, String time) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/highscoresCell.fxml"));

        HighscoresCellController controller = new HighscoresCellController(id, time);
        loader.setController(controller);
        Pane pane;
        try {
            pane = loader.load();
            listOfTimes.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}