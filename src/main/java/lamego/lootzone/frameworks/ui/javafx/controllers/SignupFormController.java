package lamego.lootzone.frameworks.ui.javafx.controllers;

import javafx.fxml.FXML;
import lamego.lootzone.frameworks.ui.javafx.enums.FormType;

import java.io.IOException;

public class SignupFormController {

    private LoginController parentController; // referência ao controller pai

    public void setParentController(LoginController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void onLogin() throws IOException {
        parentController.changeForms(FormType.SIGNUP);
    }
}
