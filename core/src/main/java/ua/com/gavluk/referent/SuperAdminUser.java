package ua.com.gavluk.referent;

import ua.com.gavluk.referent.user.User;

public class SuperAdminUser extends User {

    static final String USERNAME = "root";

    SuperAdminUser() {
        super(USERNAME);
    }
}
