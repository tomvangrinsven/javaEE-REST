package dao.interfaces;

import domain.Subscription;

import java.util.ArrayList;

public interface ISubscriptionDAO {
    ArrayList<Subscription> getAllSubscriptions(String token);
    Subscription getById(String token, int id);
    boolean addSubscription(String token, int id);
    boolean doubleSubscription(String token, int id);
    boolean cancelSubscription(String token, int id);
    void updateSubscriptions();
}
