package daoWithH2.H2Construction;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactory {

    Connection getConnection() throws SQLException;
}
