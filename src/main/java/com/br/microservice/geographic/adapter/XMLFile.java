package com.br.microservice.geographic.adapter;

public class XMLFile implements IGenericFile {

    private String fileName;
    private String header;

    public XMLFile(String fileName){
        setFileName(fileName);
        setHeader("{");
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root> \n";
    }

    public void setHeader(String header) {
    }

    public String getFooter() {
        return "</root>";
    }

    public String getSeparator() {
        return "\n";
    }

    public String getInitialParameterName() {
        return "<%s>";
    }

    public String getFinishParameterName() {
        return "</%s>";
    }

    public String getInitialParameterValue() {
        return "";
    }

    public String getFinishParameterValue() {
        return "";
    }

    @Override
    public String getInitialBlock() {
        return "<element>";
    }

    @Override
    public String getFinishBlock() {
        return "</element>";
    }

    public boolean isUseParameterName() {
        return true;
    }

    public String getContentType() {
        return EnumFile.XML.getcontextType();
    }

    public String getBlockSeparator() {
        return "\n";
    }
}
