/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.controller.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class PaneUtils {
    /**
     * Utility to conveniently set a defined {@link StackPane} to a new {@link Pane}
     *
     * @author Do Khoa Nguyen
     */
    public static void setPane(StackPane stackPane, Node content) {
        // hide every node in stackPane with fade out transition
        for (Node node : stackPane.getChildren()) {
            if (node == content) continue;

            if (node.isVisible()) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(500), node);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> node.setVisible(false));
                fadeOut.play();
            }
        }

        // then, shows only the specified one with fade in transition
        content.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), content);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
}
