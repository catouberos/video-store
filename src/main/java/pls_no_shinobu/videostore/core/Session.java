package pls_no_shinobu.videostore.core;

import pls_no_shinobu.videostore.model.User;

public class Session {
    User currentUser;
    boolean authenticated;

    private Session() {
        authenticated = false;
    }

    public Session(User user) {
        super();

        currentUser = user;
    }

    public boolean login(String password) {
        if (currentUser.checkPassword(password)) authenticated = true;

        return authenticated;
    }

    public boolean logout() {
        authenticated = false;

        return authenticated;
    }
}
