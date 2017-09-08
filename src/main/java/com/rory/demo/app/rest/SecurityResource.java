package com.rory.demo.app.rest;

import com.rory.demo.app.security.HTTPHeaderNames;
import com.rory.demo.app.security.RoryAuthenticator;
import org.jboss.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.GeneralSecurityException;

@ApplicationPath("/resources")
@Path("security")
public class SecurityResource extends Application {

    private static final Logger LOGGER = Logger.getLogger(SecurityResource.class);

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        RoryAuthenticator demoAuthenticator = RoryAuthenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString(HTTPHeaderNames.SERVICE_KEY);

        try {
            String authToken = demoAuthenticator.login(serviceKey, username, password);

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("auth_token", authToken);
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();

        } catch (final LoginException ex) {
            LOGGER.error(ex.getMessage(), ex);
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("message", "Problem matching service key, username and password");
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }

    @POST
    @Path("logout")
    public Response logout(
            @Context HttpHeaders httpHeaders) {
        try {
            RoryAuthenticator demoAuthenticator = RoryAuthenticator.getInstance();
            String serviceKey = httpHeaders.getHeaderString(HTTPHeaderNames.SERVICE_KEY);
            String authToken = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);

            demoAuthenticator.logout(serviceKey, authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
        } catch (final GeneralSecurityException ex) {
            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);

        return Response.status(status).cacheControl(cc);
    }
}
