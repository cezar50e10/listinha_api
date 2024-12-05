package br.net.dotbr.listadecomprasmercado.infra.security;

import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import br.net.dotbr.listadecomprasmercado.infra.session.SessionService;
import br.net.dotbr.listadecomprasmercado.domain.usuario.UsuarioRepository;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    private final SessionService sessionService;

    public SecurityFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByEmail(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try{
                var subject = tokenService.getSubject(tokenJWT);

                // Obter o usuário de forma segura
                var usuarioOptional = repository.findByEmail(subject);

                // Verifique se o usuário está presente
                if (usuarioOptional.isPresent()) {
                    var usuario = usuarioOptional.get(); // Obtenha o usuário

                    if(!sessionService.isSessaoPreenchida("usuarioLogado"))
                        sessionService.salvarNaSessao("usuarioLogado",usuario);
                    else{
                        Usuario usuarioSessao  = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
                        if (usuarioSessao.getId() != usuario.getId())
                            sessionService.salvarNaSessao("usuarioLogado",usuario);
                    }
                    // Crie a autenticação
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    sessionService.invalidarSessao();
                    // Usuário não encontrado, você pode retornar um erro ou logar
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autorizado");
                    return;
                }
            } catch (JWTVerificationException e) {
                sessionService.invalidarSessao();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getLocalizedMessage()); // Mensagem personalizada
                return;
            }
        }

        filterChain.doFilter(request, response);
    }




    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
