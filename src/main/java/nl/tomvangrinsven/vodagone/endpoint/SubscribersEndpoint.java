package nl.tomvangrinsven.vodagone.endpoint;

import nl.tomvangrinsven.vodagone.authorization.Authorization;
import nl.tomvangrinsven.vodagone.dao.ISubscriberDAO;
import nl.tomvangrinsven.vodagone.responses.SubscriptionId;

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

    @Inject
    private Authorization auth;

    @Path("")
    @GET
    public Response getOtherSubscribers(@QueryParam("token") String token){
        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        } else{
            return Response.ok().entity(dao.getAllSubscribers(token)).build();
        }
    }

    @Path("/{id}")
    @POST
    public Response shareSubscriptionById(@PathParam("id") int id, SubscriptionId subId, @QueryParam("token") String token){
        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            dao.shareSubscription(token, id, subId.getId());
            return Response.ok().build();
        }
    }
}
