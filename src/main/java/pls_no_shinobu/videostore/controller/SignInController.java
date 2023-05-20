/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.controller.utils.SceneUtils;

import java.io.IOException;

public class SignInController {
    @FXML private Button signUpButton;

    @FXML
    protected void onSignUpButtonClick() {
        try {
            SceneUtils.switchScene((Stage) signUpButton.getScene().getWindow(), "signup.fxml");
        } catch (IOException e) {
            // TODO: error handling
        }
    }
}
