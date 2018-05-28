package endpoints;

import nl.tomvangrinsven.vodagone.dao.ISubscriberDAO;
import nl.tomvangrinsven.vodagone.domain.Subscriber;
import nl.tomvangrinsven.vodagone.domain.User;
import nl.tomvangrinsven.vodagone.endpoint.LoginEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import nl.tomvangrinsven.vodagone.responses.AuthorizedUser;
import nl.tomvangrinsven.vodagone.responses.UserLogin;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginEndpointTest {

    @Mock
    private ISubscriberDAO dao;

    @Mock
    private UserLogin userLogin;

    @Mock
    private User user;

    @InjectMocks
    private LoginEndpoint sut;

    private Response response;

    @Test
    public void testIncorrectLogin(){
        response = sut.login(new UserLogin());
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCorrectLogin(){
        when(userLogin.getPassword()).thenReturn("supersafepassword");
        when(userLogin.getUser()).thenReturn("user");
        when(dao.getUser(anyString(), anyString())).thenReturn(user);
        response = sut.login(userLogin);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testReturnUserWithToken(){
        Subscriber sub = new Subscriber(2, "Dennis", "d.breuker@han.nl");
        User user = new User("token", sub);
        when(userLogin.getPassword()).thenReturn("");
        when(userLogin.getUser()).thenReturn("");
        when(dao.getUser(anyString(), anyString())).thenReturn(user);
        response = sut.login(userLogin);
        assertEquals(AuthorizedUser.class, response.getEntity().getClass());
    }
}
