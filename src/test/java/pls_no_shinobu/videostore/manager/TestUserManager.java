/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pls_no_shinobu.videostore.errors.DuplicateException;
import pls_no_shinobu.videostore.errors.NotFoundException;
import pls_no_shinobu.videostore.model.User;

import java.util.ArrayList;

public class TestUserManager {
    UserManager manager;

    @BeforeEach
    void setup() {
        manager = new UserManager();
    }

    @Test
    @DisplayName("Create a new user with duplicate username")
    void createNewDuplicateUserUsername() {
        User user1 = new User("C001", "username", "password");
        User user2 = new User("C002", "username", "password");

        DuplicateException exception =
                assertThrows(
                        DuplicateException.class,
                        () -> {
                            manager.add(user1);
                            manager.add(user2);
                        });

        String expectedMessage = "Cannot create user with taken username";
        String actualMessage = exception.getMessage();

        assertEquals(1, manager.getEntities().toArray().length);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create a new user with duplicate id")
    void createNewDuplicateUserID() {
        User user1 = new User("C001", "username", "password");
        User user2 = new User("C001", "username2", "password");

        DuplicateException exception =
                assertThrows(
                        DuplicateException.class,
                        () -> {
                            manager.add(user1);
                            manager.add(user2);
                        });

        String expectedMessage = "Cannot create with taken ID";
        String actualMessage = exception.getMessage();

        assertEquals(1, manager.getEntities().toArray().length);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Remove user from list using ID")
    void removeUserById() {
        User user1 = new User("C001", "username", "password");
        User user2 = new User("C002", "username2", "password");
        User user3 = new User("C003", "username3", "password");

        try {
            manager.add(user1);
            manager.add(user2);
            manager.add(user3);
            manager.remove(user1.getId());
        } catch (DuplicateException | NotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, manager.getEntities().toArray().length);
    }

    @Test
    @DisplayName("Remove a not-found user")
    void removeInvalidUser() {
        User user1 = new User("C001", "username", "password");
        User user2 = new User("C002", "username2", "password");

        NotFoundException exception =
                assertThrows(
                        NotFoundException.class,
                        () -> {
                            manager.add(user1);
                            manager.add(user2);

                            manager.remove("C003");
                        });

        String expectedMessage = "Entity not found";
        String actualMessage = exception.getMessage();

        assertEquals(2, manager.getEntities().toArray().length);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create a manager from ArrayList")
    void createManagerFromArrayList() {
        User user1 = new User("C001", "username", "password");
        User user2 = new User("C001", "username2", "password");
        ArrayList<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        manager = new UserManager(users);

        assertEquals(1, manager.getEntities().toArray().length);
    }

    @Test
    @DisplayName("Find an unused ID and create user")
    void findFreeIDAndCreateUser() {
        User admin = new User("C000", "admin", "password");
        User user1 = new User("C001", "username", "password");
        ArrayList<User> users = new ArrayList<>();

        users.add(admin);
        users.add(user1);

        manager = new UserManager(users);

        try {
            User user2 = new User(manager.getUnusedID(), "username2", "password");

            manager.add(user2);
        } catch (Exception e) {
            System.out.println("Something went wrong" + e.getMessage());
        }

        assertEquals(3, manager.getEntities().toArray().length);
    }
}
