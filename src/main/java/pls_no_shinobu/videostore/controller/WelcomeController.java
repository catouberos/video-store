/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.controller.utils.SceneUtils;

import java.io.IOException;

public class WelcomeController {
    @FXML Button signInButton;
    @FXML Button signUpButton;

    @FXML
    protected void onSignInButtonClick() {
        try {
            SceneUtils.switchScene((Stage) signInButton.getScene().getWindow(), "signin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Something went wrong with the application. See stacktrace for more"
                                    + " info.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onSignUpButtonClick() {
        try {
            SceneUtils.switchScene((Stage) signUpButton.getScene().getWindow(), "signup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Something went wrong with the application. See stacktrace for more"
                                    + " info.");
            alert.showAndWait();
        }
    }
}
