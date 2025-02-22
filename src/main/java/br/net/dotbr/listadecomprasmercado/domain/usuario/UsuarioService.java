package br.net.dotbr.listadecomprasmercado.domain.usuario;

import br.net.dotbr.listadecomprasmercado.infra.exception.CodigoErroNegocio;
import br.net.dotbr.listadecomprasmercado.infra.exception.ErroDeNegocio;
import br.net.dotbr.listadecomprasmercado.infra.security.DadosTokenJWT;
import br.net.dotbr.listadecomprasmercado.infra.security.TokenService;
import br.net.dotbr.listadecomprasmercado.infra.session.SessionService;
import br.net.dotbr.listadecomprasmercado.infra.sucesso.CodigoSucessoOperacao;
import br.net.dotbr.listadecomprasmercado.infra.sucesso.SucessoOpercao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.time.Duration;
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroDeNegocio(CodigoErroNegocio.EMAIL_JA_CADASTRADO, HttpStatus.CONFLICT));
        }else{
            repository.save(new Usuario(dadosCadastroUsuario));
            return ResponseEntity.status(HttpStatus.CREATED).body(new SucessoOpercao(CodigoSucessoOperacao.USUARIO_CADASTRADO,HttpStatus.CREATED,null));
        }
    }
/*
    public ResponseEntity efetuarLogin(DadosAutenticacao dados) {
        var usuarioLogin = new Usuario(dados);
        var usuario = repository.findByEmail(usuarioLogin.getEmail());

        if(usuario.isPresent()) {
            if(usuario.get().getSenha().equals(usuarioLogin.getSenha())) {
                var tokenJWT = tokenService.gerarToken(usuarioLogin);//(Usuario) authentication.getPrincipal());

                sessionService.salvarNaSessao("usuarioLogado", usuario);  // Salvando o objeto na sessão
                return ResponseEntity.ok(
                        new SucessoOpercao(CodigoSucessoOperacao.LOGIN_EFETUADO,HttpStatus.OK,new DadosTokenJWT(tokenJWT)));
            }else{
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErroDeNegocio(CodigoErroNegocio.USUARIO_INVALIDO, HttpStatus.UNAUTHORIZED));

            }
        }else{
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErroDeNegocio(CodigoErroNegocio.USUARIO_INVALIDO, HttpStatus.UNAUTHORIZED));
        }
    }*/


    public ResponseEntity<?> efetuarLogin(DadosAutenticacao dados, HttpServletResponse response) {
        var usuarioLogin = new Usuario(dados);
        var usuario = repository.findByEmail(usuarioLogin.getEmail());

        if (usuario.isPresent()) {
            if (usuario.get().getSenha().equals(usuarioLogin.getSenha())) {
                var tokenJWT = tokenService.gerarToken(usuarioLogin);

                // Criando um cookie seguro para armazenar o token
                ResponseCookie cookie = ResponseCookie.from("token", tokenJWT)
                        .httpOnly(true)  // Protege contra XSS
                        //.secure(false)  // TRUE apenas se usar HTTPS
                        .sameSite("Strict")  // Protege contra CSRF
                        .path("/")  // Disponível para toda a aplicação
                        .maxAge(Duration.ofHours(2))  // Expira em 2 horas
                        .build();

                sessionService.salvarNaSessao("usuarioLogado", usuario);

                // Adicionando o cookie à resposta
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.ok()
                        .body(new SucessoOpercao(CodigoSucessoOperacao.LOGIN_EFETUADO, HttpStatus.OK, null));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErroDeNegocio(CodigoErroNegocio.USUARIO_INVALIDO, HttpStatus.UNAUTHORIZED));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErroDeNegocio(CodigoErroNegocio.USUARIO_INVALIDO, HttpStatus.UNAUTHORIZED));
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

    public ResponseEntity verificarSessao(HttpServletRequest request) {
        String token = tokenService.recuperarTokenDoCookie(request);

        tokenService.getSubject(token);

        return ResponseEntity.ok()
                .body(new SucessoOpercao(CodigoSucessoOperacao.USUARIO_LOGADO, HttpStatus.OK, null));
    }
}
