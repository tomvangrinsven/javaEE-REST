package endpoint;

import dao.interfaces.ISubscriberDAO;
import domain.User;
import responses.AuthorizedUser;
import responses.UserLogin;

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
                    .status(401)
                    .build();
        }

        return Response
                .ok()
                .entity(new AuthorizedUser(user))
                .build();
    }
}
