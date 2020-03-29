package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectLevelController {

    static ArrayList<ArrayList<Image>> images = new ArrayList<>();
    static int mode;

    private void splitImg(int rows, int columns) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(MenuController.selectedFile));
        int cellWidth = originalImage.getWidth() / columns;
        int cellHeight = originalImage.getHeight() / rows;

        int x, y = 0;
        for (int i = 0; i < rows; i++) {
            ArrayList<Image> tmp = new ArrayList<>();
            x = 0;
            for (int j = 0; j < columns; j++) {
                BufferedImage subImg = originalImage.getSubimage(x, y, cellWidth, cellHeight);
                tmp.add(SwingFXUtils.toFXImage(subImg, null));
                x += cellWidth;
            }
            images.add(tmp);
            y += cellHeight;
        }
    }

    @FXML
    public void goToGameEasy(ActionEvent event) throws IOException {
        mode = 0;
        splitImg(4, 4);
        loadAndShow(event);
    }

    @FXML
    public void goToGameMedium(ActionEvent event) throws IOException {
        mode = 1;
        splitImg(5, 5);
        loadAndShow(event);
    }

    @FXML
    public void goToGameHard(ActionEvent event) throws IOException {
        mode = 2;
        splitImg(6, 6);
        loadAndShow(event);
    }

    private void loadAndShow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/sample/views/game.fxml")));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        GameController gm = loader.getController();
        scene.setOnKeyPressed(gm);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/menu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}