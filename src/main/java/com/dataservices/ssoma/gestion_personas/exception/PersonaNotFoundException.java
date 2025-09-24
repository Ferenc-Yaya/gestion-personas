package com.dataservices.ssoma.gestion_personas.exception;

public class PersonaNotFoundException extends RuntimeException {

    public PersonaNotFoundException(String message) {
        super(message);
    }

    public PersonaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
