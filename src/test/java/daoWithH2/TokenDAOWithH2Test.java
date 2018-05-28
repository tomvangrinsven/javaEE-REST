package daoWithH2;

import daoWithH2.H2Construction.DBConnectionFactoryMock;
import daoWithH2.H2Construction.H2DatabaseProperties;
import daoWithH2.H2Construction.IConnectionFactory;
import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.realisation.SubscriptionDAO;
import nl.tomvangrinsven.vodagone.dao.realisation.TokenDAO;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenDAOWithH2Test {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IConnectionFactory factory;

    private TokenDAO dao;

    private IDatabaseProperties prop;

    @Before
    public void setUp() throws Exception{
        factory = new DBConnectionFactoryMock();
        prop = new H2DatabaseProperties();
        dao = new TokenDAO(prop);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testDB_create.sql");


        RunScript.execute(factory.getConnection(), new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
    }

    @Test
    public void getUserIdByToken(){
       int id = dao.getUserIdByToken("token");
       assertEquals(1, id);
    }
}
