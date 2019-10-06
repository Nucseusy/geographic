package com.br.microservice.geographic.service;

import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.data.Region;
import com.br.microservice.geographic.data.State;
import com.br.microservice.geographic.data.Zone;
import com.br.microservice.geographic.exception.BreakForEachException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    @HystrixCommand(fallbackMethod = "defaultState")
    public List<State> findAllStates() {
        LOGGER.info("findAllStates processing...");
        List<State> list = getApi(_uriState, HttpMethod.POST, null, new ParameterizedTypeReference<List<State>>() {
        }, null);
        return list;
    }

    @Override
    @Cacheable(value = "zonesbystate", key = "#ufId")
    public List<Zone> findZonesByState(Integer ufId) {
        List<Zone> zones = getApi(_uriZone, HttpMethod.POST, null, new ParameterizedTypeReference<List<Zone>>() {
        }, ufId);
        return zones;
    }

    @Override
    @Cacheable("locales")
    public List<Locale> findAllLocalidade() {
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

        return locales;
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
                Optional<Zone> zone = zones.stream().filter(obj -> obj.getNome().equals(name)).findAny();
                if (zone.isPresent()) {
                    locale.add(buildLocale(zone.get(), state));
                    throw new BreakForEachException("Locale found > " + zone.get().getNome());
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
                .idEstado(state.getId())
                .siglaEstado(state.getSigla())
                .regiaoNome(state.getRegiao().getNome())
                .nomeCidade(zone.getNome())
                .nomeMesorregiao(zone.getMicrorregiao().getMesorregiao().getNome())
                .nomeFormatado(zone.getNome().concat("/").concat(state.getSigla()))
                .build();
    }

    public List<State> defaultState() {
        List<State> state = new ArrayList<>();
        state.add(State.builder()
                .id(35)
                .nome("São Paulo")
                .sigla("SP")
               .regiao(Region.builder()
                       .id(3)
                       .nome("Sudeste")
                       .sigla("SE")
                       .build())
                .build()
        );
        return state;
    }
}
