package endpoints;

import authorization.Authorization;
import dao.interfaces.IServicesDAO;
import domain.Service;
import endpoint.ServicesEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServicesEndpointTest {

    @Mock
    private IServicesDAO dao;

    @Mock
    private Authorization auth;

    @InjectMocks
    private ServicesEndpoint sut;

    private Response response;

    @Test
    public void testResponseForGetAllServices(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        response = sut.getSubscriptionsFromUser("correct", "");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUnauthorizedResponseForGetAllServices(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.getSubscriptionsFromUser("false", "");
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
