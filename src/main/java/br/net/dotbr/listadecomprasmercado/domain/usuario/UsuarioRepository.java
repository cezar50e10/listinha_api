package br.net.dotbr.listadecomprasmercado.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email); // Para verificar a existência

    List<Usuario> findByEmailContaining(String email);

    Optional<Usuario> findById(Long id);

    //UserDetails findByEmail(String email);
}
