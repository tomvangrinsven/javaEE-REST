package nl.tomvangrinsven.vodagone.dao.realisation;

import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties implements IDatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        this.properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String driver() { return properties.getProperty("driver");}
    public String connectionString() { return properties.getProperty("connectionString");}
}
