package br.net.dotbr.listadecomprasmercado.infra.sucesso;

import br.net.dotbr.listadecomprasmercado.infra.exception.CodigoErroNegocio;
import br.net.dotbr.listadecomprasmercado.infra.exception.MensagemErro;
import org.springframework.http.HttpStatus;

public record SucessoOpercao(String mensagem, int status, String codigoOperacao, boolean erro, Object conteudo) {
    public SucessoOpercao(CodigoSucessoOperacao codigoSucessoOperacao, HttpStatus status, Object conteudo) {
        this(MensagemSucesso.valueOf(codigoSucessoOperacao.name()).getMensagem(), status.value(), codigoSucessoOperacao.getCodigo(),false,conteudo);
    }
}
