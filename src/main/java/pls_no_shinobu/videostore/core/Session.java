package pls_no_shinobu.videostore.core;

import pls_no_shinobu.videostore.model.User;

public class Session {
    User currentUser;
    boolean authenticated;

    public Session(User user) {
        currentUser = user;
        authenticated = false;
    }

    public boolean login(String password) {
        if (currentUser.checkPassword(password)) {
            authenticated = true;
            return true;
        };

        // something went wrong
        return false;
    }

    public boolean logout() {
        authenticated = false;

        return true;
    }
}
