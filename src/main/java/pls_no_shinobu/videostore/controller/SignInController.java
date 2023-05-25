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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.controller.utils.SceneUtils;
import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;
import pls_no_shinobu.videostore.errors.IncorrectLoginInfo;
import pls_no_shinobu.videostore.errors.NotFoundException;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;

public class SignInController {
    @FXML private VBox signInContainer;
    @FXML private TextField usernameField;
    @FXML private TextField plainPasswordField;
    @FXML private PasswordField passwordField;
    @FXML private Button eyeToggleButton;
    @FXML private Button eyeToggleButtonClosed;
    @FXML private Button signUpButton;

    @FXML
    protected void onSignInContainerClick() {
        signInContainer.requestFocus();
    }

    @FXML
    protected void togglePasswordVisibility() {
        if (passwordField.isVisible()) {
            plainPasswordField.setText(passwordField.getText());
            plainPasswordField.setVisible(true);
            passwordField.setVisible(false);
            eyeToggleButton.setVisible(false);
            eyeToggleButtonClosed.setVisible(true);
        } else {
            passwordField.setText(plainPasswordField.getText());
            passwordField.setVisible(true);
            plainPasswordField.setVisible(false);
            eyeToggleButton.setVisible(true);
            eyeToggleButtonClosed.setVisible(false);
        }
    }

    @FXML
    protected void onPlainPasswordFieldChanged() {
        passwordField.setText(plainPasswordField.getText());
    }

    @FXML
    protected void onSignInButtonClick() {
        try {
            CSVDatabase database = CSVDatabase.getInstance();
            Session session = Session.getInstance();

            User user = database.getUsers().getByUsername(usernameField.getText());

            session.login(user, passwordField.getText());

            if (session.getCurrentUser().getRole() != User.UserType.ADMIN)
                SceneUtils.switchScene(
                        (Stage) usernameField.getScene().getWindow(), "userDashboard.fxml");
            else
                SceneUtils.switchScene(
                        (Stage) usernameField.getScene().getWindow(), "adminDashboard.fxml");
        } catch (IncorrectLoginInfo e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (NotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User not found.");
            alert.showAndWait();
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
