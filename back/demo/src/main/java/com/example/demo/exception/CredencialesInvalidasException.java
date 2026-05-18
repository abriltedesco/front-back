package com.example.demo.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() {
        super("Ingresaste mal tus datos. Verificá tu email y contraseña.");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
