package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DadosAlterarProdutoListaDeCompra(
        @NotNull(message = "ID da sequencial lista não pode ser nulo ou vazio")
        @JsonProperty("id_seq_produto_lista")
        Long idSeqProdutoLista,
        @NotNull(message = "ID da lista não pode ser nulo ou vazio")
        @JsonProperty("id_lista_compra")
        Long idListaCompra,
        @NotNull(message = "ID do produto não pode ser nulo ou vazio")
        @JsonProperty("id_produto")
        Long idProduto,
        @NotNull(message = "Status não pode ser nulo ou vazio")
        StatusProdutoLista status,
        @Min(value = 1,message = "Quantidade Desejada do produto não pode ser nulo ou vazio")
        int qtd_desejada,
        @Min(value = 1,message = "Quantidade em estoque do produto não pode ser nulo ou vazio")
        int qtd_estoque,
        @NotNull(message = "tipo do produto não pode ser nulo ou vazio")
        @JsonProperty("tipo_produto")
        TipoProdutoLista tipoProduto) {
}
