package com.br.microservice.geographic.service;

import com.br.microservice.geographic.data.Locale;
import com.br.microservice.geographic.data.State;
import com.br.microservice.geographic.data.Zone;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILocalidadeService {
    List<State> findAllStates();

    List<Zone> findZonesByState(Integer ufId);

    ResponseEntity<List<Locale>> findAllLocalidade();

    Locale findLocalidadeByName(String name) ;
}
