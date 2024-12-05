package br.net.dotbr.listadecomprasmercado.domain.usuario;

import br.net.dotbr.listadecomprasmercado.domain.listadecompra.ParticipanteListaCompra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario  implements UserDetails , Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    private String status;

    // Relacionamento com ParticipanteListaCompra
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ParticipanteListaCompra> participacoes = new ArrayList<>();

    public Usuario(DadosCadastroUsuario dados) {
        this.email = dados.email();
        this.senha = dados.senha();
        this.status = "ATIVO";
    }

    public Usuario(DadosAutenticacao dados) {
        this.email = dados.email();
        this.senha = dados.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
