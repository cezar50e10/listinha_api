package br.net.dotbr.listadecomprasmercado.domain.listadecompra;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoListaDeCompraRepository extends JpaRepository<ProdutoListaDeCompra,Long> {
    List<ProdutoListaDeCompra> findAllByListaDeCompraIdAndStatus(Long id, StatusProdutoLista statusProdutoLista);
}
