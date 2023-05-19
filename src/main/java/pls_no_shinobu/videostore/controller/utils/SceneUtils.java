/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.HelloApplication;

import java.io.IOException;
import java.net.URL;

/**
 * Utilities for handling JavaFX {@link Scene}
 *
 * @author Do Khoa Nguyen
 */
public class SceneUtils {
    /**
     * Utility to conveniently switch scene on a defined {@link Stage}
     *
     * @author Do Khoa Nguyen
     * @param stage the current {@link Stage} for handling scene changes
     * @param fxmlFileName a string path to the fxml file
     * @throws IOException in case that the fxml file doesn't exist
     */
    public static void switchScene(Stage stage, String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(loader.load(), 1000, 700);

        URL stylesheet = HelloApplication.class.getResource("css/main.css");

        if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
