package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

public record DadosRetornoListagemListaCompra(
        Long idSeqProdutoLista,
        Long idProduto,
        Long idListaCompra,
        int qtdEstoque,
        int qtdDesejada,
        TipoProdutoLista tipoProdutoLista,
        String nomeProduto,
        String descricao
) {
}
