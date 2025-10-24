package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private VBox formBox;
    @FXML
    private VBox logoBox;
    private VBox form;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                //Respiração do fundo
        applyFluidGradientWithFlow(logoBox);

        // Inicialmente, o loginPane fica fora da tela
        formBox.setPrefWidth(0);

        // Espera 2 segundos e depois executa a animação
        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        PauseTransition waitPane = new PauseTransition(Duration.seconds(2.6));
        wait.setOnFinished(event -> {
            // Animação de expansão de largura
            Timeline expand = new Timeline(
                    new KeyFrame(Duration.seconds(0.6),
                            new KeyValue(formBox.prefWidthProperty(), 350))
            );

            expand.play();

//            ParallelTransition animation = new ParallelTransition(expand, fade);
//            animation.play();
        });
        wait.play();

        waitPane.setOnFinished(event -> {
            //CARREGA FORMULARIO DE LOGIN
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/lamego/lootzone/app/login/login-form.fxml"));
                form = fxmlLoader.load();
                form.setOpacity(0);
                formBox.getChildren().add(form);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Fade do conteúdo após expansão começar
            FadeTransition fade = new FadeTransition(Duration.seconds(0.6), form);
            fade.setToValue(1);
            fade.play();
        });
        waitPane.play();
    }

    /**
     * Aplica um efeito de degradê fluido moderno com movimento diagonal.
     * Cores: Roxo #791BF5 e Laranja #FF540D.
     */
    public static void applyFluidGradientWithFlow(Pane pane) {
        // Cores base
        Color color1Start = Color.web("#791BF5");
        Color color2Start = Color.web("#FF540D");

        Color color1End = Color.web("#A020FF"); // variação suave do roxo
        Color color2End = Color.web("#FF3F00"); // variação suave do laranja

        // Propriedades animáveis das cores
        ObjectProperty<Color> color1 = new SimpleObjectProperty<>(color1Start);
        ObjectProperty<Color> color2 = new SimpleObjectProperty<>(color2Start);

        // Propriedades animáveis da posição do degradê (movimento diagonal)
        SimpleDoubleProperty startX = new SimpleDoubleProperty(0);
        SimpleDoubleProperty startY = new SimpleDoubleProperty(0);
        SimpleDoubleProperty endX = new SimpleDoubleProperty(1);
        SimpleDoubleProperty endY = new SimpleDoubleProperty(1);

        // Atualiza o fundo
        Runnable updateBackground = () -> {
            LinearGradient gradient = new LinearGradient(
                    startX.get(), startY.get(),
                    endX.get(), endY.get(),
                    true, CycleMethod.NO_CYCLE,
                    new Stop(0, color1.get()),
                    new Stop(1, color2.get())
            );
            pane.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));
        };

        // Listeners
        color1.addListener((obs, oldVal, newVal) -> updateBackground.run());
        color2.addListener((obs, oldVal, newVal) -> updateBackground.run());
        startX.addListener((obs, oldVal, newVal) -> updateBackground.run());
        startY.addListener((obs, oldVal, newVal) -> updateBackground.run());
        endX.addListener((obs, oldVal, newVal) -> updateBackground.run());
        endY.addListener((obs, oldVal, newVal) -> updateBackground.run());

        // Timeline para transição de cores
        Timeline colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(color1, color1Start),
                        new KeyValue(color2, color2Start)
                ),
                new KeyFrame(Duration.seconds(4),
                        new KeyValue(color1, color1End),
                        new KeyValue(color2, color2End)
                ),
                new KeyFrame(Duration.seconds(8),
                        new KeyValue(color1, color1Start),
                        new KeyValue(color2, color2Start)
                )
        );
        colorTimeline.setCycleCount(Timeline.INDEFINITE);
        colorTimeline.setAutoReverse(true);

        // Timeline para movimento diagonal do degradê
        Timeline flowTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(startX, 0),
                        new KeyValue(startY, 0),
                        new KeyValue(endX, 1),
                        new KeyValue(endY, 1)
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(startX, 0.2),
                        new KeyValue(startY, 0.2),
                        new KeyValue(endX, 1.2),
                        new KeyValue(endY, 1.2)
                )
        );
        flowTimeline.setCycleCount(Timeline.INDEFINITE);
        flowTimeline.setAutoReverse(true);

        // Executa as duas animações em paralelo
        ParallelTransition parallel = new ParallelTransition(colorTimeline, flowTimeline);
        parallel.play();

        // Atualiza inicialmente
        updateBackground.run();
    }

    /**
     * Aplica um efeito de degradê fluido moderno com movimento diagonal
     * usando 5 cores vibrantes.
     *
     * @param pane o Pane que receberá o efeito
     */
    public static void applyFluidGradientWithFlow2(Pane pane) {
        // Propriedades animáveis da posição do degradê
        SimpleDoubleProperty startX = new SimpleDoubleProperty(0);
        SimpleDoubleProperty startY = new SimpleDoubleProperty(0);
        SimpleDoubleProperty endX = new SimpleDoubleProperty(1);
        SimpleDoubleProperty endY = new SimpleDoubleProperty(1);

        // Atualiza o fundo com 5 cores vibrantes
        Runnable updateBackground = () -> {
            LinearGradient gradient = new LinearGradient(
                    startX.get(), startY.get(),
                    endX.get(), endY.get(),
                    true, CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.web("#A020FF")), // roxo intenso
                    new Stop(0.25, Color.web("#C040FF")), // roxo mais claro
                    new Stop(0.5, Color.web("#FF3F00")), // laranja intenso
                    new Stop(0.75, Color.web("#FF6A2B")), // laranja mais claro
                    new Stop(1.0, Color.web("#FF8C4D")) // laranja suave
            );
            pane.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));
        };

        // Listeners para atualizar o fundo quando a posição mudar
        startX.addListener((obs, oldVal, newVal) -> updateBackground.run());
        startY.addListener((obs, oldVal, newVal) -> updateBackground.run());
        endX.addListener((obs, oldVal, newVal) -> updateBackground.run());
        endY.addListener((obs, oldVal, newVal) -> updateBackground.run());

        // Timeline para movimento diagonal do degradê
        Timeline flowTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(startX, 0),
                        new KeyValue(startY, 0),
                        new KeyValue(endX, 1),
                        new KeyValue(endY, 1)
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(startX, 0.2),
                        new KeyValue(startY, 0.2),
                        new KeyValue(endX, 1.2),
                        new KeyValue(endY, 1.2)
                )
        );
        flowTimeline.setCycleCount(Timeline.INDEFINITE);
        flowTimeline.setAutoReverse(true);

        // Timeline extra opcional para um leve “pulso” de brilho (opacidade)
        Timeline opacityTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pane.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(pane.opacityProperty(), 0.9)),
                new KeyFrame(Duration.seconds(6), new KeyValue(pane.opacityProperty(), 1.0))
        );
        opacityTimeline.setCycleCount(Timeline.INDEFINITE);
        opacityTimeline.setAutoReverse(true);

        // Executa movimento + pulso em paralelo
        ParallelTransition parallel = new ParallelTransition(flowTimeline, opacityTimeline);
        parallel.play();

        // Atualiza fundo inicialmente
        updateBackground.run();
    }
}
