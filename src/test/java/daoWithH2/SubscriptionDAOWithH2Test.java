package daoWithH2;

import daoWithH2.H2Construction.DBConnectionFactoryMock;
import daoWithH2.H2Construction.H2DatabaseProperties;
import daoWithH2.H2Construction.IConnectionFactory;
import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.realisation.ServicesDAO;
import nl.tomvangrinsven.vodagone.dao.realisation.SubscriptionDAO;
import nl.tomvangrinsven.vodagone.domain.Service;
import nl.tomvangrinsven.vodagone.domain.Subscription;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubscriptionDAOWithH2Test {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IConnectionFactory factory;

    private SubscriptionDAO dao;

    private IDatabaseProperties prop;

    @Before
    public void setUp() throws Exception{
        factory = new DBConnectionFactoryMock();
        prop = new H2DatabaseProperties();
        dao = new SubscriptionDAO(prop);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testDB_create.sql");


        RunScript.execute(factory.getConnection(), new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
    }


    @Test
    public void getById(){
        Subscription sub = dao.getById("token", 1);
        assertTrue(sub != null);
        assertEquals("H2 telefonie", sub.getServiceName());
        assertEquals("h2 is awesome", sub.getProvider());
    }

    @Test
    public void getAllByToken(){
        ArrayList<Subscription> subs = dao.getAllSubscriptions("token");
        assertTrue(subs != null);
        assertEquals(3, subs.size());
    }

    @Test
    public void doubleSubscription(){
        assertTrue(dao.doubleSubscription("token", 1));
        assertTrue(dao.doubleSubscription("token", 2));
    }


}
