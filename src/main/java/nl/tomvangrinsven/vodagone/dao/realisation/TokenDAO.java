package nl.tomvangrinsven.vodagone.dao.realisation;

import nl.tomvangrinsven.vodagone.dao.*;

import javax.inject.Inject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO implements ITokenDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private IDatabaseProperties prop;

    @Inject
    public TokenDAO(IDatabaseProperties prop){
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
    public boolean insertOrupdateToken(String token, int subscriberId) {
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
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteExpiredTokens() {
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("DELETE FROM token WHERE TIMEDIFF(EXPIRES, NOW()) < 0")
        ){
            statement.execute();
        } catch (SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public int getUserIdByToken(String token) {
        int id = -1;
        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("SELECT t.ID FROM token t WHERE t.TOKEN = ?")
                ){
            statement.setString(1, token);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                id = result.getInt("ID");
            }

        } catch(SQLException exception){
            logger.log(Level.SEVERE, exception.getMessage());
        }
        return id;
    }
}
