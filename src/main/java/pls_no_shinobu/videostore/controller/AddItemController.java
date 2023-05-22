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
import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.errors.ManagerLimitException;
import pls_no_shinobu.videostore.model.Item;

import java.io.IOException;
import java.util.Optional;

public class AddItemController {
    @FXML private TextField yearField;
    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField stockField;
    @FXML private TextField feeField;

    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @FXML private ComboBox<Item.RentalType> typeComboBox;
    @FXML private ComboBox<Item.LoanType> loanTypeComboBox;

    @FXML
    public void initialize() {
        typeComboBox.setItems(
                FXCollections.observableArrayList(
                        Item.RentalType.DVD, Item.RentalType.GAME, Item.RentalType.RECORD));
        loanTypeComboBox.setItems(
                FXCollections.observableArrayList(Item.LoanType.ONE_WEEK, Item.LoanType.TWO_DAY));
    }

    @FXML
    public void onAddButtonClick() {
        try {
            int year = Integer.parseInt(yearField.getText());

            Item item =
                    new Item(
                            CSVDatabase.getInstance().getItems().getUnusedID(year),
                            titleField.getText());

            item.setGenre(genreField.getText());
            item.setStock(Integer.parseInt(stockField.getText()));
            item.setRentalFee(Float.parseFloat(feeField.getText()));
            item.setRentalType(typeComboBox.getValue());
            item.setLoanType(loanTypeComboBox.getValue());

            CSVDatabase.getInstance().getItems().add(item);
            CSVDatabase.getInstance().updateItems();

            ((Stage) addButton.getScene().getWindow()).close();
        } catch (IllegalArgumentException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (ManagerLimitException e) {
            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR,
                            "There's no available ID. Details: " + e.getMessage());
            alert.showAndWait();
        } catch (DuplicateException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Item is duplicate: " + e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert =
                    new Alert(Alert.AlertType.ERROR, "Cannot connect to database" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelButtonClick() {
        if (!yearField.getText().isBlank()
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
}
