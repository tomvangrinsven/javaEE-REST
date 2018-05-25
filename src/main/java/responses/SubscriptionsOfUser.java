package responses;

import domain.Subscription;

import java.util.ArrayList;

public class SubscriptionsOfUser {

    private final ArrayList<SubscriptionOfUser> abonnementen;
    private double totalPrice;

    public SubscriptionsOfUser(ArrayList<Subscription> subscriptions) {

        this.abonnementen = new ArrayList<>();
        this.totalPrice = 0.0;

        for (Subscription sub :
                subscriptions) {
            if (sub.isOwner()){
                this.totalPrice += sub.getPrice();
            }
            this.abonnementen.add(new SubscriptionOfUser(sub));

        }
    }
}
