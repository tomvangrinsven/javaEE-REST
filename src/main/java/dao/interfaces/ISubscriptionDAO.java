package dao.interfaces;

import domain.Subscription;

import java.util.ArrayList;

public interface ISubscriptionDAO {
    ArrayList<Subscription> getAllSubscriptions(String token);
    Subscription getById(String token, int id);
    void addSubscription(String token, int id);
    void doubleSubscription(String token, int id);
    void cancelSubscription(String token, int id);
    void updateSubscriptions();
}
