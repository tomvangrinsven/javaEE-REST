import dao.DatabaseProperties;
import dao.TokenDAO;
import dao.interfaces.ITokenDAO;
import org.junit.Before;
import org.junit.Test;

public class test {


    private ITokenDAO dao;
    private DatabaseProperties prop;

    @Before
    public void setup(){
        prop = new DatabaseProperties();
        dao = new TokenDAO(prop);
    }

    @Test
    public void test(){

    }
}
