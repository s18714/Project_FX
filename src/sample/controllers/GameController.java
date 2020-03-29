package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

public class GameController implements EventHandler<KeyEvent> {

    private ArrayList<ArrayList<Image>> mixedImgs = mix(SelectLevelController.images);
    private ArrayList<ArrayList<ImageView>> views = new ArrayList<>();

    @FXML
    private GridPane gridPane;
    @FXML
    private Label hours;
    @FXML
    private Label minutes;
    @FXML
    private Label seconds;

    private int h;
    private int m;
    private int s;
    private int x = 0;
    private int y = 0;
    private String lvl;
    private boolean timerIsWorking = true;
    private boolean gameisWon = false;

    public void initialize() {
        gridPane.setGridLinesVisible(true);
        paintOnGrid();
        h = 0;
        m = 0;
        s = 0;
        new Thread(() -> {
            while (true) {
                if (timerIsWorking) {
                    s++;
                    if (s > 59) {
                        s = 0;
                        m++;
                        if (m == 60) {
                            m = 0;
                            h++;
                        }
                    }
                    Platform.runLater(() -> {
                        hours.setText(h + "");
                        minutes.setText(m + "");
                        seconds.setText(s + "");
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        for (int i = 0; i < SelectLevelController.mode; i++) {
            gridPane.addColumn(0);
            gridPane.addRow(0);
        }

        switch (SelectLevelController.mode) {
            case 0:
                lvl = "Easy";
                break;
            case 1:
                lvl = "Medium";
                break;
            case 2:
                lvl = "Hard";
                break;
        }
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        timerIsWorking = false;
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/menu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        if (!gameisWon) {
            stage.show();
        } else {
            Writer writer = new FileWriter("results.txt", true);
            writer.write(h + ":" + m + ":" + s + "\t\t\t" + lvl + "\n");
            mixedImgs.clear();
            SelectLevelController.images.clear();
            views.clear();
            writer.close();
            stage.show();
        }
    }

    private ArrayList<ArrayList<Image>> mix(ArrayList<ArrayList<Image>> arr) {
        int blankWidth = 840 / (4 + SelectLevelController.mode);
        int blankHeight = 768 / (4 + SelectLevelController.mode);
        for (ArrayList<Image> images : arr) {
            Collections.shuffle(images);
        }
        Image img = new Image("/sample/assets/blank.jpg", blankWidth, blankHeight, false, false);
        arr.get(x).set(y, img);
        return arr;
    }

    private void paintOnGrid() {
        for (int i = 0; i < mixedImgs.size(); i++) {
            views.add(new ArrayList<>());
            for (int j = 0; j < mixedImgs.get(i).size(); j++) {
                Image tmp = mixedImgs.get(i).get(j);
                ImageView imgv = new ImageView();
                imgv.setImage(tmp);
                imgv.setFitHeight(gridPane.getHeight() / (SelectLevelController.mode + 4));
                imgv.setFitWidth(gridPane.getWidth() / (SelectLevelController.mode + 4));
                views.get(i).add(imgv);
                gridPane.add(imgv, i, j);
            }
        }
    }

    private void repaint() {
        for (int i = 0; i < mixedImgs.size(); i++) {
            for (int j = 0; j < mixedImgs.get(i).size(); j++) {
                Image img = mixedImgs.get(i).get(j);
                views.get(i).get(j).setImage(img);
            }
        }
    }

    private void isCorrect() {
        int wrongCount = 0;
        for (int i = 0; i < mixedImgs.size(); i++) {
            for (int j = 0; j < mixedImgs.get(i).size(); j++) {
                if (mixedImgs.get(i).get(j) != SelectLevelController.images.get(i).get(j)) {
                    wrongCount++;
                }
            }
        }
        if (wrongCount == 1) {
            gameisWon = true;
        }
    }

    private void moveHoriz(int param) {
        if ((x < 3 + SelectLevelController.mode && param == -1) || (x > 0 && param == 1)) {
            Image tmp = SelectLevelController.images.get(x).get(y);
            SelectLevelController.images.get(x).set(y, SelectLevelController.images.get(x - param).get(y));
            x = x - param;
            SelectLevelController.images.get(x).set(y, tmp);
            repaint();
        }
    }

    private void moveVert(int param) {
        if ((y < 3 + SelectLevelController.mode && param == 1) || (y > 0 && param == -1)) {
            Image tmp = SelectLevelController.images.get(x).get(y);
            SelectLevelController.images.get(x).set(y, SelectLevelController.images.get(x).get(y + param));
            y = y + param;
            SelectLevelController.images.get(x).set(y, tmp);
            repaint();
        }
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                moveVert(1);
                isCorrect();
                break;
            case DOWN:
                moveVert(-1);
                isCorrect();
                break;
            case LEFT:
                moveHoriz(-1);
                isCorrect();
                break;
            case RIGHT:
                moveHoriz(1);
                isCorrect();
                break;
        }
    }
}