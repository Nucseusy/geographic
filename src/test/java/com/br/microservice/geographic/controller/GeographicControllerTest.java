package com.br.microservice.geographic.controller;

import com.br.microservice.geographic.TesteUtil;
import com.br.microservice.geographic.adapter.EnumFile;
import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.data.State;
import com.br.microservice.geographic.data.Zone;
import com.br.microservice.geographic.service.LocalidadeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GeographicControllerTest {
    @MockBean
    LocalidadeService localidadeService;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenValid_whenFindLocalidadeByName_thenLocaleJson() throws Exception {
        final String VALID = "São Paulo";
        State state = TesteUtil.buildState(33, "São Paulo", "SP", 3, "Sudeste", "SE");
        Zone zone = TesteUtil.buildZone(3550308, "São Paulo", 35061, "São Paulo", 3515, "Metropolitana de São Paulo");

        Locale locale = localidadeService.buildLocale(zone, state);
        given(localidadeService.findLocalidadeByName(VALID)).willReturn(locale);

        this.mockMvc.perform(get("/api/v1/br/localidade/{name}", VALID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TesteUtil.toJson(locale)))
                .andExpect(status().isOk());
    }


    @Test
    public void givenNull_whenFindLocalidadeByName_thenNullReturn() throws Exception {
        final String NULL = null;

        Locale locale = null;
        given(localidadeService.findLocalidadeByName(NULL)).willReturn(locale);

        this.mockMvc.perform(get("/api/v1/br/localidade/{name}", NULL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenEmpty_whenFindLocalidadeByName_thenJsonBlankReturn() throws Exception {
        final String EMPTY = null;

        Locale locale = null;
        given(localidadeService.findLocalidadeByName(EMPTY)).willReturn(locale);

        this.mockMvc.perform(get("/api/v1/br/localidade/{name}", EMPTY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test(expected = Exception.class)
    public void givenValid_whenGetJsonLocales_thenJsonLocaleReturn() throws Exception {
        this.mockMvc.perform(get("/api/v1/br/localidade/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidFileFormat_whenGetJsonLocalidade_thenReturnValueDefaul() throws Exception {
        String contentType = "text/plain";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/br/localidade/download/")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test(expected = Exception.class)
    public void givenValidCSV_whenGetDownloadFile_thenReturnFile() throws Exception {
        EnumFile VALID = EnumFile.CSV;
        this.mockMvc.perform(get("/api/v1/br/localidade/download/{type}", VALID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void givenValidJSON_whenGetDownloadFile_thenReturnFile() throws Exception {
        EnumFile VALID = EnumFile.JSON;
        this.mockMvc.perform(get("/api/v1/br/localidade/download/{type}", VALID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
