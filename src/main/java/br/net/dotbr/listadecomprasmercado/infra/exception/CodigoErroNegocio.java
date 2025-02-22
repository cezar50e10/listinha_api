package br.net.dotbr.listadecomprasmercado.infra.exception;

public enum CodigoErroNegocio {
    MENSAGEM_DE_ERRO_NAO_ENCONTRADA("ER_0000000000"),
    USUARIO_INVALIDO("ER_0000000001"),
    EMAIL_INVALIDO("ER_0000000002"),
    SENHA_VAZIA_INVALIDO("ER_0000000003"),
    EMAIL_VAZIO_INVALIDO("ER_0000000004"),
    EMAIL_JA_CADASTRADO("ER_0000000005"),
    TOKEN_JWT_INVALIDO_EXPIRADO("ER_0000000006")
    ;

    private final String codigo;

    CodigoErroNegocio(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    // Método para obter o enum a partir da mensagem
    public static CodigoErroNegocio fromMessage(String message) {
        for (CodigoErroNegocio erro : values()) {
            if (erro.codigo.equalsIgnoreCase(message)) {
                return erro;
            }
        }
        return CodigoErroNegocio.MENSAGEM_DE_ERRO_NAO_ENCONTRADA;
    }
}

