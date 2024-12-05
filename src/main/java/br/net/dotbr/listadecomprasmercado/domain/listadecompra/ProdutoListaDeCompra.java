package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.ProdutoAvulso;
import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "produtos_lista_de_compra")
@Entity(name = "ProdutoListaDeCompra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProdutoListaDeCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private StatusProdutoLista status;


    @Enumerated(EnumType.STRING)
    private TipoProdutoLista tipoProduto;
    @Column(name = "qtd_desejada")
    private int qtdDesejada;
    @Column(name = "qtd_estoque")
    private int qtdEmEstoque;

   // Referência para a ListaDeCompra
    @ManyToOne
    @JoinColumn(name = "id_lista_de_compra")
    private ListaDeCompra listaDeCompra;


    // Referência para o Produto
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutoAvulso produtoAvulso;

    public ProdutoListaDeCompra(DadosAdicionaProdutoListaDeCompra dados, ListaDeCompra listaDeCompra, ProdutoAvulso produtoAvulso) {
        this.produtoAvulso = produtoAvulso;
        this.listaDeCompra = listaDeCompra;
        this.status = StatusProdutoLista.ATIVO;
        this.tipoProduto = dados.tipoProduto();
        this.qtdDesejada = dados.qtd_desejada();
        this.qtdEmEstoque = dados.qtd_estoque();
    }

    public ProdutoListaDeCompra(DadosAlterarProdutoListaDeCompra dados, ListaDeCompra listaDeCompra, ProdutoAvulso produtoAvulso) {
        this.id = dados.idSeqProdutoLista();
        this.produtoAvulso = produtoAvulso;
        this.listaDeCompra = listaDeCompra;
        this.status = dados.status();
        this.tipoProduto = dados.tipoProduto();
        this.qtdDesejada = dados.qtd_desejada();
        this.qtdEmEstoque = dados.qtd_estoque();
    }



}
