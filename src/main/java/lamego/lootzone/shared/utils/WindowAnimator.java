package lamego.lootzone.shared.utils;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindowAnimator {
    public static void expandWindow(Stage stage, double targetWidth, double targetHeight) {
        double startWidth = stage.getWidth();
        double startHeight = stage.getHeight();

        // Propriedades temporárias que o Timeline pode manipular
        DoubleProperty width = new SimpleDoubleProperty(startWidth);
        DoubleProperty height = new SimpleDoubleProperty(startHeight);

        // Listener que aplica o valor animado no Stage
        width.addListener((obs, oldVal, newVal) -> stage.setWidth(newVal.doubleValue()));
        height.addListener((obs, oldVal, newVal) -> stage.setHeight(newVal.doubleValue()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(width, startWidth, Interpolator.EASE_OUT),
                        new KeyValue(height, startHeight, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(width, targetWidth, Interpolator.EASE_OUT),
                        new KeyValue(height, targetHeight, Interpolator.EASE_OUT)
                )
        );

        timeline.play();
    }

    public static void expandFromCenter(Stage stage, double targetWidth, double targetHeight) {
        double startWidth = stage.getWidth();
        double startHeight = stage.getHeight();
        double startX = stage.getX();
        double startY = stage.getY();

        // Calcula deslocamento para manter centralizado
        double deltaWidth = targetWidth - startWidth;
        double deltaHeight = targetHeight - startHeight;

        // Propriedades animáveis
        DoubleProperty width = new SimpleDoubleProperty(startWidth);
        DoubleProperty height = new SimpleDoubleProperty(startHeight);
        DoubleProperty x = new SimpleDoubleProperty(startX);
        DoubleProperty y = new SimpleDoubleProperty(startY);

        // Listeners aplicam os valores no Stage
        width.addListener((obs, oldVal, newVal) -> stage.setWidth(newVal.doubleValue()));
        height.addListener((obs, oldVal, newVal) -> stage.setHeight(newVal.doubleValue()));
        x.addListener((obs, oldVal, newVal) -> stage.setX(newVal.doubleValue()));
        y.addListener((obs, oldVal, newVal) -> stage.setY(newVal.doubleValue()));

        // Timeline da animação
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(width, startWidth, Interpolator.EASE_OUT),
                        new KeyValue(height, startHeight, Interpolator.EASE_OUT),
                        new KeyValue(x, startX, Interpolator.EASE_OUT),
                        new KeyValue(y, startY, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(width, targetWidth, Interpolator.EASE_OUT),
                        new KeyValue(height, targetHeight, Interpolator.EASE_OUT),
                        new KeyValue(x, startX - deltaWidth / 2, Interpolator.EASE_OUT),
                        new KeyValue(y, startY - deltaHeight / 2, Interpolator.EASE_OUT)
                )
        );

        timeline.play();
    }

    public static void expandFromCenter(Stage stage, double targetW, double targetH, Parent root) {
        // ativa cache para diminuir custo de repintura
        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);

        double startW = stage.getWidth();
        double startH = stage.getHeight();
        double startX = stage.getX();
        double startY = stage.getY();
        double deltaW = targetW - startW;
        double deltaH = targetH - startH;

        DoubleProperty w = new SimpleDoubleProperty(startW);
        DoubleProperty h = new SimpleDoubleProperty(startH);
        DoubleProperty x = new SimpleDoubleProperty(startX);
        DoubleProperty y = new SimpleDoubleProperty(startY);

        w.addListener((o, oldV, newV) -> stage.setWidth(newV.doubleValue()));
        h.addListener((o, oldV, newV) -> stage.setHeight(newV.doubleValue()));
        x.addListener((o, oldV, newV) -> stage.setX(newV.doubleValue()));
        y.addListener((o, oldV, newV) -> stage.setY(newV.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(w, startW, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(h, startH, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(x, startX, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(y, startY, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0))
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(w, targetW, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(h, targetH, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(x, startX - deltaW / 2, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)),
                        new KeyValue(y, startY - deltaH / 2, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0))
                )
        );

        tl.setOnFinished(ev -> {
            // libera cache para as renderizações normais voltarem
            root.setCache(false);
        });

        tl.play();
    }

    public static void expandSmooth(Stage stage, double targetW, double targetH, Parent root) {
        // 1) calcula novo X/Y para manter centrado
        double startW = stage.getWidth(), startH = stage.getHeight();
        double startX = stage.getX(), startY = stage.getY();
        double deltaW = targetW - startW, deltaH = targetH - startH;
        double newX = startX - deltaW / 2;
        double newY = startY - deltaH / 2;

        // 2) aplica novo tamanho primeiro (evita vários layouts durante animation)
        stage.setX(newX);
        stage.setY(newY);
        stage.setWidth(targetW);
        stage.setHeight(targetH);

        // 3) prepara root para animação (escala menor + invisível)
        root.setScaleX(0.85);
        root.setScaleY(0.85);
        root.setOpacity(0);

        // ativar cache para acelerar transformações
        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);

        // 4) anima scale + fade
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.45), root);
        scale.setFromX(0.85); scale.setFromY(0.85);
        scale.setToX(1.0); scale.setToY(1.0);
        scale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.seconds(0.45), root);
        fade.setFromValue(0); fade.setToValue(1.0);
        fade.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition p = new ParallelTransition(scale, fade);
        p.setOnFinished(e -> {
            root.setCache(false);
            // se quiser resetar transforms:
            root.setScaleX(1); root.setScaleY(1);
        });
        p.play();
    }

    public static void animateStageSize(Stage stage, double newWidth, double newHeight, Duration duration) {
        if (!stage.isShowing()) {
            stage.show();
        }

        final double startWidth = stage.getWidth();
        final double startHeight = stage.getHeight();
        final double startX = stage.getX();
        final double startY = stage.getY();

        final double deltaWidth = newWidth - startWidth;
        final double deltaHeight = newHeight - startHeight;

        Transition resizeTransition = new Transition() {
            {
                setCycleDuration(duration);
                // EASE_BOTH is typically the smoothest for start/end
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                // Calculate new dimensions
                double currentWidth = startWidth + deltaWidth * frac;
                double currentHeight = startHeight + deltaHeight * frac;

                // Set the new dimensions
                stage.setWidth(currentWidth);
                stage.setHeight(currentHeight);

                // Adjust X and Y positions to keep the center fixed
                stage.setX(startX - (currentWidth - startWidth) / 2.0);
                stage.setY(startY - (currentHeight - startHeight) / 2.0);
            }
        };

        resizeTransition.play();
    }
}
