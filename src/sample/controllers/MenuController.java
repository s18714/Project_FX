package sample.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

    static String selectedFile;

    private Pattern pattern = Pattern.compile("([^\\s]+\\.(jpg))$", Pattern.CASE_INSENSITIVE);

    public void startGame(ActionEvent event) throws IOException {
        if (selectedFile == null) {
            selectedFile = "/Users/denys_kryzhanivskyi/Desktop/2 Semestr/GUI/Project_FX/src/sample/assets/default.jpg";
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/selectLevel.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToHighscores(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/highscores.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null).getPath();
        Matcher matcher = pattern.matcher(selectedFile);
        if (!matcher.find()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong file format");
            alert.setHeaderText("Choose another file");
            alert.setContentText("Please, choose the file of format jpg");
            alert.showAndWait();
            selectImage();
        }
    }
}