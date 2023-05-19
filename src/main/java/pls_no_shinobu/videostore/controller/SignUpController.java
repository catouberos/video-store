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

public class SignUpController {
    @FXML private Button signInButton;

    @FXML
    protected void onSignInButtonClick() {
        try {
            SceneUtils.switchScene((Stage) signInButton.getScene().getWindow(), "signin.fxml");
        } catch (IOException e) {
            // TODO: error handling
        }
    }
}
