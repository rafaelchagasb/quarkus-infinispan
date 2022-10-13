package br.com.rafaelchagasb;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

@Path("/hello")
public class GreetingResource {

    @Inject
    CacheService service;

    @Path("{name}")
    @GET
    public String getValue(@PathParam("name") String name) {

        Optional<String> itemCache = service.get(name);

        if(!itemCache.isPresent()) {
            service.put(name, name, 60l, SECONDS);
        }

        return  name;
    }
}
