package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroListaDeCompra(
        @NotBlank(message = "Nome da lista não pode ser nulo ou vazio")
        String nome,
        String descricao) {
}
