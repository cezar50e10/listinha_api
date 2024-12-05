package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DadosRemoveParticipanteListaDeCompra(
        @NotNull(message = "ID da lista não pode ser nulo ou vazio")
        @JsonProperty("id_lista_compra")
        Long idListaCompra,
        @NotNull(message = "ID do Usuario não pode ser nulo ou vazio")
        @JsonProperty("id_usuario")
        Long idUsuario) {
}
