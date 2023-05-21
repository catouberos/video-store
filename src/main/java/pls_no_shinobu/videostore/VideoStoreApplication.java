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
import javafx.stage.Stage;

import pls_no_shinobu.videostore.core.CSVDatabase;
import pls_no_shinobu.videostore.core.Session;

import java.io.IOException;
import java.net.URL;

public class VideoStoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CSVDatabase database = CSVDatabase.getInstance();
        Session session = Session.getInstance();

        FXMLLoader fxmlLoader =
                new FXMLLoader(VideoStoreApplication.class.getResource("signin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        URL stylesheet = VideoStoreApplication.class.getResource("css/main.css");
        if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());

        stage.setTitle("Shinobu Video Store");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
