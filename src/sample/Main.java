package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/menu.fxml"));
        primaryStage.getIcons().add(new Image("/sample/assets/puzzle.png"));
        primaryStage.setTitle("Magic Puzzle");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
        javafx.scene.text.Font.getFamilies();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
