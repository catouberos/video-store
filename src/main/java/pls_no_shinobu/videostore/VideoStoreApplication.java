/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class VideoStoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // create a database and session instance at first
        CSVDatabase.getInstance();
        Session.getInstance();

        FXMLLoader fxmlLoader =
                new FXMLLoader(VideoStoreApplication.class.getResource("signin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        URL stylesheet = VideoStoreApplication.class.getResource("css/main.css");
        if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());

        stage.setTitle("Shinobu Video Store");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);

        try {
            InputStream iconStream = VideoStoreApplication.class.getResourceAsStream("img/donut.png");
            if (iconStream != null) stage.getIcons().add(new Image(iconStream));
        } catch (NullPointerException e) {
            System.err.println("Cannot set icon, details: " + e.getMessage());
            e.printStackTrace();
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
