package br.com.rafaelchagasb;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InfinispanClient {

    @Inject
    Logger log;

    RemoteCacheManager rmc;

    //you need inject properties
    private final String host = "localhost";
    private final int port = 11222;
    private final String user = "admin";
    private final String password = "password";

    @PostConstruct
    public void init() {
        try{
            this.rmc = new RemoteCacheManager(new ConfigurationBuilder()
                    .addServer()
                    .host(host)
                    .port(port)
                    .clientIntelligence(ClientIntelligence.BASIC)
                    .connectionTimeout(1000)
                    .socketTimeout(1000)
                    .security()
                    .authentication()
                    .username(user)
                    .password(password).build());
        } catch(Exception e) {
            log.error("Failure to connect infinispan", e);
        }
    }

    @PreDestroy
    public void destroy() {
        if(this.rmc != null) {
            this.rmc.close();
        }
    }

    public RemoteCacheManager getRmc() {
        return rmc;
    }
}
