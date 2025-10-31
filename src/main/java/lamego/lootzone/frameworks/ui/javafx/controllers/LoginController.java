package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private VBox formBox;
    @FXML
    private VBox logoBox;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button closeButton;

    private VBox loginForm;
    private VBox signupForm;
    private VBox accountTypeForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                //Respiração do fundo
        applyFluidGradientWithFlow(logoBox);

        // Inicialmente, o formBox fica fora da tela
        formBox.setPrefWidth(0);
        imgLogo.setOpacity(0);

        // Fade do logo
        FadeTransition fadeLogo = new FadeTransition(Duration.seconds(2), imgLogo);
        fadeLogo.setInterpolator(Interpolator.EASE_IN);
        fadeLogo.setToValue(1);
        fadeLogo.play();

        // Espera 2 segundos e depois executa a animação
        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        PauseTransition waitPane = new PauseTransition(Duration.seconds(2.6));
        wait.setOnFinished(event -> {
            // Animação de expansão de largura
            Timeline expand = new Timeline(
                    new KeyFrame(Duration.seconds(0.6),
                            new KeyValue(formBox.prefWidthProperty(), 350, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
            );

            expand.play();
        });
        wait.play();

        //carrega formulario
        waitPane.setOnFinished(event -> {
            loadForms();

            //Fade in suave do novo formulário
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), loginForm);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        waitPane.play();
    }

    @FXML
    public void onCloseButton (ActionEvent e) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    Object signupFormC;

    private void loadForms() {
        try {
            FXMLLoader fxmlLoaderLogin = new FXMLLoader(getClass().getResource("/lamego/lootzone/app/login/login-form.fxml"));
            FXMLLoader fxmlLoaderSignup = new FXMLLoader(getClass().getResource("/lamego/lootzone/app/login/signup-form.fxml"));
            FXMLLoader fxmlLoaderAccountType = new FXMLLoader(getClass().getResource("/lamego/lootzone/app/login/account-type-form.fxml"));
            loginForm = fxmlLoaderLogin.load();
            signupForm = fxmlLoaderSignup.load();
            accountTypeForm = fxmlLoaderAccountType.load();
            signupForm.setPrefWidth(10);
            signupForm.setOpacity(0);
            accountTypeForm.setPrefWidth(10);
            accountTypeForm.setOpacity(0);

            Object loginFormC = fxmlLoaderLogin.getController();
            if (loginFormC instanceof LoginFormController loginFormController) {
                loginFormController.setParentController(this);
            }

            signupFormC = fxmlLoaderSignup.getController();
            if (signupFormC instanceof SignupFormController signupController) {
                signupController.setParentController(this);
            }

            Object accountTypeC = fxmlLoaderAccountType.getController();
            if(accountTypeC instanceof AccountTypeController accountTypeController) {
                accountTypeController.setParentController(this);
            }

            loginForm.setOpacity(0);
            formBox.getChildren().add(loginForm);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Object getSignupController() {
        return signupFormC;
    }

    public void changeForms(FormType formType, VBox leavingForm) {
        switch (formType) {
            case SIGNUP -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), leavingForm);
                fadeOut.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0));
                fadeOut.setToValue(0);

                Timeline expand = new Timeline(
                        new KeyFrame(Duration.seconds(0.3),
                                new KeyValue(formBox.prefWidthProperty(), 470, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
                );

                Timeline expandForm = new Timeline(
                        new KeyFrame(Duration.seconds(0.3),
                                new KeyValue(loginForm.prefWidthProperty(), 400, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
                );

                ParallelTransition animation = new ParallelTransition(expand, fadeOut, expandForm);
                animation.setOnFinished(event -> {
                    formBox.getChildren().clear();

                    signupForm.setPrefWidth(400);
                    formBox.getChildren().add(signupForm);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.6), signupForm);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                });
                animation.play();
            }
            case LOGIN -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), leavingForm);
                fadeOut.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0));
                fadeOut.setToValue(0);

                Timeline expand = new Timeline(
                        new KeyFrame(Duration.seconds(0.3),
                                new KeyValue(formBox.prefWidthProperty(), 350, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
                );

                Timeline collapse = new Timeline(
                        new KeyFrame(Duration.seconds(0.3),
                                new KeyValue(leavingForm.prefWidthProperty(), 280, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
                );

                ParallelTransition animation = new ParallelTransition(expand, fadeOut, collapse);
                animation.setOnFinished(event -> {
                    formBox.getChildren().clear();

                    loginForm.setPrefWidth(280);
                    formBox.getChildren().add(loginForm);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.6), loginForm);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                });
                animation.play();
            }
            case ACCOUNTTYPE -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), signupForm);
                fadeOut.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0));
                fadeOut.setToValue(0);

//                Timeline expand = new Timeline(
//                        new KeyFrame(Duration.seconds(0.3),
//                                new KeyValue(formBox.prefWidthProperty(), 470, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
//                );
//
//                Timeline expandForm = new Timeline(
//                        new KeyFrame(Duration.seconds(0.3),
//                                new KeyValue(signupForm.prefWidthProperty(), 400, Interpolator.SPLINE(0.0, 0.0, 0.4, 1.0)))
//                );

//                ParallelTransition animation = new ParallelTransition(expand, fadeOut, expandForm);
                fadeOut.setOnFinished(event -> {
                    formBox.getChildren().clear();

                    accountTypeForm.setPrefWidth(400);
                    formBox.getChildren().add(accountTypeForm);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.6), accountTypeForm);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                });
                fadeOut.play();
            }
        }
    }

    /**
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
}
