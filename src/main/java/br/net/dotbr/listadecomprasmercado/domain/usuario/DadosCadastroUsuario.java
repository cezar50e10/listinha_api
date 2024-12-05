package br.net.dotbr.listadecomprasmercado.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank(message = "Email não pode ser nulo ou vazio")
        @Email(message = "Email inválido")
        String email,
                                   String senha) {
}
