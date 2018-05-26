package dao;

import dao.interfaces.ISubscriptionDAO;
import domain.Service;
import domain.Subscription;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionDAO implements ISubscriptionDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private DatabaseProperties prop;

    @Inject
    public SubscriptionDAO(DatabaseProperties prop){
        this.prop = prop;
        loadJDBCDriver(prop);
    }

    private void loadJDBCDriver(DatabaseProperties prop) {
        try {
            Class.forName(prop.driver());
        } catch(ClassNotFoundException ex){
            logger.log(Level.SEVERE, "Can't load JDBC Driver");
        }
    }

    @Override
    public ArrayList<Subscription> getAllSubscriptions(String token) {
        ArrayList<Subscription> resultList = new ArrayList<>();
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement(" select s.id as subscriptionid, s.startdate, s.doubled, s.status, " +
                        "(select s.subscriber = (select t.id FROM token t where t.token = ?)) " +
                        "as owner, d.id as serviceId, d.PROVIDER, d.name, " +
                        "(SELECT p.PRICE FROM prices p where P.SERVICE = d.id AND p.length = 'maand') " +
                        "as pricepermonth, (select d.shareable - " +
                        "(select count(*) from sharedsubscription ss where ss.subscriptionid = s.id)) " +
                        "as shareable, d.DOUBLEABLE " +
                        "from subscription s inner join service d on d.id = s.SERVICE " +
                        "where s.subscriber = " +
                        "( select t.id from token t where t.token = ?) " +
                        "or s.id = (select t.id from token t where t.token = ?);");
        ){
            statement.setString(1, token);
            statement.setString(2, token);
            statement.setString(3, token);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                resultList.add(new Subscription(
                        result.getInt("subscriptionid"),
                        result.getString("startdate"),
                        result.getBoolean("doubled"),
                        result.getString("status"),
                        result.getBoolean("owner"),
                        new Service(
                                result.getInt("serviceId"),
                                result.getString("name"),
                                result.getString("provider"),
                                result.getDouble("PricePerMonth"),
                                result.getInt("shareable"),
                                result.getBoolean("doubleable")
                            )
                        ));
            }
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return resultList;
    }

    @Override
    public Subscription getById(String token, int id) {
        Subscription sub = null;
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("SELECT s.ID as subscriptionid, s.STARTDATE, s.DOUBLED, " +
                                "s.STATUS, (SELECT s.SUBSCRIBER = (SELECT t.ID FROM token t WHERE t.TOKEN = ?)) as owner, se.id as " +
                                "serviceId, se.provider, se.name, (SELECT p.price FROM prices p WHERE p.service = se.ID AND " +
                                "p.length = 'maand') as pricePerMonth, (SELECT se.shareable - (SELECT COUNT(*) FROM sharedsubscription ss WHERE " +
                                "ss.subscriptionid = s.ID)) as shareable, se.doubleable FROM subscription s INNER JOIN service se ON se.ID = " +
                                "s.service WHERE s.ID = ? AND (s.subscriber = (SELECT t.ID FROM token t WHERE t.TOKEN = ?) OR " +
                                "s.subscriber IN (SELECT s.subscriber FROM subscription s2 WHERE s2.ID IN (SELECT ss.subscriptionid " +
                                "FROM sharedsubscription ss WHERE ss.subscriberid = (SELECT t.ID FROM token t WHERE t.TOKEN = ?))))"
                )
        ){
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.setString(3, token);
            statement.setString(4, token);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                sub = new Subscription(
                        result.getInt("subscriptionId"),
                        result.getString("startdate"),
                        result.getBoolean("doubled"),
                        result.getString("status"),
                        result.getBoolean("owner"),
                        new Service(
                                result.getInt("serviceId"),
                                result.getString("name"),
                                result.getString("provider"),
                                result.getDouble("PricePerMonth"),
                                result.getInt("shareable"),
                                result.getBoolean("doubleable")
                        )
                );
            }
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return sub;
    }

    @Override
    public boolean addSubscription(String token, int id) {

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Subscription (Subscriber, status, service, length, startdate, doubled)" +
                        " VALUES ((SELECT t.ID from token t where t.TOKEN = ?), 'proef', ?, 'maand', NOW(), false)")
        ){
            statement.setString(1, token);
            statement.setInt(2, id);
            statement.execute();
            } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean doubleSubscription(String token, int id) {

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("UPDATE Subscription SET DOUBLED = true WHERE ID = ? AND SUBSCRIBER = (SELECT t.ID FROM token t where t.TOKEN = ?)")
        ){
            statement.setInt(1, id);
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean cancelSubscription(String token, int id) {
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("UPDATE Subscription SET STATUS = 'opgezegd' WHERE ID = ? AND Subscriber = (SELECT t.ID from token t where t.TOKEN = ?)")
        ){
            statement.setInt(1, id);
            statement.setString(2, token);
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void updateSubscriptions() {
        updateSubscriptionToActive();
        updateSubscriptionToCancelled();
    }

    private void updateSubscriptionToCancelled() {

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("UPDATE Subscription SET STATUS = 'opgezegd' " +
                        "WHERE ENDDATE IS NOT NULL AND STATUS != 'opgezegd' " +
                        "AND DATEDIFF(NOW(), ENDDATE) > 0 )");
        ){
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
    }

    private void updateSubscriptionToActive(){

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("UPDATE Subscription SET STATUS = 'actief' " +
                        "WHERE STATUS = 'proef' AND DATEDIFF(NOW(), STARTDATE) >= 31 )")
        ){
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
    }

}
