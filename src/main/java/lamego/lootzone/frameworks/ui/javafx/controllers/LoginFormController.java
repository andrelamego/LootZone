package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lamego.lootzone.application.services.UsuarioService;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;
import lamego.lootzone.infrastructure.database.IDBConnection;
import lamego.lootzone.infrastructure.database.SQLServer;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private HBox errorWarning;
    @FXML private Label messageLabel;

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
    public void onCreateAccount() throws IOException {
        //limpa campos
        errorWarning.setVisible(false);
        emailField.setText("");
        passwordField.setText("");


        parentController.changeForms(FormType.SIGNUP, form);
    }

    @FXML
    public void onLoginClicked() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Todos os campos devem ser preenchidos");
            errorWarning.setVisible(true);
            return;
        }

        try {
            boolean success = usuarioService.autenticar(email, password);

            if (success) {
                //TODO - Troca de tela
                messageLabel.setText("Login efetuado!");
                errorWarning.setVisible(true);
            }
            else{
                messageLabel.setText("Usuário ou senha incorretos!");
                errorWarning.setVisible(true);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
