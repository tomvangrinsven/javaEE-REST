package daoWithH2.H2Construction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactoryMock implements IConnectionFactory {
    @Override
    public Connection getConnection() throws SQLException {
        try{
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "");
    }
}
