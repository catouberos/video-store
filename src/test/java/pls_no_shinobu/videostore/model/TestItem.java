/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestItem {
    Item item;

    @Test
    @DisplayName("Create an item with invalid ID format")
    void createNewInvalidItem() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                item =
                                        new Item(
                                                "this is an illegal ID",
                                                "",
                                                "",
                                                Item.RentalType.RECORD,
                                                Item.LoanType.ONE_WEEK,
                                                0,
                                                0));

        String expectedMessage = "Invalid item ID format";
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    @DisplayName("Update an item stock with negative value")
    void updateNegativeStock() {
        item = new Item("I001-20", "Medal of Honour");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> item.setStock(-1));
        System.out.print(exception.getMessage());

        String expectedMessage = "Stock cannot be negative";
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.contains(actualMessage));
    }
}
