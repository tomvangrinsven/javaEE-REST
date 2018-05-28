package nl.tomvangrinsven.vodagone.dao.realisation;


import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.IServicesDAO;
import nl.tomvangrinsven.vodagone.domain.Service;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicesDAO implements IServicesDAO {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private IDatabaseProperties prop;

    @Inject
    public ServicesDAO(IDatabaseProperties prop){
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
            logger.log(Level.SEVERE, exception.getMessage());
        }

        return services;
    }
}
