package daoWithH2;

import daoWithH2.H2Construction.DBConnectionFactoryMock;
import daoWithH2.H2Construction.H2DatabaseProperties;
import daoWithH2.H2Construction.IConnectionFactory;
import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.realisation.ServicesDAO;
import nl.tomvangrinsven.vodagone.domain.Service;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServicesDAOWithH2Test {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IConnectionFactory factory;

    private ServicesDAO dao;

    private IDatabaseProperties prop;

    @Before
    public void setUp() throws Exception{
        factory = new DBConnectionFactoryMock();
        prop = new H2DatabaseProperties();
        dao = new ServicesDAO(prop);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testDB_create.sql");


        RunScript.execute(factory.getConnection(), new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
    }


    @Test
    public void test(){
            List<Service> services = dao.getAll();
            assertTrue(services != null);
            assertEquals("H2 telefonie", services.get(0).getProvider());
            assertEquals("h2 is awesome", services.get(0).getService());
            assertEquals("In-memory", services.get(1).getProvider());
            assertEquals("h2 is awesome", services.get(1).getService());

    }
}
