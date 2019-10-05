package com.br.microservice.geographic.controller;

import com.br.microservice.geographic.data.State;
import com.br.microservice.geographic.data.Zone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/br/localidade")
@Api(tags = {"Localidade"})
public class GeographicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeographicController.class);

    @Value("${uri.states}")
    private String _uriState;

    @Value("${uri.states}${uri.zone}")
    private String _uriZone;


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Pesquisa uma localidade pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Localidade não encontrada!"),
            @ApiResponse(code = 400, message = "Problema no nome informado."),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public List<State> getLocaleByName(@PathVariable("name") String name) {
        LOGGER.info("Creating locale object ... ");

        List<State> list = getApi(_uriState, HttpMethod.POST, null, new ParameterizedTypeReference<List<State>>() { }, null);

        list.stream().forEach(state -> {
            List<Zone> zones = getApi(_uriZone, HttpMethod.POST, null, new ParameterizedTypeReference<List<Zone>>() { }, state.getId());

        });

        LOGGER.info("Returning locale... ");

        return list;
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Retorna todos os dados da localidade como um arquivo CSV")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Tipo de arquivo não aceito!"),
            @ApiResponse(code = 400, message = "Problema no nome do tipo de arquivo."),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public OutputStream getFileCsv() {
        LOGGER.info("Creating file object ... ");
        List<Integer> output = new LinkedList<>();
        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                output.add(b);
            }
        };

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        // write all the things via CsvBuilder
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new InputStream() {
            @Override
            public int read() throws IOException {
                if (output.size() > 0) {
                    return output.remove(0);
                }
                return -1;
            }
        }));
        LOGGER.info("Returning file... ");
        return outputStream;
    }

    @GetMapping("/download/json")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Retorna todos os dados da localidade em formato JSON")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Tipo de retorno não aceito!"),
            @ApiResponse(code = 400, message = "Problema ao gerar retorno"),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public long getFileJson() {
        return 0;
    }

    public <T> List<T> getApi(final String path, final HttpMethod method, final HttpEntity requestEntity,
                              final ParameterizedTypeReference typeRef,
                              final Object... uriVariables) {
        final RestTemplate restTemplate = new RestTemplate();

        if (uriVariables != null ){
            return ((List<T>) restTemplate.exchange(path, method, requestEntity, typeRef, uriVariables).getBody());
        } else {
            return ((List<T>) restTemplate.exchange(path, method, requestEntity, typeRef).getBody());
        }
    }
}
