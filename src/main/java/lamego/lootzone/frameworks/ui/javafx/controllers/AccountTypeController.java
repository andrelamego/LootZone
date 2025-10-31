package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountTypeController implements Initializable {

    @FXML private ComboBox<String> cbAccountType;

    @FXML private VBox form;
    @FXML private VBox camposForm;

    @FXML private HBox errorWarning;
    @FXML private Label errorLabel;

    private Usuario usuario;
    private LoginController parentController; // referência ao controller pai

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorWarning.setVisible(false);

        String comprador = "Comprador";
        String vendedorPF = "Vendedor (Pessoa Física)";
        String vendedorPJ = "Vendedor (Pessoa Jurídica)";
        cbAccountType.getItems().addAll(comprador, vendedorPF, vendedorPJ);

        TextField credito = new TextField();
        credito.setPromptText("Crédito Inicial");
        credito.setPrefHeight(40);
        credito.setStyle("-fx-margin: 10 10 0 10;");
        credito.getStyleClass().add("textfield");

        TextField cpf = new TextField();
        cpf.setPromptText("CPF");
        cpf.setPrefHeight(40);
        cpf.setStyle("-fx-margin: 10 10 0 10;");
        cpf.getStyleClass().add("textfield");

        TextField cnpj = new TextField();
        cnpj.setPromptText("CNPJ");
        cnpj.setPrefHeight(40);
        cnpj.setStyle("-fx-margin: 10 10 0 10;");
        cnpj.getStyleClass().add("textfield");

        VBox.setMargin(credito, new Insets(10, 10, 0, 10));
        VBox.setMargin(cpf, new Insets(10, 10, 0, 10));
        VBox.setMargin(cnpj, new Insets(10, 10, 0, 10));

        cbAccountType.valueProperty().addListener((obs, oldValue, newValue) -> {
            camposForm.getChildren().remove(2);

            if(comprador.equals(newValue)){
                camposForm.getChildren().add(2, credito);
            } else if (vendedorPF.equals(newValue)) {
                camposForm.getChildren().add(2, cpf);
            } else if (vendedorPJ.equals(newValue)) {
                camposForm.getChildren().add(2, cnpj);
            }
        });

    }

    @FXML
    public void onSignupClicked() {

    }

    public void getUserInformation() {
        SignupFormController signupController = (SignupFormController) parentController.getSignupController();
        usuario = signupController.getUsuario();
    }

    @FXML
    public void onLogin(ActionEvent e) throws IOException {
        parentController.changeForms(FormType.LOGIN, form);
    }

    @FXML
    public void onGoBackClicked() {
        parentController.changeForms(FormType.SIGNUP, form);
    }
}
