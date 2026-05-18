package com.example.demo.exception;

public class ConflictoExc extends RuntimeException {
    public ConflictoExc() {
        super("Ocurrió un conflicto al procesar la solicitud");
    }

    public ConflictoExc(String mensaje) {
        super(mensaje);
    }
}
