package com.br.microservice.geographic.service;

import com.br.microservice.geographic.data.*;
import com.br.microservice.geographic.exception.BreakForEachException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
public class LocalidadeService implements ILocalidadeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalidadeService.class);

    @Value("${uri.states}")
    private String _uriState;

    @Value("${uri.states}${uri.zone}")
    private String _uriZone;

    @Override
    @Cacheable(cacheNames = "states")
    @HystrixCommand(fallbackMethod = "defaultState", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    public List<State> findAllStates() {
        LOGGER.info("findAllStates processing...");
        List<State> list = getApi(_uriState, HttpMethod.POST, null, new ParameterizedTypeReference<List<State>>() {
        }, null);
        return list;
    }

    @Override
    @Cacheable(value = "zonesbystate", key = "#ufId")
    @HystrixCommand(fallbackMethod = "defaultZoneByState", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")})
    public List<Zone> findZonesByState(Integer ufId) {
        List<Zone> zones = getApi(_uriZone, HttpMethod.POST, null, new ParameterizedTypeReference<List<Zone>>() {
        }, ufId);
        return zones;
    }

    @Override
    @Cacheable("locales")
    public ResponseEntity<List<Locale>> findAllLocalidade() {
        LOGGER.info("findAllLocalidade init process...");
        List<Locale> locales = new ArrayList<>();

        List<State> states = findAllStates();
        states.stream().forEach(state -> {
            List<Zone> zones = findZonesByState(state.getId());
            zones.stream().forEach(zone -> {
                locales.add(buildLocale(zone, state));
            });
        });

        LOGGER.info("findAllLocalidade finish process...");

        return new ResponseEntity<>(locales, HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "localesbyname", key = "#name")
    public Locale findLocalidadeByName(String name) {
        LOGGER.info("findLocalidadeByName init process...");
        List<Locale> locale = new ArrayList<>();
        try {
            List<State> states = findAllStates();
            states.stream().forEach(state -> {
                List<Zone> zones = findZonesByState(state.getId());
                if (!zones.isEmpty()) {
                    Optional<Zone> zone = zones.stream().filter(obj -> obj.getName().equals(name)).findAny();
                    if (zone.isPresent()) {
                        locale.add(buildLocale(zone.get(), state));
                        throw new BreakForEachException("Locale found > " + zone.get().getName());
                    }
                }
            });
        } catch (BreakForEachException e) {
            LOGGER.info(e.getLocalizedMessage());
        }

        LOGGER.info("findLocalidadeByName finish process...");
        return locale.isEmpty() ? null : locale.get(0);
    }

    public <T> List<T> getApi(final String path, final HttpMethod method, final HttpEntity requestEntity,
                              final ParameterizedTypeReference typeRef,
                              final Object... uriVariables) {
        final RestTemplate restTemplate = new RestTemplate();

        if (uriVariables != null) {
            return ((List<T>) restTemplate.exchange(path, method, requestEntity, typeRef, uriVariables).getBody());
        } else {
            return ((List<T>) restTemplate.exchange(path, method, requestEntity, typeRef).getBody());
        }
    }

    private Locale buildLocale(Zone zone, State state) {
        return Locale.builder()
                .idEstado(String.valueOf(state.getId()))
                .siglaEstado(state.getInitial())
                .regiaoNome(state.getRegion().getName())
                .nomeCidade(zone.getName())
                .nomeMesorregiao(zone.getMicroRegion().getMesoRegion().getName())
                .nomeFormatado(zone.getName().concat("/").concat(state.getInitial()))
                .build();
    }

    public List<State> defaultState() {
        List<State> state = new ArrayList<>();
        state.add(State.builder()
                .id(35)
                .name("São Paulo")
                .initial("SP")
                .region(Region.builder()
                        .id(3)
                        .name("Sudeste")
                        .initial("SE")
                        .build())
                .build()
        );
        return state;
    }

    public List<Zone> defaultZoneByState(Integer ufId) {
        List<Zone> zone = new ArrayList<>();
        zone.add(Zone.builder()
                .id(3550308)
                .name("São Paulo")
                .microRegion(MicroRegion.builder()
                        .id(35061)
                        .name("São Paulo")
                        .mesoRegion(MesoRegion.builder()
                                .id(3515)
                                .name("Metropolitana de São Paulo")
                                .build())
                        .build())
                .build()
        );
        return zone;
    }
}
