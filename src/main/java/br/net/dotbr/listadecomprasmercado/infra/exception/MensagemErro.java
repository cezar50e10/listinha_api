package br.net.dotbr.listadecomprasmercado.infra.exception;

public enum MensagemErro {
    MENSAGEM_DE_ERRO_NAO_ENCONTRADA("Mensagem de Erro Não Encontrada"),
    USUARIO_INVALIDO("Usuário inválido"),
    EMAIL_INVALIDO("Email Informado Invalido"),
    SENHA_VAZIA_INVALIDO("Senha Não Pode Ser Vazia"),
    EMAIL_VAZIO_INVALIDO("Email Não Pode Ser Vazio"),
    EMAIL_JA_CADASTRADO("Email Já Cadastrado")
    ;



    private final String mensagem;

    MensagemErro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}