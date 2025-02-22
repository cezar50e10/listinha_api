package br.net.dotbr.listadecomprasmercado.domain.listadecompra;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaDeCompraRepository extends JpaRepository<ListaDeCompra,Long> {
    List<ListaDeCompra> findByParticipantesUsuarioId(Long idUsuarioParticipante);
}
