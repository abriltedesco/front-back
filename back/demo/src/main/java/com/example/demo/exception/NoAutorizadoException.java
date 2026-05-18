package com.example.demo.exception;

public class NoAutorizadoException extends RuntimeException {
    public NoAutorizadoException() {
        super("No tenés permiso para realizar esta acción");
    }    
}
