package br.net.dotbr.listadecomprasmercado.infra.sucesso;

public enum MensagemSucesso {
    MENSAGEM_DE_SUCESSO_NAO_ENCONTRADA("Mensagem de Sucesso Não Encontrada"),
    USUARIO_CADASTRADO("Usuario Casdastrado com sucesso"),
    LOGIN_EFETUADO("Login Efetuado")
    ;



    private final String mensagem;

    MensagemSucesso(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
