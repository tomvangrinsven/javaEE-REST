package daoWithH2;

import daoWithH2.H2Construction.DBConnectionFactoryMock;
import daoWithH2.H2Construction.H2DatabaseProperties;
import daoWithH2.H2Construction.IConnectionFactory;
import nl.tomvangrinsven.vodagone.dao.IDatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.realisation.ServicesDAO;
import nl.tomvangrinsven.vodagone.dao.realisation.SubscriberDAO;
import nl.tomvangrinsven.vodagone.domain.Subscriber;
import nl.tomvangrinsven.vodagone.domain.User;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SubscriberDAOWithH2Test {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IConnectionFactory factory;

    private SubscriberDAO dao;

    private IDatabaseProperties prop;

    @Before
    public void setUp() throws Exception{
        factory = new DBConnectionFactoryMock();
        prop = new H2DatabaseProperties();
        dao = new SubscriberDAO(prop);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testDB_create.sql");


        RunScript.execute(factory.getConnection(), new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
    }

    @Test
    public void getAllSubscribers(){
        ArrayList<Subscriber> subs = dao.getAllSubscribers("token");
        assertTrue(subs != null);
        assertEquals("Meron", subs.get(0).getName());
        assertEquals("Meron.Brouwer@han.nl", subs.get(0).getEmailAddress());
        assertEquals("Dennis", subs.get(1).getName());
        assertEquals("Dennis.Breuker@han.nl", subs.get(1).getEmailAddress());
        assertEquals("Michel", subs.get(2).getName());
        assertEquals("Michel.Portier@han.nl", subs.get(2).getEmailAddress());
    }

    @Test
    public void testGetAll(){
        User user = dao.getUser("zegik@lekker.niet", "pwd");
        assertTrue(user != null);
        assertEquals("Tom van Grinsven", user.getSubscriberName());
    }
}
