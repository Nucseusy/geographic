package com.br.microservice.geographic;

import com.br.microservice.geographic.data.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class TesteUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static String toJson(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static State buildState(Integer id, String name, String initial, Integer regionId, String regionName, String regionInitial) {
        State state = State.builder()
                .id(id)
                .name(name)
                .initial(initial)
                .region(Region.builder()
                        .id(regionId)
                        .name(regionName)
                        .initial(regionInitial)
                        .build())
                .build();
        return state;
    }

    public static State getStateMock() {
        return State.builder()
                .id(35)
                .name("São Paulo")
                .initial("SP")
                .region(Region.builder()
                        .id(3)
                        .name("Sudeste")
                        .initial("SE")
                        .build())
                .build();
    }

    public static Zone buildZone(Integer id, String name, Integer microregionId, String microregionName, Integer mesoregionId, String mesoregionName) {
        Zone zone = Zone.builder()
                .id(id)
                .name(name)
                .microRegion(MicroRegion.builder()
                        .id(microregionId)
                        .name(microregionName)
                        .mesoRegion(MesoRegion.builder()
                                .id(mesoregionId)
                                .name(mesoregionName)
                                .build())
                        .build())
                .build();
        return zone;
    }

    public static Zone getZoneMock() {
        return Zone.builder()
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
                .build();
    }

}
