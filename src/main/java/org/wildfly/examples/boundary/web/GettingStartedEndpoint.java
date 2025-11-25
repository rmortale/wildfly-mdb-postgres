package org.wildfly.examples.boundary.web;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.wildfly.examples.controller.GettingStartedService;
import org.wildfly.examples.entity.repository.PersonRepository;

@Path("/")
public class GettingStartedEndpoint {

    @EJB
    PersonRepository personRepository;

    @Inject
    private GettingStartedService service;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(final @PathParam("name") String name) {
        String response = service.hello(name);

        personRepository.createPerson(name, name);

        return Response.ok(response).build();
    }
}
