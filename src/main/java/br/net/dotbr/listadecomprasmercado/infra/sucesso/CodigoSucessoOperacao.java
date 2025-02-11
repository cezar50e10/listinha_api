package br.net.dotbr.listadecomprasmercado.infra.sucesso;

import br.net.dotbr.listadecomprasmercado.infra.exception.CodigoErroNegocio;

public enum CodigoSucessoOperacao {
    MENSAGEM_DE_SUCESSO_NAO_ENCONTRADA("SU_0000000000"),
    USUARIO_CADASTRADO("SU_0000000001"),
    LOGIN_EFETUADO("SU_0000000002")
    ;

    private final String codigo;

    CodigoSucessoOperacao(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    // Método para obter o enum a partir da mensagem
    public static CodigoSucessoOperacao fromMessage(String message) {
        for (CodigoSucessoOperacao erro : values()) {
            if (erro.codigo.equalsIgnoreCase(message)) {
                return erro;
            }
        }
        return CodigoSucessoOperacao.MENSAGEM_DE_SUCESSO_NAO_ENCONTRADA;
    }
}
