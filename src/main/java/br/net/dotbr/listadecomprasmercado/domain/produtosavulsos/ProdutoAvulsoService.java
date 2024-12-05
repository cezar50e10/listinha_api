package br.net.dotbr.listadecomprasmercado.domain.produtosavulsos;

import br.net.dotbr.listadecomprasmercado.domain.ValidacaoException;
import br.net.dotbr.listadecomprasmercado.domain.listadecompra.ParticipanteListaCompra;
import br.net.dotbr.listadecomprasmercado.domain.listadecompra.ParticipanteListaCompraRepository;
import br.net.dotbr.listadecomprasmercado.domain.usuario.Usuario;
import br.net.dotbr.listadecomprasmercado.infra.session.SessionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoAvulsoService {

    @Autowired
    private ProdutoAvulsoRepository produtoAvulsoRepository;

    private final SessionService sessionService;

    public ProdutoAvulsoService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public ResponseEntity cadastrarNovoProdutoAvulso(DadosCadastroProdutoAvulso dados) {

        var produtoNovo = new ProdutoAvulso(dados);

        Usuario usuario = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");

        produtoNovo.setUsuario(usuario);

        var pordutoSalvo = produtoAvulsoRepository.save(produtoNovo);


        return  ResponseEntity.ok("produto salvo com sucesso");
    }

    public ResponseEntity listarProdutos() {

        List<DadosListagemProdutoAvulso> listaDeProdutos = produtoAvulsoRepository.findAllByStatus(StatusProdutoAvulso.ATIVO).stream()
                .map(p -> new DadosListagemProdutoAvulso(p.getId(),p.getNome(),p.getDescricao(),p.getStatus(),p.getTipoMedida()))
                .toList();


        return  ResponseEntity.ok(listaDeProdutos);
    }

    public ResponseEntity alterarProdutoAvulso(DadosAlteracaoProdutoAvulso dados) {
        Usuario usuario = (Usuario) sessionService.recuperarDaSessao("usuarioLogado");
        var produtoAlterado = new ProdutoAvulso(dados,usuario);
        
        Optional<ProdutoAvulso> produtoOptional = produtoAvulsoRepository.findByIdAndStatus(produtoAlterado.getId(), StatusProdutoAvulso.ATIVO);

        if(produtoOptional.isPresent()) {
            if(produtoOptional.get().getUsuario().getId() == produtoAlterado.getUsuario().getId()){
                produtoAvulsoRepository.save(produtoAlterado);
                return ResponseEntity.ok("produto alterado com sucesso");
            }else{
                throw new ValidacaoException("Você não tem permissão para alterar este produto");
            }
        }else{
            throw new ValidacaoException("O Produto que esta tentando altera não existe");
        }
    }

    
}
