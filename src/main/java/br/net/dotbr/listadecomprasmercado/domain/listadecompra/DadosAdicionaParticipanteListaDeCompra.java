package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAdicionaParticipanteListaDeCompra(
        @NotNull(message = "ID da lista não pode ser nulo ou vazio")
        @JsonProperty("id_lista_compra")
        Long idListaCompra,
        @NotNull(message = "ID do Usuario não pode ser nulo ou vazio")
        @JsonProperty("id_usuario")
        Long idUsuario,
        @NotNull(message = "cargo usuario não pode ser nulo ou vazio")
        @JsonProperty("cargo")
        CargoUsuarioLista cargo) {
}
