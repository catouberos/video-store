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
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.controller.utils.PaneUtils;
import pls_no_shinobu.videostore.controller.utils.SceneUtils;

import java.io.IOException;
import java.util.Optional;

public class AdminDashboardController {
    @FXML private Button logoutButton;
    @FXML private StackPane stackPane;

    @FXML private VBox accountContainer;
    @FXML private VBox stockContainer;
    @FXML private VBox rentalContainer;

    @FXML
    protected void onAccountButtonClick() {
        PaneUtils.setPane(stackPane, accountContainer);
    }

    @FXML
    protected void onStockButtonClick() {
        PaneUtils.setPane(stackPane, stockContainer);
    }

    @FXML
    protected void onRentalButtonClick() {
        PaneUtils.setPane(stackPane, rentalContainer);
    }

    @FXML
    protected void onLogoutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("We will miss you a lot.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                SceneUtils.switchScene((Stage) logoutButton.getScene().getWindow(), "signin.fxml");

                // TODO: session logout
            } catch (IOException e) {
                // catch any occurred errors
            }
        }
    }
}
