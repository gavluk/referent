package ua.com.gavluk.referent;

public class AnonymousUser extends User {

    public static final String USERNAME = "ANONYMOUS";

    AnonymousUser() {
        super(USERNAME);
    }
}
