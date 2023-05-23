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

public class TestUser {
    User user;

    @Test
    @DisplayName("Create an user with invalid ID format")
    void createNewInvalidIDUser() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> user = new User("this is an illegal ID", "user", "password"));

        String expectedMessage = "Invalid user ID format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create an user with blank username")
    void createNewBlankUsernameUser() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> user = new User("C001", "", "password"));

        String expectedMessage = "Username cannot be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create an user with space character username")
    void createNewSpaceUsernameUser() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> user = new User("C001", "   hi h ", "password"));

        String expectedMessage = "Username cannot special character";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create an user with invalid password")
    void createInvalidPasswordUser() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> user = new User("C001", "user", ""));

        String expectedMessage = "Password cannot be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // TODO: rental-related tests need a rewrite
    //    @Test
    //    @DisplayName("rentalCount behavior with addRental()")
    //    void rentalCountCheckWithAddRental() {
    //        user = new User("C001", "user", "password");
    //        Item item =
    //                new Item(
    //                        "I001-2001", "", "", Item.RentalType.RECORD, Item.LoanType.ONE_WEEK,
    // 10, 0);
    //
    //        try {
    //            user.addRental(item);
    //        } catch (OutOfStockException | RentLimitException e) {
    //            System.out.print("Item is out of stock: " + e);
    //        }
    //
    //        assertEquals(1, user.getRentalCount());
    //        assertTrue(user.getRentals().get(0).getItem().getId().contains(item.getId()));
    //    }
}
