/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller.utils;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PaneUtils {
    /**
     * Utility to conveniently set a defined {@link StackPane} to a new {@link Pane}
     *
     * @author Do Khoa Nguyen
     */
    public static void setPane(StackPane stackPane, Pane pane) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(pane);
    }
}
