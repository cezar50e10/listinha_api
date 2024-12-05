package br.net.dotbr.listadecomprasmercado.infra.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;



@Service
public class SessionService {

    private final HttpServletRequest request;

    // Construtor que recebe o HttpServletRequest
    public SessionService(HttpServletRequest request) {
        this.request = request;
    }

    // Método para obter a sessão
    private HttpSession getSession() {
        return request.getSession(); // Obtém ou cria a sessão
    }

    // Método para salvar dados na sessão
    public void salvarNaSessao(String chave, Object valor) {
        getSession().setAttribute(chave, valor);
    }

    // Método para recuperar dados da sessão
    public Object recuperarDaSessao(String chave) {
        return getSession().getAttribute(chave);
    }

    // Método para invalidar a sessão
    public void invalidarSessao() {
        getSession().invalidate();
    }

    // Método para verificar se um atributo na sessão está preenchido
    public boolean isSessaoPreenchida(String chave) {
        Object valor = recuperarDaSessao(chave);
        return valor != null; // Retorna true se o valor não for nulo, indicando que a sessão está preenchida
    }
}
