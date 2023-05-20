/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pls_no_shinobu.videostore.model.Item;

import java.util.ArrayList;

public class TestFileIOUtils {
    @Test
    @DisplayName("Parse a file")
    void parseValidString() {
        ArrayList<Item> items = FileIOUtils.parseItem();

        assertNull(items.get(0).getGenre());
        assertTrue(items.get(1).getTitle().equals("White Castle"));
        assertEquals(items.get(2).getLoanType(), Item.LoanType.ONE_WEEK);
        assertEquals(items.get(3).getRentalType(), Item.RentalType.DVD);
        assertEquals(2, items.get(4).getStock());
        assertEquals(0.99f, items.get(5).getRentalFee());
    }
}
