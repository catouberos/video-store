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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    @FXML private Button askSignUp;

    @FXML private Button askLogIn;

    @FXML
    public void switchScene(Button button, String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000,700);
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
}