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

import pls_no_shinobu.videostore.manager.ItemManager;
import pls_no_shinobu.videostore.model.Item;
import pls_no_shinobu.videostore.model.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TestFileIOUtils {
    FileIOUtils utils;

    @BeforeEach
    void setup() {
        utils = new FileIOUtils();
    }

    @Test
    @DisplayName("Parse an items file")
    void parseItemsFile() throws NullPointerException, FileNotFoundException {
        ArrayList<Item> items = utils.parseItem();

        assertNull(items.get(0).getGenre());
        assertTrue(items.get(1).getTitle().equals("White Castle"));
        assertEquals(items.get(2).getLoanType(), Item.LoanType.ONE_WEEK);
        assertEquals(items.get(3).getRentalType(), Item.RentalType.DVD);
        assertEquals(2, items.get(4).getStock());
        assertEquals(0.99f, items.get(5).getRentalFee());
    }

    @Test
    @DisplayName("Parse and serialize an items file")
    void parseAndSerializeItemsFile() throws NullPointerException, FileNotFoundException {
        ArrayList<Item> items = utils.parseItem();

        utils.serializeItems(items);
    }

    @Test
    @DisplayName("Parse and serialize an users file")
    void parseAndSerializeUsersFile() throws NullPointerException, FileNotFoundException {
        ArrayList<Item> items = utils.parseItem();
        ItemManager itemManager = new ItemManager(items);

        ArrayList<User> users = utils.parseUser(itemManager);

        utils.serializeUsers(users);
    }

    @Test
    @DisplayName("Parse an users file")
    void parseUsersFile() throws NullPointerException, FileNotFoundException {
        ArrayList<Item> items = utils.parseItem();
        ItemManager itemManager = new ItemManager(items);

        ArrayList<User> users = utils.parseUser(itemManager);

        assertEquals("C005", users.get(5).getId());
        assertEquals("Dylan Case", users.get(6).getName());
        assertEquals("I006-2013", users.get(6).getRentals().get(0).getId());

        // TODO: apply more asserts
    }
}
