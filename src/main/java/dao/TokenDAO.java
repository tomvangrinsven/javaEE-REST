package dao;

import dao.interfaces.ITokenDAO;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO implements ITokenDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private DatabaseProperties prop;

    @Inject
    public TokenDAO(DatabaseProperties prop){
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
    public void insertOrupdateToken(String token, int subscriberId) {
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("INSERT INTO token VALUES(?, ?, NOW() +  " +
                        " INTERVAL 1 DAY) ON DUPLICATE KEY UPDATE token = ?, expires = NOW() + INTERVAL 1 DAY")
        ){
            statement.setString(1, token);
            statement.setInt(2, subscriberId);
            statement.setString(3, token);
            statement.execute();
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void deleteExpiredTokens() {
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("DELETE FROM token WHERE TIMEDIFF(EXPIRES, NOW()) < 0")
        ){
            statement.execute();
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
            System.out.println(exception.getSQLState());
            exception.printStackTrace();
        }

    }
}
