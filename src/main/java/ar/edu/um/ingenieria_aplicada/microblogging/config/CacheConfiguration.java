package ar.edu.um.ingenieria_aplicada.microblogging.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario.class.getName() + ".publicas", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario.class.getName() + ".likeas", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario.class.getName() + ".sigues", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Usuario.class.getName() + ".bloqueas", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Tag.class.getName() + ".tageas", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion.class.getName(), jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion.class.getName() + ".mencionas", jcacheConfiguration);
            cm.createCache(ar.edu.um.ingenieria_aplicada.microblogging.domain.Publicacion.class.getName() + ".esFavs", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
