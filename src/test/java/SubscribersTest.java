

import dao.DatabaseProperties;
import dao.SubscriberDAO;
import dao.interfaces.ISubscriberDAO;
import domain.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SubscribersTest {
    private ISubscriberDAO dao;
    private DatabaseProperties props;
    ArrayList<Subscriber> testList;

    @Before
    public void setUp(){
        props = new DatabaseProperties();
        dao = new SubscriberDAO(props);
        testList = new ArrayList<>();
    }

    @Test
    public void getAllServices(){
        testList = dao.getAllSubscribers("9023-3479-0882-8130");
        Assert.assertTrue(testList.size() > 0);
        System.out.println(testList.get(0));
    }
}
