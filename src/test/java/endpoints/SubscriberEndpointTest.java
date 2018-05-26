package endpoints;

import authorization.Authorization;
import dao.interfaces.ISubscriberDAO;
import domain.Subscriber;
import endpoint.SubscribersEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import responses.SubscriptionId;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberEndpointTest {

    @InjectMocks
    private SubscribersEndpoint sut;

    @Mock
    private ISubscriberDAO dao;

    @Mock
    private Authorization authorization;

    @Mock
    private SubscriptionId id;

    Response response;

    @Test
    public void testResponseForGetAllOtherSubscribers(){
        when(authorization.isAuthorized("correct")).thenReturn(true);
        response = sut.getOtherSubscribers("correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForGetAllOtherSubscribers(){
        when(authorization.isAuthorized("false")).thenReturn(false);
        response = sut.getOtherSubscribers("false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testResponseForShareSubscription(){
        when(id.getId()).thenReturn(0);
        when(authorization.isAuthorized("correct")).thenReturn(true);
        response = sut.shareSubscriptionById(0, id, "correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenREsponseForShareSubscription(){
        when(authorization.isAuthorized("false")).thenReturn(false);
        response = sut.shareSubscriptionById(0, id, "false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
}
