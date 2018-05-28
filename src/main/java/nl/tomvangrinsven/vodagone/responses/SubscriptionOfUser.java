package nl.tomvangrinsven.vodagone.responses;

import nl.tomvangrinsven.vodagone.domain.Subscription;

public class SubscriptionOfUser {

    private final int id;
    private final String dienst;
    private final String aanbieder;

    public SubscriptionOfUser(Subscription subscription) {
        this.id = subscription.getId();
        this.dienst = subscription.getService().getService();
        this.aanbieder = subscription.getService().getProvider();
    }
}
