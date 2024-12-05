package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "listas_de_compras")
@Entity(name = "ListaDeCompra")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ListaDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String status;


    // Relacionamento com ParticipanteListaCompra
    @OneToMany(mappedBy = "listaDeCompra", cascade = CascadeType.ALL)
    private List<ParticipanteListaCompra> participantes = new ArrayList<>();

    // Relacionamento com ProdutoListaCompra
    @OneToMany(mappedBy = "listaDeCompra", cascade = CascadeType.ALL)
    private List<ProdutoListaDeCompra> produtoListaDeCompras = new ArrayList<>();


    public ListaDeCompra(DadosCadastroListaDeCompra dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.status = "ATIVO";
    }
}
