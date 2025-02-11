package br.net.dotbr.listadecomprasmercado.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank(message = "ER_0000000004") // CodigoErroNegocio.EMAIL_VAZIO_INVALIDO
        @Email(message = "ER_0000000002") // CodigoErroNegocio.EMAIL_INVALIDO
        String email,
        @NotBlank(message = "ER_0000000003") // CodigoErroNegocio.SENHA_VAZIA_INVALIDO
        String senha) {
}
