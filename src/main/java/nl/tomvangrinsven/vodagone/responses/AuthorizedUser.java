package nl.tomvangrinsven.vodagone.responses;

import nl.tomvangrinsven.vodagone.domain.User;

public class AuthorizedUser {
    private final String user;
    private final String token;

    public AuthorizedUser(User user) {
        this.token = user.getToken();
        this.user = user.getSubscriberName();
    }
}
