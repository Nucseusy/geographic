package com.br.microservice.geographic.adapter;

public class JSONFile implements IGenericFile {

    private String fileName;
    private String header;

    public JSONFile(String fileName){
        setFileName(fileName);
        setHeader("[{");
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
        return "}]";
    }

    public String getSeparator() {
        return ",";
    }

    public String getInitialParameterName() {
        return " \"%s\": ";
    }

    public String getFinishParameterName() {
        return "";
    }

    public String getInitialParameterValue() {
        return "\"";
    }

    public String getFinishParameterValue() {
        return "\"";
    }

    public String getInitialBlock() {
        return "{";
    }

    public String getFinishBlock() {
        return "}";
    }

    public boolean isUseParameterName() {
        return true;
    }

    public String getContentType() {
        return EnumFile.JSON.getcontextType();
    }

    public String getBlockSeparator() {
        return ",";
    }
}
