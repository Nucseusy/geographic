package com.br.microservice.geographic.controller;

import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.exception.BreakForEachException;
import com.br.microservice.geographic.service.LocalidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/br/localidade")
@Api(tags = {"Localidade"})
public class GeographicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeographicController.class);

    @Autowired
    LocalidadeService localidadeService;

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Pesquisa uma localidade pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Localidade não encontrada!"),
            @ApiResponse(code = 400, message = "Problema no nome informado."),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public ResponseEntity<Locale> getLocaleByName(@PathVariable("name") String name) {
        LOGGER.info("Processing find by name... ");
        Locale locale = localidadeService.findLocalidadeByName(name);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePrivate())
                .body(locale);
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
}
