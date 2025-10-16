package lamego.lootzone.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-app.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);
        stage.setTitle("LootZone");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
