/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller.utils;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PaneUtils {
    /**
     * Utility to conveniently set a defined {@link StackPane} to a new {@link Pane}
     *
     * @author Do Khoa Nguyen
     */
    public static void setPane(StackPane stackPane, Node content) {
        // hide every node in stackPane
        for (Node node : stackPane.getChildren()) node.setVisible(false);

        // then, shows only the specified one
        content.setVisible(true);
    }
}
