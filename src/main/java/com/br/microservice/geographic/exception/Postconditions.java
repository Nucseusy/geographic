package com.br.microservice.geographic.exception;

public class Postconditions {

    private Postconditions() {}

    public static <T> T checkNull( final T object ) {
        if ( object == null ) {
            throw new DataNotFoundException();
        }
        return object;
    }
}