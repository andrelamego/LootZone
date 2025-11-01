package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lamego.lootzone.application.exceptions.RegraNegocioException;
import lamego.lootzone.application.services.UsuarioService;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;
import lamego.lootzone.infrastructure.database.IDBConnection;
import lamego.lootzone.infrastructure.database.SQLServer;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;
import lamego.lootzone.shared.utils.MaskUtils;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
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
    public void onSignupClicked() {
        getUserInformation();

        if(cbAccountType.getValue() == null) {
            showError("Selecione um tipo de conta válido");
            return;
        }

        if(tfCredito.getText().isEmpty() && tfCPF.getText().isEmpty() && tfCNPJ.getText().isEmpty()) {
            showError("Todos os campos são obrigatórios");
            return;
        }

        if(!checkPrivacyTerms.isSelected()){
            showError("Concordar com os Termos de Privacidade é obrigatório");
            return;
        }

        String accountType = cbAccountType.getValue();

        try {
            switch (accountType){
                case cbComprador -> usuarioService.cadastrarComprador(usuario, tfCredito.getText());
                case cbVendedorPF -> usuarioService.cadastrarVendedorPF(usuario, tfCPF.getText());
                case cbVendedorPJ -> usuarioService.cadastrarVendedorPJ(usuario, tfCNPJ.getText());
            }

            showConfirmationDialog();
            parentController.changeForms(FormType.LOGIN, form);
        } catch (RegraNegocioException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            showError(e.getMessage());
            e.printStackTrace();
        }
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cadastro efetuado com sucesso!", ButtonType.OK);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);

        // aplicar CSS
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/lamego/lootzone/css/alerts.css")).toExternalForm()
        );

        alert.showAndWait();
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorWarning.setVisible(true);
    }

    private void getUserInformation() {
        SignupFormController signupController = (SignupFormController) parentController.getSignupController();
        usuario = signupController.getUsuario();
    }

    @FXML
    public void onLogin(ActionEvent e) {
        errorWarning.setVisible(false);
        parentController.changeForms(FormType.LOGIN, form);
    }

    @FXML
    public void onGoBackClicked() {
        errorWarning.setVisible(false);
        parentController.changeForms(FormType.SIGNUP, form);
    }
}
