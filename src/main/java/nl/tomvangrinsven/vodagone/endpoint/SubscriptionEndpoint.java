package nl.tomvangrinsven.vodagone.endpoint;

import nl.tomvangrinsven.vodagone.authorization.Authorization;
import nl.tomvangrinsven.vodagone.dao.ISubscriptionDAO;
import nl.tomvangrinsven.vodagone.responses.ServiceResponse;
import nl.tomvangrinsven.vodagone.responses.SpecifiedSubscriptionOfUser;
import nl.tomvangrinsven.vodagone.responses.SubscriptionsOfUser;

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

    @Inject
    private Authorization auth;

    @Path("")
    @GET
    public Response getSubscriptionsOfUser(@QueryParam("token") String token){
        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok().entity(getAllSubscriptions(token)).build();
        }
    }

    @Path("")
    @POST
    public Response addSubscription(@QueryParam("token") String token, ServiceResponse service){
        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            dao.addSubscription(token, service.getId());
            return Response.ok().entity(getAllSubscriptions(token)).build();
        }
    }

    @Path("/{id}")
    @GET
    public Response getSubscriptionsById(@PathParam("id") int id, @QueryParam("token") String token){
        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok().entity(getSpecificSubscription(id, token)).build();
            }
        }

    @Path("/{id}")
    @POST
    public Response doubleSubscriptionById(@PathParam("id") int id, @QueryParam("token") String token) {
        if (!auth.isAuthorized(token)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok().entity(getSpecificSubscription(id, token)).build();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response cancelSubscriptionById(@PathParam("id") int id, @QueryParam("token") String token) {
        if (!auth.isAuthorized(token)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            dao.cancelSubscription(token, id);
            return Response.ok().entity(getSpecificSubscription(id, token)).build();
        }
    }


    private SubscriptionsOfUser getAllSubscriptions(@QueryParam("token") String token){
        return new SubscriptionsOfUser(dao.getAllSubscriptions(token));


    }

    private SpecifiedSubscriptionOfUser getSpecificSubscription( int id, String token){
        return new SpecifiedSubscriptionOfUser(dao.getById(token, id));
    }
}
