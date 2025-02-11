package br.net.dotbr.listadecomprasmercado.infra.exception;

import org.springframework.http.HttpStatus;

public record ErroDeNegocio(String mensagem, int status, String codigoErro,boolean erro) {
    public ErroDeNegocio(CodigoErroNegocio codigoErroNumerico, HttpStatus status) {
        this(MensagemErro.valueOf(codigoErroNumerico.name()).getMensagem(), status.value(), codigoErroNumerico.getCodigo(),true);
    }
}
