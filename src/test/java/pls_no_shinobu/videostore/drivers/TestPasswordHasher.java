/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.drivers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPasswordHasher {
    PasswordHasher p = new PasswordHasher();

    @Test
    @DisplayName("Create a new password hash and authenticate with the same password")
    void createNewPassword() {
        String rawPassword = "this-is-my-password";
        String hashedPassword = p.hash(rawPassword);

        assertTrue(p.check(rawPassword, hashedPassword));
    }

    @Test
    @DisplayName("Create a new password hash and authenticate with the wrong password")
    void createNewPasswordFails() {
        String rawPassword = "this-is-my-password";
        String hashedPassword = p.hash(rawPassword);

        assertFalse(p.check("this-is-not-my-password", hashedPassword));
    }
}
