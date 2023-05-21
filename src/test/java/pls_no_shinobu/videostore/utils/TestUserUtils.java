/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pls_no_shinobu.videostore.manager.ItemManager;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

public class TestUserUtils {
    UserUtils utils;
    ItemManager itemManager;

    @BeforeEach
    void setUp() {
        Item testItem = new Item("I006-2013", "test item");
        itemManager = new ItemManager();

        try {
            itemManager.add(testItem);
        } catch (Exception e) {
            // ignore
        }

        utils = new UserUtils();
    }

    @Test
    @DisplayName("Parse a valid string")
    void parseValidString() {
        final String validString =
                "C001,Minh Dinh,18 Irwin Street,0421473243,3,VIP, minhdinh, 123456\n";

        User testUser = utils.parse(validString, itemManager);

        assertEquals("C001", testUser.getId());
        assertEquals("Minh Dinh", testUser.getName());
        assertEquals("18 Irwin Street", testUser.getAddress());
        assertEquals("0421473243", testUser.getPhone());
        assertEquals(3, testUser.getRentalCount());
        assertEquals(User.UserType.VIP, testUser.getRole());
    }

    // TODO: more tests
}
