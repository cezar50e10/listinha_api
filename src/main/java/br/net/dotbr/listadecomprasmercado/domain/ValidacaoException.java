package br.net.dotbr.listadecomprasmercado.domain;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
