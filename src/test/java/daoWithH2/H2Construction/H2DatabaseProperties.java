package daoWithH2.H2Construction;

import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;

import java.io.IOException;
import java.util.Properties;

public class H2DatabaseProperties implements IDatabaseProperties {
    private Properties properties;

    public H2DatabaseProperties() {
        this.properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("h2database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String driver() { return properties.getProperty("driver");}
    public String connectionString() { return properties.getProperty("connectionString");}
}
