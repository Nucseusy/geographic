package com.br.microservice.geographic.adapter;

public enum EnumFile {
    CSV("application/csv"), JSON("application/json"), XML("application/xml");

    private final String contextType;

    EnumFile(String contextTypeOpcao) {
        contextType = contextTypeOpcao;
    }

    public String getcontextType() {
        return contextType;
    }
}
