package com.br.microservice.geographic.adapter;

import com.br.microservice.geographic.data.Locale;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public interface IWriteFileAdapter {
    OutputStream getJson(HttpServletResponse response, List<Locale> locales) throws Exception;

    OutputStream getCsv(HttpServletResponse response, List<Locale> locales) throws Exception;

    OutputStream getXML(HttpServletResponse response, List<Locale> locales) throws Exception;
}
