package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;

import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "produtos_avulsos")
@Entity(name = "ProdutoAvulso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProdutoAvulso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusProdutoAvulso status;
    private String tipoMedida;

    // Relacionamento com Usuario
    @OneToOne
    @JoinColumn(name = "id_usuario_dono")
    private Usuario usuario;

    public ProdutoAvulso(DadosCadastroProdutoAvulso dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.tipoMedida = dados.tipoMedida();
        this.status = StatusProdutoAvulso.ATIVO;
    }

    public ProdutoAvulso(DadosAlteracaoProdutoAvulso dados, Usuario usuario) {
        this.id = dados.id();
        this.usuario = usuario;
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.tipoMedida = dados.tipoMedida();
        this.status = dados.status();
    }
}
