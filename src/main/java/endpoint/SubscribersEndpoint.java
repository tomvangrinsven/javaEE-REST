package endpoint;

import dao.interfaces.ISubscriberDAO;
import responses.SubscriptionId;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/abonnees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscribersEndpoint {
    @Inject
    private ISubscriberDAO dao;

    @Path("")
    @GET
    public Response getOtherSubscribers(@QueryParam("token") String token){
        return Response.ok().entity(dao.getAllSubscribers(token)).build();
    }

    @Path("/{id}")
    @POST
    public Response shareSubscriptionById(@PathParam("id") int id, SubscriptionId subId, @QueryParam("token") String token){
        dao.shareSubscription(token, id, subId.getId());
        return Response.ok().build();
    }
}
