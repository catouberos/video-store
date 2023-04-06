/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class TestItem {
    Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
    }

    @Test
    @DisplayName("Create an item with invalid ID format should throw an error")
    void createNewInvalidItem() {
        HashSet<String> genres = new HashSet<>();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                item =
                                        new Item(
                                                "this is an illegal ID",
                                                "",
                                                genres,
                                                Item.RentalType.RECORD,
                                                Item.LoanType.ONE_WEEK,
                                                0,
                                                0,
                                                Item.RentalStatus.AVAILABLE));
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Update an item stock with negative value")
    void updateNegativeStock() {
        assertFalse(item.setStock(-1));
        assertEquals(0, item.getStock());
    }

    @Test
    @DisplayName("Add a duplicate genre")
    void addDuplicateGenre() {
        item.addGenre("test");

        assertFalse(item.addGenre("test"));
        assertEquals(item.getGenres().toString(), "[test]");
    }

    @Test
    @DisplayName("Remove a not-in-array genre")
    void removeInvalidGenre() {
        assertFalse(item.removeGenre("mystery item"));
        assertEquals(item.getGenres().toString(), "[]");
    }
}
