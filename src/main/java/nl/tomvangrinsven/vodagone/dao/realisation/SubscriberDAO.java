package nl.tomvangrinsven.vodagone.dao.realisation;

import nl.tomvangrinsven.vodagone.dao.*;
import nl.tomvangrinsven.vodagone.domain.*;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriberDAO implements ISubscriberDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private IDatabaseProperties prop;

    @Inject
    public SubscriberDAO(IDatabaseProperties prop){
        this.prop = prop;
        loadJDBCDriver(prop);
    }

    private void loadJDBCDriver(IDatabaseProperties prop) {
        try {
            Class.forName(prop.driver());
        } catch(ClassNotFoundException ex){
            logger.log(Level.SEVERE, "Can't load JDBC Driver");
        }
    }

    @Override
    public User getUser(String username, String password) {
        User user = null;
        Subscriber sub = null;

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("SELECT ID, NAME, EMAIL, " +
                        "(SELECT t.TOKEN FROM token t WHERE t.ID = s.ID) as token " +
                        "FROM subscriber s " +
                        "WHERE ID = (SELECT ID FROM user WHERE PASSWORD = ? AND USERNAME = ?)");

        ){
            statement.setString(1, password);
            statement.setString(2, username);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                sub = new Subscriber(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email")
                );
                String token = result.getString("token");
                user = new User(token, sub);
            }
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return user;
    }

    @Override
    public ArrayList<Subscriber> getAllSubscribers(String token) {
        ArrayList<Subscriber> subs = new ArrayList<>();

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM SUBSCRIBER WHERE ID != (SELECT t.ID FROM token t WHERE t.TOKEN = ?)")

        ){
            statement.setString(1, token);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                subs.add(new Subscriber(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email")
                ));
            }
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return subs;
    }

    @Override
    public boolean shareSubscription(String token, int subscriberId, int subscriptionId) {

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("INSERT INTO sharedsubscription (subscriberid, subscriptionid)" +
                        " SELECT s.ID, ? FROM subscription s WHERE s.ID = ? AND s.SUBSCRIBER = (SELECT t.ID FROM token t where t.TOKEN = ?)")
        ){
            statement.setInt(1, subscriptionId);
            statement.setInt(2, subscriberId);
            statement.setString(3, token);
            System.out.println(subscriberId);
            System.out.println(subscriptionId);
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return true;
    }
}
