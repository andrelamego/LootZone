package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lamego.lootzone.application.services.UsuarioService;
import lamego.lootzone.domain.entities.Comprador;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.domain.entities.VendedorPF;
import lamego.lootzone.domain.entities.VendedorPJ;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;
import lamego.lootzone.infrastructure.database.IDBConnection;
import lamego.lootzone.infrastructure.database.SQLServer;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;
import lamego.lootzone.shared.utils.MaskUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountTypeController implements Initializable {

    @FXML private ComboBox<String> cbAccountType;

    @FXML private VBox form;
    @FXML private VBox camposForm;
    @FXML private CheckBox checkPrivacyTerms;
    @FXML private CheckBox checkUsageData;

    @FXML private HBox errorWarning;
    @FXML private Label errorLabel;

    private TextField tfCredito;
    private TextField tfCPF;
    private TextField tfCNPJ;

    private Usuario usuario;
    private final String cbComprador = "Comprador";
    private final String cbVendedorPF = "Vendedor (Pessoa Física)";
    private final String cbVendedorPJ = "Vendedor (Pessoa Jurídica)";

    private UsuarioService usuarioService;
    private LoginController parentController; // referência ao controller pai

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorWarning.setVisible(false);

        cbAccountType.getItems().addAll(cbComprador, cbVendedorPF, cbVendedorPJ);

        tfCredito = new TextField();
        tfCredito.setPromptText("Crédito Inicial");
        tfCredito.setPrefHeight(40);
        tfCredito.setStyle("-fx-margin: 10 10 0 10;");
        tfCredito.getStyleClass().add("textfield");

        tfCPF = new TextField();
        tfCPF.setPromptText("CPF");
        tfCPF.setPrefHeight(40);
        tfCPF.setStyle("-fx-margin: 10 10 0 10;");
        tfCPF.getStyleClass().add("textfield");

        tfCNPJ = new TextField();
        tfCNPJ.setPromptText("CNPJ");
        tfCNPJ.setPrefHeight(40);
        tfCNPJ.setStyle("-fx-margin: 10 10 0 10;");
        tfCNPJ.getStyleClass().add("textfield");

        VBox.setMargin(tfCredito, new Insets(10, 10, 0, 10));
        VBox.setMargin(tfCPF, new Insets(10, 10, 0, 10));
        VBox.setMargin(tfCNPJ, new Insets(10, 10, 0, 10));

        cbAccountType.valueProperty().addListener((obs, oldValue, newValue) -> {
            camposForm.getChildren().remove(2);

            if(cbComprador.equals(newValue)){
                camposForm.getChildren().add(2, tfCredito);
            } else if (cbVendedorPF.equals(newValue)) {
                camposForm.getChildren().add(2, tfCPF);
            } else if (cbVendedorPJ.equals(newValue)) {
                camposForm.getChildren().add(2, tfCNPJ);
            }
        });

        //MASCARA DOS CAMPOS
        MaskUtils.aplicarMascaraMonetaria(tfCredito);
        MaskUtils.aplicarMascaraCPF(tfCPF);
        MaskUtils.aplicarMascaraCNPJ(tfCNPJ);

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
    public void onSignupClicked() throws SQLException {
        getUserInformation();

        if(cbAccountType.getValue() == null) {
            errorLabel.setText("Selecione um tipo de conta válido");
            errorWarning.setVisible(true);
            return;
        }

        String accountType = cbAccountType.getValue();

        if(tfCredito.getText().isEmpty() && tfCPF.getText().isEmpty() && tfCNPJ.getText().isEmpty()) {
            errorLabel.setText("Todos os campos são obrigatórios");
            errorWarning.setVisible(true);
            return;
        }

        if(!checkPrivacyTerms.isSelected()){
            errorLabel.setText("Concordar com os Termos de Privacidade é obrigatório");
            errorWarning.setVisible(true);
            return;
        }

        if(accountType.equals(cbComprador)){
            float credito = MaskUtils.parseValorMonetario(MaskUtils.limparFormatacao(tfCredito.getText()));

            if(credito < 10.0f) {
                errorLabel.setText("O valor mínimo é de R$ 10,00");
                errorWarning.setVisible(true);
                return;
            }

            Comprador comprador = new Comprador();
            comprador.setNome(usuario.getNome());
            comprador.setSobrenome(usuario.getSobrenome());
            comprador.setEmail(usuario.getEmail());
            comprador.setSenha(usuario.getSenha());
            comprador.setTelefone(usuario.getTelefone());
            comprador.setDataNascimento(usuario.getDataNascimento());
            comprador.setCredito(credito);

//            usuarioService.cadastrarUsuario(comprador);
        } else if (accountType.equals(cbVendedorPF)) {
            if(tfCPF.getText().length() != 14) {
                errorLabel.setText("CPF inválido!");
                errorWarning.setVisible(true);
                return;
            }

            String cpf = MaskUtils.limparFormatacao(tfCPF.getText());

            VendedorPF vendedorPF = new VendedorPF();
            vendedorPF.setNome(usuario.getNome());
            vendedorPF.setSobrenome(usuario.getSobrenome());
            vendedorPF.setEmail(usuario.getEmail());
            vendedorPF.setSenha(usuario.getSenha());
            vendedorPF.setTelefone(usuario.getTelefone());
            vendedorPF.setDataNascimento(usuario.getDataNascimento());
            vendedorPF.setCpf(cpf);

//            usuarioService.cadastrarUsuario(vendedorPF);
        } else if (accountType.equals(cbVendedorPJ)) {
            if(tfCNPJ.getText().length() != 18) {
                errorLabel.setText("CNPJ inválido!");
                errorWarning.setVisible(true);
                return;
            }

            String cnpj = MaskUtils.limparFormatacao(tfCNPJ.getText());

            VendedorPJ vendedorPJ = new VendedorPJ();
            vendedorPJ.setNome(usuario.getNome());
            vendedorPJ.setSobrenome(usuario.getSobrenome());
            vendedorPJ.setEmail(usuario.getEmail());
            vendedorPJ.setSenha(usuario.getSenha());
            vendedorPJ.setTelefone(usuario.getTelefone());
            vendedorPJ.setDataNascimento(usuario.getDataNascimento());
            vendedorPJ.setCnpj(cnpj);

//            usuarioService.cadastrarUsuario(vendedorPJ);
        }

        showConfirmationDialog();
        parentController.changeForms(FormType.LOGIN, form);
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cadastro efetuado com sucesso!", ButtonType.OK);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);

        // aplicar CSS
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/lamego/lootzone/css/alerts.css").toExternalForm()
        );

        alert.showAndWait();
    }

    private void getUserInformation() {
        SignupFormController signupController = (SignupFormController) parentController.getSignupController();
        usuario = signupController.getUsuario();
    }

    @FXML
    public void onLogin(ActionEvent e) throws IOException {
        errorWarning.setVisible(false);
        parentController.changeForms(FormType.LOGIN, form);
    }

    @FXML
    public void onGoBackClicked() {
        errorWarning.setVisible(false);
        parentController.changeForms(FormType.SIGNUP, form);
    }
}
