package com.br.microservice.geographic.adapter;

public interface IGenericFile {
    void setFileName(String fileName);

    String getFileName();

    String getHeader();

    void setHeader(String header);

    String getFooter();

    String getSeparator();

    String getInitialParameterName();

    String getFinishParameterName();

    String getInitialParameterValue();

    String getFinishParameterValue();

    String getInitialBlock();

    String getFinishBlock();

    boolean isUseParameterName();

    String getContentType();

    String getBlockSeparator();
}
