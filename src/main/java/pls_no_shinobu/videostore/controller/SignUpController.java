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
import javafx.scene.text.Text;
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
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private Button signInButton;
    @FXML private Text hint_1;
    @FXML private Text hint_2;
    @FXML private Text hint_3;
    @FXML private Text hint_4;


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



    @FXML
    protected void initialize(){
        hint_1.setVisible(false);
        hint_2.setVisible(false);
        hint_3.setVisible(false);
        hint_4.setVisible(false);
        usernameField.setOnMouseClicked(event -> hintOnUsernameClick());
        usernameField.setOnMouseExited(event -> hintOnUsernameLeave());
        passwordField.setOnMouseClicked(event -> hintOnPasswordClick());
        passwordField.setOnMouseExited(event -> hintOnPasswordLeave());
        addressField.setOnMouseClicked(event -> hintOnAddressClick());
        addressField.setOnMouseExited(event -> hintOnAddressLeave());
        phoneField.setOnMouseClicked(event -> hintOnPhoneClick());
        phoneField.setOnMouseExited(event -> hintOnPhoneLeave());
    }
    @FXML
    protected void hintOnUsernameClick(){
        hint_1.setVisible(true);
    }
    @FXML
    protected void hintOnPasswordClick(){
        hint_2.setVisible(true);
    }
    @FXML
    protected void hintOnPhoneClick(){
        hint_3.setVisible(true);
    }
    @FXML
    protected void hintOnAddressClick(){
        hint_4.setVisible(true);
    }
    @FXML
    protected void hintOnUsernameLeave(){
        hint_1.setVisible(false);
    }
    @FXML
    protected void hintOnPasswordLeave(){
        hint_2.setVisible(false);
    }
    @FXML
    protected void hintOnPhoneLeave(){
        hint_3.setVisible(false);
    }
    @FXML
    protected void hintOnAddressLeave(){
        hint_4.setVisible(false);
    }
}


