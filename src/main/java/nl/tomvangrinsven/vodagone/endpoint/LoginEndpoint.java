package nl.tomvangrinsven.vodagone.endpoint;

import nl.tomvangrinsven.vodagone.dao.ISubscriberDAO;
import nl.tomvangrinsven.vodagone.domain.User;
import nl.tomvangrinsven.vodagone.responses.AuthorizedUser;
import nl.tomvangrinsven.vodagone.responses.UserLogin;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginEndpoint {

    @Inject
    private ISubscriberDAO dao;

    @Path("login")
    @POST
    public Response login(UserLogin userLogin)
    {
        User user = dao.getUser(userLogin.getUser(), userLogin.getPassword());
        if (user == null)
        {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        return Response
                .ok()
                .entity(new AuthorizedUser(user))
                .build();
    }
}
