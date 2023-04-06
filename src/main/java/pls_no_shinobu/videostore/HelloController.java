/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
  Acknowledgement: Acknowledge the resources that you use here.
*/
package pls_no_shinobu.videostore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
