package lamego.lootzone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lamego.lootzone.frameworks.ui.javafx.SceneManager;

import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("app/login/login-page.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        final double[] offset = new double[2];
        root.setOnMousePressed(e -> {
            offset[0] = e.getSceneX();
            offset[1] = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - offset[0]);
            stage.setY(e.getScreenY() - offset[1]);
        });

        SceneManager.setStage(stage);

        stage.setTitle("LootZone");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/lamego/lootzone/icons/bau.png"))));
        scene.setFill(Color.TRANSPARENT);// fundo totalmente transparente
        stage.initStyle(StageStyle.UNDECORATED); // sem bordas do SO
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
