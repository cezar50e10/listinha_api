package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAlteracaoProdutoAvulso(
        @NotNull(message = "Codigo ID do produto não pode ser nulo ou vazio")
        Long id,
        @NotBlank(message = "Nome do produto não pode ser nulo ou vazio")
        String nome,
        String descricao,
        @NotBlank(message = "Tipo Medida do produto não pode ser nulo ou vazio")
        @JsonProperty("tipo_medida")
        String tipoMedida,
        @NotNull(message = "Status do produto não pode ser nulo ou vazio")
        StatusProdutoAvulso status) {
}
