package br.net.dotbr.listadecomprasmercado.domain.listadecompra;

import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "ParticipanteListaCompra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ParticipanteListaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Referência para o Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario_participante")
    private Usuario usuario;

    // Referência para a ListaDeCompra
    @ManyToOne
    @JoinColumn(name = "id_lista_de_compra")
    private ListaDeCompra listaDeCompra;

    @Enumerated(EnumType.STRING)
    private CargoUsuarioLista cargo;
    @Enumerated(EnumType.STRING)
    private StatusUsuarioListaCompra status;

    public ParticipanteListaCompra(ListaDeCompra listaDeCompra, Usuario usuario) {
        this.usuario = usuario;
        this.listaDeCompra = listaDeCompra;
        this.cargo = CargoUsuarioLista.ADMIN;
        this.status = StatusUsuarioListaCompra.ATIVO;
    }


    public ParticipanteListaCompra(ListaDeCompra listaDeCompraAtual, Usuario usuarioAdd, CargoUsuarioLista cargo) {
        this.usuario = usuarioAdd;
        this.listaDeCompra = listaDeCompraAtual;
        this.cargo = cargo;
        this.status = StatusUsuarioListaCompra.ATIVO;
    }
}
