package br.com.fiap.petfiap.exception;

public class AtendimentoNaoEncontradoException extends RuntimeException {

    public AtendimentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
