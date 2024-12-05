package br.net.dotbr.listadecomprasmercado.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosRetornoPesquisaUsuario(
        Long id_usuario,
        String email) {
}
