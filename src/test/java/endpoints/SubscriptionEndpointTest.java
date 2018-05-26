package endpoints;

import authorization.Authorization;
import dao.interfaces.ISubscriptionDAO;
import domain.Subscription;
import endpoint.SubscribersEndpoint;
import endpoint.SubscriptionEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import responses.ServiceResponse;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SubscriptionEndpointTest {

    @Mock
    private ISubscriptionDAO dao;

    @Mock
    private Authorization auth;

    @InjectMocks
    private SubscriptionEndpoint sut;

    Response response;

    @Test
    public void testResponseForSubscriptionsOfUser(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        response = sut.getSubscriptionsOfUser("correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForSubscriptionsOfUser(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.getSubscriptionsOfUser("false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testResponseForAddSubscription(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        response = sut.addSubscription("correct", new ServiceResponse());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForAddSubscription(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.addSubscription("false", new ServiceResponse());
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testResponseForGetSubscriptionById(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        when(dao.getById(anyString(), anyInt())).thenReturn(mock(Subscription.class));
        response = sut.getSubscriptionsById(0, "correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForGetSubscriptionById(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.getSubscriptionsById(0, "false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testResponseForDoubleSubscription(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        when(dao.doubleSubscription("correct", 1)).thenReturn(true);
        when(dao.getById(anyString(), anyInt())).thenReturn(mock(Subscription.class));
        response = sut.doubleSubscriptionById(0, "correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForDoubleSubscription(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.doubleSubscriptionById(0, "false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void testResponseForCancelSubscription(){
        when(auth.isAuthorized("correct")).thenReturn(true);
        when(dao.cancelSubscription("correct", 1)).thenReturn(true);
        when(dao.getById(anyString(), anyInt())).thenReturn(mock(Subscription.class));
        response = sut.cancelSubscriptionById(0, "correct");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testForbiddenResponseForCancelSubscription(){
        when(auth.isAuthorized("false")).thenReturn(false);
        response = sut.cancelSubscriptionById(0, "false");
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

}
