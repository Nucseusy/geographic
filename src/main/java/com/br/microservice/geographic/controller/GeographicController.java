package com.br.microservice.geographic.controller;

import com.br.microservice.geographic.adapter.EnumFile;
import com.br.microservice.geographic.adapter.IWriteFileAdapter;
import com.br.microservice.geographic.adapter.WriteFileAdapterImpl;
import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.data.Zone;
import com.br.microservice.geographic.exception.Postconditions;
import com.br.microservice.geographic.service.LocalidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

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
        LOGGER.info("Processing find by name... " + name);
        Locale locale = localidadeService.findLocalidadeByName(name);

        return new ResponseEntity<>(locale, HttpStatus.OK);
    }

    @GetMapping("/download/{type}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna todos os dados da localidade como um arquivo CSV")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Tipo de arquivo não aceito!"),
            @ApiResponse(code = 400, message = "Problema no nome do tipo de arquivo."),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public OutputStream getDownloadFile(HttpServletResponse response, @PathVariable("type") EnumFile type) throws Exception {
        ResponseEntity<List<Locale>> locales = localidadeService.findAllLocalidade();
        IWriteFileAdapter fileAdapter = new WriteFileAdapterImpl();
        OutputStream out = getFileOutput(fileAdapter, type.name(), response, locales.getBody());
        return out;
    }


    @GetMapping("/json")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Retorna todos os dados da localidade em formato JSON")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Tipo de retorno não aceito!"),
            @ApiResponse(code = 400, message = "Problema ao gerar retorno"),
            @ApiResponse(code = 302, message = "Sucesso!")
    }
    )
    public ResponseEntity<List<Locale>> getJsonLocales() {
        return Postconditions.checkNull(localidadeService.findAllLocalidade());
    }

    @GetMapping("/municipio/{id}")
    public ResponseEntity<List<Zone>> getMunicipios(@PathVariable("id") int id){
        return new ResponseEntity<>(localidadeService.findZonesByState(id), HttpStatus.FOUND);
    }

    private OutputStream getFileOutput(IWriteFileAdapter fileAdapter, String type, HttpServletResponse response, List<Locale> locales) throws Exception {
        switch (type){
            case "JSON": return fileAdapter.getJson(response, locales);
            case "XML": return fileAdapter.getXML(response, locales);
            default: return fileAdapter.getCsv(response, locales);
        }
    }
}
