package br.com.fiap.petfiap.exception;

public class StatusInvalidoException extends RuntimeException {

    public StatusInvalidoException(String mensagem) {
        super(mensagem);
    }
}
