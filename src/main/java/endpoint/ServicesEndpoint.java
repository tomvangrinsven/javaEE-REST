package endpoint;

import authorization.Authorization;
import dao.interfaces.IServicesDAO;
import domain.Service;
import responses.ServiceResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/abonnementen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicesEndpoint {

    @Inject
    private IServicesDAO dao;

    @Inject
    private Authorization auth;

    @Path("/all")
    @GET
    public Response getSubscriptionsFromUser(@QueryParam("token") String token, @QueryParam("filter") String filter){

        if (!auth.isAuthorized(token)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            ArrayList<ServiceResponse> serviceResponses = new ArrayList<>();

            for (Service service : dao.getAll()) {
                if (service.getService().contains(filter) || service.getProvider().contains(filter)) {
                    serviceResponses.add(new ServiceResponse(service.getId(), service.getProvider(), service.getService()));
                }
            }
            return Response.ok().entity(serviceResponses).build();
        }
    }
}
