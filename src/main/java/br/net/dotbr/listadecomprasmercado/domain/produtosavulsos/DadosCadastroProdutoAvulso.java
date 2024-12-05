package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroProdutoAvulso(
        @NotBlank(message = "Nome do produto não pode ser nulo ou vazio")
        String nome,
        String descricao,
        @NotBlank(message = "Tipo Medida do produto não pode ser nulo ou vazio")
        @JsonProperty("tipo_medida")
        String tipoMedida) {
}
