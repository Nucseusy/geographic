package com.br.microservice.geographic.service;

import com.br.microservice.geographic.TesteUtil;
import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.data.State;
import com.br.microservice.geographic.data.Zone;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LocalidadeServiceTest {
    @InjectMocks
    LocalidadeService localidadeService;

    @Before
    public void setUp() {
        localidadeService._uriState = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
        localidadeService._uriZone = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios";
    }

    @Test
    public void givenValid_whenFindAllState_thenReturnList() {
        List<State> listState = localidadeService.findAllStates();
        Assert.assertNotEquals(listState, IsEmptyCollection.empty());
    }

    @Test
    public void givenValid_whenFindZonesByState_thenReturnList() {
        Integer VALID = 33;
        List<Zone> listZones = localidadeService.findZonesByState(VALID);

        Assert.assertNotEquals(listZones, IsEmptyCollection.empty());
    }

    @Test
    public void givenValid_whenFindAllLocales_thenReturnList() {
        ResponseEntity<List<Locale>> listLocales = localidadeService.findAllLocalidade();

        Assert.assertEquals(listLocales.getStatusCode(), HttpStatus.OK);
        Assert.assertNotEquals(listLocales, IsEmptyCollection.empty());
    }

    @Test
    public void givenValid_whenFindLocalidadeByName_thenReturnLocale() {
        String VALID = "São Paulo";
        State state = TesteUtil.buildState(35, "São Paulo", "SP", 3, "Sudeste", "SE");
        Zone zone = TesteUtil.buildZone(3550308, "São Paulo", 35061, "São Paulo", 3515, "Metropolitana de São Paulo");

        Locale locale = localidadeService.findLocalidadeByName(VALID);

        Assert.assertNotNull(locale);
        Assert.assertEquals(locale.getNomeCidade(), localidadeService.buildLocale(zone, state).getNomeCidade());
    }

    @Test
    public void givenValid_whenBuildLocale_thenOk() {
        State state = TesteUtil.buildState(35, "São Paulo", "SP", 3, "Sudeste", "SE");
        Zone zone = TesteUtil.buildZone(3550308, "São Paulo", 35061, "São Paulo", 3515, "Metropolitana de São Paulo");

        Locale locale = localidadeService.buildLocale(zone, state);

        Assert.assertNotNull(locale);
        Assert.assertEquals(locale.getIdEstado(), String.valueOf(35));
    }

    @Test
    public void givenValid_whenDefaultState_thenOk() {
        State state = TesteUtil.buildState(35, "São Paulo", "SP", 3, "Sudeste", "SE");

        List<State> states = localidadeService.defaultState();

        Assert.assertNotEquals(states, IsEmptyCollection.empty());
        Assert.assertEquals(states.get(0).getId(), state.getId());
    }
//
//    @Test
//    public void givenValid_whenDefaultZone_thenOk() {
//        Integer VALID = 35;
//        Zone zone = TesteUtil.buildZone(3550308, "São Paulo", 35061, "São Paulo", 3515, "Metropolitana de São Paulo");
//
//        List<Zone> zones = localidadeService.defaultZoneByState(VALID);
//
//        Assert.assertNotEquals(zones, IsEmptyCollection.empty());
//        Assert.assertEquals(zones.get(0).getId(), zone.getId());
//    }
}
