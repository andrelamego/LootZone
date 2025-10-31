package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lamego.lootzone.application.services.UsuarioService;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;
import lamego.lootzone.infrastructure.database.IDBConnection;
import lamego.lootzone.infrastructure.database.SQLServer;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;
import lamego.lootzone.shared.utils.MaskUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignupFormController implements Initializable {

    @FXML private HBox errorWarning;
    @FXML private Label errorLabel;

    @FXML private TextField tfNome;
    @FXML private TextField tfSobrenome;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTelefone;
    @FXML private DatePicker dpNascimento;
    @FXML private PasswordField tfPassword;
    @FXML private PasswordField tfConfirmPassword;
    @FXML private Button btnContinuar;

    @FXML private VBox form;

    private Usuario usuario;
    private UsuarioService usuarioService;
    private LoginController parentController; // referência ao controller pai

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorWarning.setVisible(false);

        //formatação dos campos
        MaskUtils.aplicarMascaraTelefone(tfTelefone);
        MaskUtils.aplicarMascaraData(dpNascimento);

        // cria as dependências necessárias
        try {
            IDBConnection c = new SQLServer();
            var usuarioRepository = new UsuarioRepository(c);
            usuarioService = new UsuarioService(usuarioRepository);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onLogin(ActionEvent e) throws IOException {
        parentController.changeForms(FormType.LOGIN, form);
    }

    @FXML
    public void onSignupClicked() throws SQLException {
        String nome = tfNome.getText();
        String sobrenome = tfSobrenome.getText();
        String email = tfEmail.getText();
        String telefone = tfTelefone.getText();
        LocalDate dataNascimento = dpNascimento.getValue();
        String senha = tfPassword.getText();
        String confirmSenha = tfConfirmPassword.getText();

        if(nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty() ||
                dataNascimento == null || senha.isEmpty() || confirmSenha.isEmpty()){
            errorLabel.setText("Todos os campos são obrigatórios");
            errorWarning.setVisible(true);
            return;
        }

        if(!senha.equals(tfConfirmPassword.getText())){
            errorLabel.setText("A senha deve ser igual nos dois campos");
            errorWarning.setVisible(true);
            return;
        }

        try {
            boolean emailExists = usuarioService.emailExists(email);
            boolean telefoneExists = usuarioService.telefoneExists(telefone);

            if(emailExists){
                errorLabel.setText("O Endereço de Email já está sendo utilizado.");
                errorWarning.setVisible(true);
                return;
            }

            if(telefoneExists){
                errorLabel.setText("O Número de Telefone já está sendo utilizado.");
                errorWarning.setVisible(true);
                return;
            }

            usuario = new Usuario(nome, sobrenome, email, senha, telefone, dataNascimento);
            parentController.changeForms(FormType.ACCOUNTTYPE, form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Usuario getUsuario() {
        return usuario;
    }
}
