package endpoint;

import dao.interfaces.ISubscriptionDAO;
import responses.ServiceResponse;
import responses.SpecifiedSubscriptionOfUser;
import responses.SubscriptionOfUser;
import responses.SubscriptionsOfUser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/abonnementen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionEndpoint {

    @Inject
    private ISubscriptionDAO dao;

    @Path("")
    @GET
    public Response getSubscriptionsOfUser(@QueryParam("token") String token){
        return Response.ok().entity(getAllSubscriptions(token)).build();
    }

    @Path("")
    @POST
    public Response addSubscription(@QueryParam("token") String token, ServiceResponse service){
        dao.addSubscription(token, service.getId());
        return Response.ok().entity(getAllSubscriptions(token)).build();
    }

    @Path("/{id}")
    @GET
    public Response getSubscriptionsById(@PathParam("id") int id, @QueryParam("token") String token){
        return Response.ok().entity(getSpecificSubscription(id, token)).build();
    }

    @Path("/{id}")
    @POST
    public Response doubleSubscriptionById(@PathParam("id") int id, @QueryParam("token") String token){
        return Response.ok().entity(getSpecificSubscription(id, token)).build();
    }

    @Path("/{id}")
    @DELETE
    public Response cancelSubscriptionById(@PathParam("id") int id, @QueryParam("token") String token){
        dao.cancelSubscription(token, id);
        return Response.ok().entity(getSpecificSubscription(id, token)).build();
    }


    private SubscriptionsOfUser getAllSubscriptions(@QueryParam("token") String token){
        return new SubscriptionsOfUser(dao.getAllSubscriptions(token));


    }

    private SpecifiedSubscriptionOfUser getSpecificSubscription( int id, String token){
        return new SpecifiedSubscriptionOfUser(dao.getById(token, id));
    }
}
