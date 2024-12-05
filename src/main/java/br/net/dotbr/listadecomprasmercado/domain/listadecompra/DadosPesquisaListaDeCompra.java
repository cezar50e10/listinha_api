package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosPesquisaListaDeCompra(
        @NotNull(message = "ID inválido")
        @JsonProperty("id_lista_compra")
        Long idListaCompra) {
}
