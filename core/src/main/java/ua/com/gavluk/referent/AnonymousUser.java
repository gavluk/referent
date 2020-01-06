package ua.com.gavluk.referent;

import ua.com.gavluk.referent.user.User;

class AnonymousUser extends User {

    static final String USERNAME = "anonymous";

    AnonymousUser() {
        super(USERNAME);
    }
}
