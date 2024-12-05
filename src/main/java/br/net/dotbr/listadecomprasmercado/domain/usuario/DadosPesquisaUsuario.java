package br.net.dotbr.listadecomprasmercado.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosPesquisaUsuario(
        @NotBlank(message = "Email não pode ser nulo ou vazio")
        //@Email(message = "Email inválido") nesse caso vou deixar pesquisar tudo
        @Size(min = 3, message = "Digite ao menos 3 caracteres para pesquisar")
        String email) {
}
