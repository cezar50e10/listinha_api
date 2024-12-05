package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;

public record DadosListagemProdutoAvulso(
        Long id,
        String nome,
		String descricao,
		StatusProdutoAvulso status,
        String tipo_medida) {
}
