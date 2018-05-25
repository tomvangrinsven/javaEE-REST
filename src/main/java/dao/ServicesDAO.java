package dao;

import dao.interfaces.IServicesDAO;
import domain.Service;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicesDAO implements IServicesDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private DatabaseProperties prop;

    @Inject
    public ServicesDAO(DatabaseProperties prop){
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
    public ArrayList<Service> getAll() {
        ArrayList<Service> services = new ArrayList<>();

        try(
                Connection connection = DriverManager.getConnection(prop.connectionString());
                PreparedStatement statement = connection.prepareStatement("SELECT s.ID, s.PROVIDER, s.NAME, " +
                        "(SELECT p.PRICE FROM prices p WHERE p.SERVICE = s.ID AND p.LENGTH = 'maand') as pricePerMonth, " +
                        "s.SHAREABLE, s.DOUBLEABLE FROM SERVICE s")
        ){
            ResultSet result = statement.executeQuery();
            while(result.next()){
                services.add(new Service(
                        result.getInt("id"),
                        result.getString("provider"),
                        result.getString("name"),
                        result.getDouble("pricePerMonth"),
                        result.getInt("shareable"),
                        result.getBoolean("doubleable")
                ));
            }
        } catch (SQLException exception){
            throw new RuntimeException("error");
        }

        return services;
    }
}
