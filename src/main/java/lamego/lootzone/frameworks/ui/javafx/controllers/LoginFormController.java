package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.fxml.FXML;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;

import java.io.IOException;

public class LoginFormController {

    private LoginController parentController; // referência ao controller pai

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void onCreateAccount() throws IOException {
        parentController.changeForms(FormType.LOGIN);
    }
}
