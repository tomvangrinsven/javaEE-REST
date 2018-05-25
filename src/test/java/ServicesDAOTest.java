import dao.DatabaseProperties;
import dao.ServicesDAO;
import dao.interfaces.IServicesDAO;
import domain.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ServicesDAOTest {


    private IServicesDAO dao;
    private DatabaseProperties props;
    ArrayList<Service> testList;

    @Before
    public void setUp(){
        props = new DatabaseProperties();
        dao = new ServicesDAO(props);
        testList = new ArrayList<>();
    }

    @Test
    public void getAllServices(){
        testList = dao.getAll();
        Assert.assertTrue(testList.size() > 0);
    }

}
