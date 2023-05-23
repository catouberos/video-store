/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.errors.NotFoundException;
import pls_no_shinobu.videostore.model.User;

import java.io.IOException;
import java.util.Optional;

public class UpdateUserController {
    private User user;

    @FXML private TextField idField;
    @FXML private TextField usernameField;
    @FXML private TextField nameField;
    @FXML private TextArea addressField;
    @FXML private TextField phoneField;
    @FXML private TextField rentalCountField;
    @FXML private ComboBox<User.UserType> roleComboBox;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button deleteButton;

    public void setUser(User user) {
        this.user = user;

        idField.setText(user.getId());
        usernameField.setText(user.getUsername());
        nameField.setText(user.getName());
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        rentalCountField.setText(String.format("%d", user.getRentalCount()));
        roleComboBox.setValue(user.getRole());
    }

    @FXML
    public void initialize() {
        roleComboBox.setItems(
                FXCollections.observableArrayList(
                        User.UserType.GUEST,
                        User.UserType.REGULAR,
                        User.UserType.VIP,
                        User.UserType.ADMIN));
    }

    @FXML
    public void onSaveButtonClick() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (!user.getUsername().equals(usernameField.getText()))
                    user.setUsername(usernameField.getText());
                if (user.getName() == null || !user.getName().equals(nameField.getText())) user.setName(nameField.getText());
                if (user.getAddress() == null || !user.getAddress().equals(addressField.getText()))
                    user.setAddress(addressField.getText());
                if (user.getPhone() == null || !user.getPhone().equals(phoneField.getText()))
                    user.setPhone(phoneField.getText());
                if (user.getRentalCount() != Integer.parseInt(rentalCountField.getText()))
                    user.setRentalCount(Integer.parseInt(rentalCountField.getText()));
                if (user.getRole() != roleComboBox.getValue())
                    user.setRole(roleComboBox.getValue());

                CSVDatabase.getInstance().updateUsers();

                Alert successAlert =
                        new Alert(Alert.AlertType.INFORMATION, "Changes successfully saved");
                successAlert.showAndWait();

                ((Stage) saveButton.getScene().getWindow()).close();
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert =
                    new Alert(Alert.AlertType.ERROR, "Cannot connect to database" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelButtonClick() {
        if (!idField.getText().isBlank()
                || !usernameField.getText().isBlank()
                || !nameField.getText().isBlank()
                || !addressField.getText().isBlank()
                || !phoneField.getText().isBlank()
                || !rentalCountField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Unsaved changes");
            alert.setHeaderText("You have unsaved changes. Discard changes and close?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
                ((Stage) cancelButton.getScene().getWindow()).close();
        } else {
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }

    public void onDeleteButtonClick() {
        try {
            CSVDatabase.getInstance().getUsers().remove(user);
            CSVDatabase.getInstance().updateUsers();

            Alert successAlert =
                    new Alert(
                            Alert.AlertType.INFORMATION,
                            "Successfully delete " + user.getUsername());
            successAlert.showAndWait();

            ((Stage) deleteButton.getScene().getWindow()).close();
        } catch (NotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR, "Cannot connect to database " + e.getMessage());
            alert.showAndWait();
        }
    }
}
