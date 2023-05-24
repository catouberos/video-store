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
import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.errors.IncorrectLoginInfo;
import pls_no_shinobu.videostore.errors.ManagerLimitException;
import pls_no_shinobu.videostore.model.User;
import pls_no_shinobu.videostore.utils.PasswordUtils;

import java.io.IOException;

public class SignUpController {
    @FXML private VBox signUpContainer;
    @FXML private TextField usernameField;
    @FXML private TextField plainPasswordField;
    @FXML private PasswordField passwordField;
    @FXML private Button eyeToggleButton;
    @FXML private Button eyeToggleButtonClosed;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private Button signInButton;

    @FXML
    protected void onSignUpContainerClick() {
        signUpContainer.requestFocus();
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
    protected void onSignUpButtonClick() {
        try {
            CSVDatabase database = CSVDatabase.getInstance();
            Session session = Session.getInstance();
            PasswordUtils p = new PasswordUtils();

            User user =
                    new User(
                            database.getUsers().getUnusedID(),
                            usernameField.getText().trim(),
                            p.hash(passwordField.getText()));

            if (!phoneField.getText().isBlank()) user.setPhone(phoneField.getText().trim());
            if (!addressField.getText().isBlank()) user.setAddress(addressField.getText().trim());

            database.getUsers().add(user);
            database.updateUsers();

            session.login(user, passwordField.getText());

            if (session.getCurrentUser().getRole() != User.UserType.ADMIN)
                SceneUtils.switchScene(
                        (Stage) usernameField.getScene().getWindow(), "userDashboard.fxml");
            else
                SceneUtils.switchScene(
                        (Stage) usernameField.getScene().getWindow(), "adminDashboard.fxml");
        } catch (DuplicateException | ManagerLimitException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IncorrectLoginInfo e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Incorrect login info. Probably something gone wrong");
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
}
