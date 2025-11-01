package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lamego.lootzone.application.exceptions.RegraNegocioException;
import lamego.lootzone.application.services.UsuarioService;
import lamego.lootzone.frameworks.ui.javafx.SceneManager;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;
import lamego.lootzone.infrastructure.database.IDBConnection;
import lamego.lootzone.infrastructure.database.SQLServer;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;
import lamego.lootzone.shared.utils.WindowAnimator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private HBox errorWarning;
    @FXML private Label errorLabel;

    @FXML private VBox form;

    private LoginController parentController;
    private UsuarioService usuarioService;

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorWarning.setVisible(false);

        // cria as dependências necessárias
        try {
            IDBConnection c = new SQLServer();
            var userRepository = new UsuarioRepository(c);
            usuarioService = new UsuarioService(userRepository);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onCreateAccount() {
        //limpa campos
        errorWarning.setVisible(false);
        emailField.setText("");
        passwordField.setText("");


        parentController.changeForms(FormType.SIGNUP, form);
    }

    @FXML
    public void onLoginClicked(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()) {
            showError("Todos os campos devem ser preenchidos");
            return;
        }

        try {
            usuarioService.autenticar(email, password);

            //TROCA DE TELA
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            parentController.changeSceneAnimation(stage);

            PauseTransition wait = new PauseTransition(Duration.seconds(0.6));
            wait.setOnFinished(e -> {
                SceneManager.changeScene("/lamego/lootzone/app/landing/landing-page.fxml");
            });
            wait.play();

        } catch (RegraNegocioException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorWarning.setVisible(true);
    }
}
