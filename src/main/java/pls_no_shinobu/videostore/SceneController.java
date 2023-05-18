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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class SceneController {
    @FXML private Button askSignUp;

    @FXML private Button askLogIn;

    @FXML private Button RentedItem;

    @FXML private Button AllItem;

    @FXML private Button profile1;

    @FXML private  Button profile2;

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
    public void onRentedItemButtonClick() throws IOException {
        switchScene(RentedItem, "renteditem.fxml");
    }

    @FXML
    public void onAllItemButtonClick() throws IOException {
        switchScene(AllItem, "userdashboard.fxml");
    }

    @FXML
    public void onProfile1ButtonClick() throws IOException {
        switchScene(profile1, "profile.fxml");
    }

    @FXML
    public void onProfile2ButtonClick() throws IOException {
        switchScene(profile2, "profile.fxml");
    }
    @FXML
    public void onLogoutButtonClick() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("We will miss you a lot");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            switchScene(logout, "signin.fxml") ;
        }
    }
}
