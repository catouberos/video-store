/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class SceneController {
    //For sign in, sign up, log out operations
    @FXML private Button askSignUp;

    @FXML private Button askLogIn;

    @FXML private Button logout;

    @FXML
    public void switchScene(Button button, String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

        Scene scene = new Scene(loader.load(), 1000, 700);

        URL stylesheet = HelloApplication.class.getResource("css/main.css");

        if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());

        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onAskLogInButtonClick() throws IOException {
        switchScene(askLogIn, "signin.fxml");
    }

    @FXML
    public void onAskSignUpButtonClick() throws IOException {
        switchScene(askSignUp, "signup.fxml");
    }

    @FXML
    public void onLogoutButtonClick() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("We will miss you a lot");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            switchScene(logout, "signin.fxml");
        }
    }

    //For dashboard usage operations
    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane ItemPane;
    @FXML
    private GridPane rentedPane;
    @FXML
    private GridPane profilePane;
    @FXML
    private GridPane cartPane;
    @FXML
    private GridPane accountPane;
    @FXML
    private GridPane rentalPane;
    @FXML
    private GridPane stockPane;

    public void showGridPane(GridPane gridPane) {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(gridPane);
    }
        //User dashboard
    @FXML
    public void onRentedItemButtonClick() {
        showGridPane(rentedPane);
    }
    @FXML
    public void onAllItemButtonClick() {
        showGridPane(ItemPane);
    }
    @FXML
    public void onProfileButtonClick() {
        profilePane.setVisible(true);
        showGridPane(profilePane);
    }
    @FXML
    public void onCartButtonClick() {
        cartPane.setVisible(true);
        showGridPane(cartPane);
    }
        //Admin dashboard
    @FXML
    public void onAccountButtonClick() {
        showGridPane(accountPane);
    }
    @FXML
    public void onStockButtonClick() {
        stockPane.setVisible(true);
        showGridPane(stockPane);
    }
    @FXML
    public void onRentalButtonClick() {
        showGridPane(rentalPane);
    }
}
