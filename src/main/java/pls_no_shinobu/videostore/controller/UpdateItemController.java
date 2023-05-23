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
import pls_no_shinobu.videostore.model.Item;

import java.io.IOException;
import java.util.Optional;

public class UpdateItemController {
    private Item item;

    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField stockField;
    @FXML private TextField feeField;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button deleteButton;

    @FXML private ComboBox<Item.RentalType> typeComboBox;
    @FXML private ComboBox<Item.LoanType> loanTypeComboBox;

    public void setItem(Item item) {
        this.item = item;

        idField.setText(item.getId());
        titleField.setText(item.getTitle());
        genreField.setText(item.getGenre());
        stockField.setText(String.format("%d", item.getStock()));
        feeField.setText(String.format("%f", item.getRentalFee()));
        typeComboBox.setValue(item.getRentalType());
        loanTypeComboBox.setValue(item.getLoanType());
    }

    @FXML
    public void initialize() {
        typeComboBox.setItems(
                FXCollections.observableArrayList(
                        Item.RentalType.DVD, Item.RentalType.GAME, Item.RentalType.RECORD));
        loanTypeComboBox.setItems(
                FXCollections.observableArrayList(Item.LoanType.ONE_WEEK, Item.LoanType.TWO_DAY));
    }

    @FXML
    public void onSaveButtonClick() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (!item.getTitle().equals(titleField.getText()))
                    item.setTitle(titleField.getText());
                if (!item.getGenre().equals(genreField.getText()))
                    item.setGenre(genreField.getText());
                if ((item.getStock() != Integer.parseInt(stockField.getText())))
                    item.setStock(Integer.parseInt(stockField.getText()));
                if (item.getRentalFee() != Float.parseFloat(feeField.getText()))
                    item.setRentalFee(Float.parseFloat(feeField.getText()));
                if (item.getRentalType() != typeComboBox.getValue())
                    item.setRentalType(typeComboBox.getValue());
                if (item.getLoanType() != loanTypeComboBox.getValue())
                    item.setLoanType(loanTypeComboBox.getValue());

                CSVDatabase.getInstance().updateItems();

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
                    new Alert(
                            Alert.AlertType.ERROR, "Cannot connect to database " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelButtonClick() {
        if (!idField.getText().isBlank()
                || !titleField.getText().isBlank()
                || !genreField.getText().isBlank()
                || !stockField.getText().isBlank()
                || !feeField.getText().isBlank()) {
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
            CSVDatabase.getInstance().getItems().remove(item);
            CSVDatabase.getInstance().updateItems();

            Alert successAlert =
                    new Alert(
                            Alert.AlertType.INFORMATION, "Successfully delete " + item.getTitle());
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
