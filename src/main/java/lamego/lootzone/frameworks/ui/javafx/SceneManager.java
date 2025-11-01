package lamego.lootzone.frameworks.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlPath)));
            primaryStage.setResizable(true);
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
