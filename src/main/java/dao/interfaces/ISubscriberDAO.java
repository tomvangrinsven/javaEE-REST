package dao.interfaces;

import domain.Subscriber;
import domain.User;

import java.util.ArrayList;

public interface ISubscriberDAO {
    User getUser(String username, String password);
    ArrayList<Subscriber> getAllSubscribers(String token);
    boolean shareSubscription(String token, int subscriberId, int subscriptionId);
}
