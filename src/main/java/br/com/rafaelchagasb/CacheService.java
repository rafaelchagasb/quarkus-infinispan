package br.com.rafaelchagasb;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequestScoped
public class CacheService {

    private final static String NAME_KEY_CACHE = "my-cache";

    @Inject
    Logger log;

    @Inject
    InfinispanClient client;

    RemoteCache<String, String> cache;

    @PostConstruct
    public void init() {
        this.cache = build();
    }

    private RemoteCache<String, String> build() {
        try{
            RemoteCacheManager rmc = client.getRmc();
            rmc.start();
            return rmc.getCache(NAME_KEY_CACHE);
        } catch(Exception e) {
            log.error("Failure to get remote instance cache", e);
            return null;
        }
    }

    public Optional<String> get(String name) {
        if(cache != null) {
            return Optional.ofNullable(cache.get(name));
        }
        return Optional.empty();
    }

    public void put(String key, String content, long time, TimeUnit unit) {
        if(cache != null) {
            cache.put(key, content, time, unit);
        }
    }
}
