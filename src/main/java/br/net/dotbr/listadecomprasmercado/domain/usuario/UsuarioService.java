package br.net.dotbr.listadecomprasmercado.domain.usuario;

import br.net.dotbr.listadecomprasmercado.infra.security.DadosTokenJWT;
import br.net.dotbr.listadecomprasmercado.infra.security.TokenService;
import br.net.dotbr.listadecomprasmercado.infra.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;

    private final SessionService sessionService;

    // Injeção de SessionService
    public UsuarioService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public ResponseEntity cadastrarUsuario(DadosCadastroUsuario dadosCadastroUsuario){

        if(repository.existsByEmail(dadosCadastroUsuario.email())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario Já Cadastrado");
        }else{
            repository.save(new Usuario(dadosCadastroUsuario));
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Casdastrado com sucesso");
        }
    }

    public ResponseEntity efetuarLogin(DadosAutenticacao dados) {
        var usuarioLogin = new Usuario(dados);
        var usuario = repository.findByEmail(usuarioLogin.getEmail());

        if(usuario.isPresent()) {
            if(usuario.get().getSenha().equals(usuarioLogin.getSenha())) {
                var tokenJWT = tokenService.gerarToken(usuarioLogin);//(Usuario) authentication.getPrincipal());

                sessionService.salvarNaSessao("usuarioLogado", usuario);  // Salvando o objeto na sessão
                return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario Inválido");
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario Inválido");
        }
    }

    public ResponseEntity listarUsuario() {
        //var usuario = sessionService.recuperarDaSessao("usuarioLogado");

        List<DadosRetornoPesquisaUsuario> listaUsuario = repository.findAll().stream().map(
                usuario -> new DadosRetornoPesquisaUsuario(usuario.getId(),usuario.getEmail())
        ).toList();

        return ResponseEntity.ok(listaUsuario);
    }

    public ResponseEntity pesquisarUsuario(DadosPesquisaUsuario dados) {
        List<DadosRetornoPesquisaUsuario> listaUsuario = repository.findByEmailContaining(dados.email()).stream().map(
                usuario -> new DadosRetornoPesquisaUsuario(usuario.getId(),usuario.getEmail())
        ).toList();

        return ResponseEntity.ok(listaUsuario);
    }
}
