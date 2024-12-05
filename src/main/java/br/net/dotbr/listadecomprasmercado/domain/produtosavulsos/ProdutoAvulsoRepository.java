package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoAvulsoRepository extends JpaRepository<ProdutoAvulso,Long> {
    Optional<ProdutoAvulso> findByIdAndStatus(Long id, StatusProdutoAvulso status);

    List<ProdutoAvulso> findAllByStatus(StatusProdutoAvulso status);
}
