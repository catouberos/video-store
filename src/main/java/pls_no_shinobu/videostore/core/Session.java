/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.core;

import pls_no_shinobu.videostore.errors.IncorrectLoginInfo;
import pls_no_shinobu.videostore.model.User;

import java.io.FileNotFoundException;

public final class Session {
    private static Session INSTANCE;

    User currentUser;
    boolean authenticated;

    public Session() {
        currentUser = null;
        authenticated = false;
    }

    public static Session getInstance() throws NullPointerException, FileNotFoundException {
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }

        return INSTANCE;
    }

    public void login(User user, String password) throws IncorrectLoginInfo {
        if (currentUser == null && user.checkPassword(password)) {
            currentUser = user;
            authenticated = true;

            return;
        }

        throw new IncorrectLoginInfo("Incorrect login info");
    }

    public void logout() {
        currentUser = null;
        authenticated = false;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
