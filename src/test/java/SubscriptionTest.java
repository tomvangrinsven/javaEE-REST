import dao.DatabaseProperties;
import dao.SubscriberDAO;
import dao.SubscriptionDAO;
import dao.interfaces.ISubscriberDAO;
import dao.interfaces.ISubscriptionDAO;
import domain.Subscriber;
import domain.Subscription;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SubscriptionTest {

    private ISubscriptionDAO dao;
    private DatabaseProperties props;
    ArrayList<Subscription> testList;
    Subscription sub;

    @Before
    public void setUp(){
        props = new DatabaseProperties();
        dao = new SubscriptionDAO(props);
        testList = new ArrayList<>();
    }

    @Test
    public void getAllServices(){
        testList = dao.getAllSubscriptions("9023-3479-0882-8130");
        Assert.assertTrue(testList.size() > 0);
        System.out.println(testList.get(0));
    }

    @Test
    public void getServiceById(){
        sub = dao.getById("9446-2680-8519-8872", 1);
        Assert.assertTrue(sub != null);
        Assert.assertEquals("vodafone", sub.getProvider());
        System.out.println(sub.getService().getService());
        System.out.println(sub.getProvider());
        System.out.println(sub.getPrice());
    }
}
