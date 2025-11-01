package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {

    @FXML private BorderPane landingPage;
    @FXML private HBox header;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        landingPage.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), landingPage);
        fadeIn.setToValue(1);
        fadeIn.play();

        Platform.runLater(() -> {
            stage = (Stage) header.getScene().getWindow();

            final double[] offset = new double[2];
            header.setOnMousePressed(e -> {
                offset[0] = e.getSceneX();
                offset[1] = e.getSceneY();
            });
            header.setOnMouseDragged(e -> {
                stage.setX(e.getScreenX() - offset[0]);
                stage.setY(e.getScreenY() - offset[1]);
            });
        });


    }

    @FXML
    public void onCloseButtonClicked (ActionEvent e) {
        stage.close();
    }

    @FXML
    public void onMaximizeButtonClicked (ActionEvent e) {
        if (stage.isFullScreen() || stage.isMaximized()) {
            stage.setFullScreen(false);
            stage.setMaximized(false);
            stage.setWidth(1280);
            stage.setHeight(720);
            stage.centerOnScreen();
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    public void onMinimizeButtonClicked (ActionEvent e) {
        stage.setIconified(true);
    }
}
