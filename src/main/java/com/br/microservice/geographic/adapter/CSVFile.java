package com.br.microservice.geographic.adapter;

public class CSVFile implements IGenericFile {

    private String fileName;
    private String header;

    public CSVFile(String fileName, String header){
        setFileName(fileName);
        setHeader(header);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getHeader() {
        return this.header;
    }


    public void setHeader(String header) {
        this.header = header;
    }


    public String getFooter() {
        return null;
    }

    public String getSeparator() {
        return ",";
    }

    public String getInitialParameterName() {
        return null;
    }

    public String getFinishParameterName() {
        return null;
    }

    public String getInitialParameterValue() {
        return null;
    }

    public String getFinishParameterValue() {
        return null;
    }

    public String getInitialBlock() {
        return null;
    }

    public String getFinishBlock() {
        return null;
    }

    public boolean isUseParameterName() {
        return false;
    }

    public String getContentType() {
        return EnumFile.CSV.getcontextType();
    }

    public String getBlockSeparator() {
        return null;
    }
}
