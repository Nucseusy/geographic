package com.br.microservice.geographic.adapter;

import com.br.microservice.geographic.data.Locale;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public interface File {
    OutputStream getOutputStream(HttpServletResponse response, List<Locale> locales) throws Exception;
}
