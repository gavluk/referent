package ua.com.gavluk.referent;

import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.referent.user.User;

public class ServiceOnBehalf {

    private final EnvironmentContext context;
    private final User owner;

    protected ServiceOnBehalf(User owner, EnvironmentContext context) {
        this.owner = owner;
        this.context = context;
    }

    public User getOwner() {
        return owner;
    }

    public EnvironmentContext getContext() {
        return context;
    }
}
